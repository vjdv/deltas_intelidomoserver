package intelidomo.completo.modelos.nodepi;

import intelidomo.estatico.Constantes;
import intelidomo.completo.modelos.Controlador;
import intelidomo.completo.modelos.Dispositivo;
import intelidomo.completo.modelos.Casa;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
/*** added by dNodepi* modified by dNodepiTemperatura* modified by
dNodepiHumedad* modified by dNodepiFuego* modified by dNodepiGas
 */
public class NodepiSensors extends Controlador {
	private List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
	private Casa casa = Casa.getInstancia();
	private int errors = 0;
	public NodepiSensors() {
		setTipo("Nodepi Controller");
	}
	public void constructor() {
		conectar();
	}
	@Override
	public void conectar() {
		Thread t = new Thread(new Updater());
		t.setDaemon(true);
		t.start();
	}
	private void entenderCadena(String str) {
		str = str.trim();
		str = str.replaceAll("#", "");
		String pedazos [] = str.split("\\|");
		if(pedazos.length == 0 || pedazos.length % 2 == 1) return;
		for(int i = 0;
			i < pedazos.length;
			i += 2) {
			String key = pedazos[i];
			String val = pedazos[i + 1];
			entenderPedazo(key, val);
		}
		if(hayCambios()) notificarObs();
	}
	/*** modified by dNodepiTemperatura* modified by dNodepiHumedad* modified by
	dNodepiFuego* modified by dNodepiGas
	 */
	private void entenderPedazo(String key, String val) {
		if(key.equals("gas_ana")) {
			if(gas1 == null) {
				gas1 = new GasImp(this);
				dispositivos.add(gas1);
				casa.registrarAlarma(gas1);
			}
			int x = Integer.parseInt(val);
			gas1.hay(x > 50);
		}
		if(key.equals("gas_dig")) {
			if(gas2 == null) {
				gas2 = new GasImp(this);
				dispositivos.add(gas2);
				casa.registrarAlarma(gas2);
			}
			gas2.hay(val.equals("1"));
		}
		entenderPedazo_original4(key, val);
	}
	public void setIndisponibles() {
		errors ++;
		for(Dispositivo d : dispositivos) d.setEstado(Constantes.INDISPONIBLE);
		if(hayCambios()) notificarObs();
	}
	public void obtenerValores() {
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		try {
			socket = new Socket(getIP(), 80);
			socket.setSoTimeout(30000);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(Exception ex) {
			String params [] = {
				getIP(), ex.getMessage()
			};
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Nodepi: error conexión IP:{0} EX:{1}", params);
			setIndisponibles();
			return;
		}
		try {
			out.print("get values");
			out.flush();
			String mensaje = in.readLine();
			System.out.println(mensaje);
			entenderCadena(mensaje);
			in.close();
			out.close();
			socket.close();
			errors = 0;
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING, "Conexión perdida IP {0}",
				getIP());
			setIndisponibles();
		}
	}
	public class Updater implements Runnable {
		@Override
		public void run() {
			while(true) {
				obtenerValores();
				long tiempo = errors > 3 ? 60000 : 1000;
				try {
					Thread.sleep(tiempo);
				}
				catch(Exception ex) {
					System.out.println("INTERRUPTED:" + ex.getMessage());
				}
			}
		}
	}
	/*** added by dNodepiTemperatura
	 */
	TemperaturaImp temp;
	/*** modified by dNodepiTemperatura
	 */
	private void entenderPedazo_original0(String key, String val) {
	}
	/*** added by dNodepiHumedad
	 */
	HumedadImp humd;
	/*** modified by dNodepiTemperatura* modified by dNodepiHumedad
	 */
	private void entenderPedazo_original2(String key, String val) {
		if(key.equals("temp")) {
			if(temp == null) {
				temp = new TemperaturaImp(this);
				dispositivos.add(temp);
				casa.registrarSensorTemperatura(temp);
			}
			temp.setEstado(val);
		}
		entenderPedazo_original0(key, val);
	}
	/*** added by dNodepiFuego
	 */
	FuegoImp fire;
	/*** modified by dNodepiTemperatura* modified by dNodepiHumedad* modified by
	dNodepiFuego
	 */
	private void entenderPedazo_original3(String key, String val) {
		if(key.equals("humd")) {
			if(humd == null) {
				humd = new HumedadImp(this);
				dispositivos.add(humd);
				casa.registrarSensorHumedad(humd);
			}
			humd.setEstado(val);
		}
		entenderPedazo_original2(key, val);
	}
	/*** added by dNodepiGas
	 */
	GasImp gas1, gas2;
	/*** modified by dNodepiTemperatura* modified by dNodepiHumedad* modified by
	dNodepiFuego* modified by dNodepiGas
	 */
	private void entenderPedazo_original4(String key, String val) {
		if(key.equals("flama")) {
			if(fire == null) {
				fire = new FuegoImp(this);
				dispositivos.add(fire);
				casa.registrarAlarma(fire);
			}
			fire.hay(val.equals("1"));
		}
		entenderPedazo_original3(key, val);
	}
}