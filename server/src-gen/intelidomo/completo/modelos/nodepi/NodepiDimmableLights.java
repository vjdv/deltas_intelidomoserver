package intelidomo.completo.modelos.nodepi;

import intelidomo.estatico.Constantes;
import intelidomo.completo.modelos.nodepi.LuzDimp;
import intelidomo.completo.modelos.Controlador;
import intelidomo.completo.modelos.Casa;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
/*** added by dNodepiLucesDim
 */
public class NodepiDimmableLights extends Controlador {
	private List<LuzDimp> luces = new ArrayList<LuzDimp>();
	private Casa casa = Casa.getInstancia();
	private LuzDimp luz1, luz2;
	public NodepiDimmableLights(boolean dos) {
		setTipo("NodepiDL Controller");
		luz1 = new LuzDimp(this, 1);
		luces.add(luz1);
		casa.registrarLuz(luz1);
		if(dos) {
			luz2 = new LuzDimp(this, 2);
			luces.add(luz2);
			casa.registrarLuz(luz2);
		}
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
	private void entenderPedazo(String key, String val) {
		if(key.equals("light1")) luz1.setEstado(val);
		if(key.equals("light2")) luz2.setEstado(val);
	}
	public void setIndisponibles() {
		for(LuzDimp l : luces) l.setEstado(Constantes.INDISPONIBLE);
		if(hayCambios()) notificarObs();
	}
	public void obtenerValores(int cual) {
		obtenerValores(cual, - 1);
	}
	public void obtenerValores(int cual, int pct) {
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		try {
			socket = new Socket(getIP(), 80);
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
			if(pct == - 1) out.print("get light" + cual);
			else out.print("set light" + cual + " to " + pct);
			out.flush();
			String mensaje = in.readLine();
			System.out.println(mensaje);
			entenderCadena(mensaje);
			in.close();
			out.close();
			socket.close();
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
			obtenerValores(1);
			if(luz2 != null) obtenerValores(2);
			while(true) {
				try {
					Thread.sleep(60000);
				}
				catch(InterruptedException ex) {
					System.out.println("INTERRUPTED");
				}
				obtenerValores(1);
				if(luz2 != null) obtenerValores(2);
			}
		}
	}
}