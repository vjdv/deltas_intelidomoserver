package intelidomo.completo.modelos.rodas;

import intelidomo.estatico.Constantes;
import intelidomo.completo.modelos.Controlador;
import intelidomo.completo.modelos.Casa;
import java.io.*;
import java.net.*;
import java.util.logging.*;
/*** added by dRodas* modified by dRodasLuces* modified by dRodasPresencia*
modified by dRodasTemperatura
 */
public class ArduinoRodas extends Controlador {
	public ArduinoRodas() {
		setTipo("Arduino Rodas v1.0");
	}
	/*** modified by dRodasLuces* modified by dRodasPresencia* modified by
	dRodasTemperatura
	 */
	public void constructor() {
		Casa.getInstancia().registrarSensorTemperatura(st);
		new Thread(new KeepUpdated("GET /Temp$", 10000)).start();
		constructor_original6();
	}
	@Override
	public void conectar() {
	}
	public void enviarComando(String cmd) {
		new Thread(new EnviarComando(cmd)).start();
	}
	private void entenderCadena(String str) {
		String lkey = "";
		str = str.trim();
		str = str.replaceAll("#", "");
		String pedazos [] = str.split("\\|");
		if(pedazos.length == 0 || pedazos.length % 2 == 1) return;
		for(int i = 0;
			i < pedazos.length;
			i += 2) {
			String key = pedazos[i];
			String val = pedazos[i + 1];
			if(! key.equals("$E")) lkey = key;
			if(key.equals("$E") && ! val.equals("1")) entenderPedazo(lkey, "" +
				Constantes.INDISPONIBLE);
			else entenderPedazo(key, val);
		}
		if(hayCambios()) notificarObs();
	}
	/*** modified by dRodasLuces* modified by dRodasPresencia* modified by
	dRodasTemperatura
	 */
	private void entenderPedazo(String key, String val) {
		if(key.equals("$ST")) {
			st.setEstado(val);
		}
		entenderPedazo_original7(key, val);
	}
	class EnviarComando implements Runnable {
		String comando;
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		public EnviarComando(String cmd) {
			comando = cmd;
		}
		@Override
		public synchronized void run() {
			try {
				socket = new Socket(getIP(), 85);
				socket.setSoTimeout(10000);
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
			catch(Exception ex) {
				String params [] = {
					getIP(), ex.getMessage()
				};
				Logger.getLogger("DomoLog").log(Level.WARNING,
					"ArduinoRodas: Error IO con {0} por {1}", params);
				marcarIndisponibles();
				return;
			}
			try {
				out.print(comando);
				out.flush();
				int iii;
				String cadena = "";
				boolean primero = true;
				while((iii = in.read()) != - 1) {
					char c = ( char ) iii;
					if(c == '#') {
						if(primero) primero = false;
						else {
							entenderCadena(cadena);
							break;
						}
					}
					else cadena += c;
				}
				Logger.getLogger("DomoLog").log(Level.FINER, "Respuesta desde " + getIP()
					+ ": " + cadena);
			}
			catch(Exception ex) {
				Logger.getLogger("DomoLog").log(Level.WARNING,
					"ArduinoRodas: Error en dispositivo " + getIP(), ex);
				marcarIndisponibles();
			}
			try {
				out.close();
				in.close();
				socket.close();
			}
			catch(Exception ex) {
				Logger.getLogger("DomoLog").log(Level.WARNING,
					"ArduinoRodas: Error conexión por {0}", ex.getMessage());
				marcarIndisponibles();
			}
		}
	}
	class KeepUpdated implements Runnable {
		private final String comando;
		private final long tiempo;
		public KeepUpdated(String cmd, long tmr) {
			comando = cmd;
			tiempo = tmr;
		}
		@Override
		public void run() {
			try {
				while(true) {
					new EnviarComando(comando).run();
					Thread.sleep(tiempo);
				}
			}
			catch(InterruptedException ex) {
				Logger.getLogger("DomoLog").log(Level.WARNING,
					"Error interrupción en {0}", getIP());
			}
		}
	}
	/*** added by dRodasLuces
	 */
	ArduinoRodasLuz luz = new ArduinoRodasLuz(this);
	/*** modified by dRodasLuces
	 */
	public void constructor_original0() {
		conectar();
		new Thread(new EnviarComando("GET /Test$")).start();
	}
	/*** modified by dRodasLuces
	 */
	private void entenderPedazo_original2(String key, String val) {
	}
	/*** added by dRodasPresencia
	 */
	ArduinoRodasPir pir = new ArduinoRodasPir(this);
	/*** modified by dRodasLuces* modified by dRodasPresencia
	 */
	public void constructor_original4() {
		Casa.getInstancia().registrarLuz(luz);
		new Thread(new KeepUpdated("GET /Light$", 5000)).start();
		constructor_original0();
	}
	/*** modified by dRodasLuces* modified by dRodasPresencia
	 */
	private void entenderPedazo_original5(String key, String val) {
		if(key.equals("$L")) {
			String x = val.equals("1") ? "1" : "0";
			luz.setEstado(x);
		}
		entenderPedazo_original2(key, val);
	}
	/*** added by dRodasTemperatura
	 */
	ArduinoRodasTemp st = new ArduinoRodasTemp(this);
	/*** modified by dRodasLuces* modified by dRodasPresencia* modified by
	dRodasTemperatura
	 */
	public void constructor_original6() {
		Casa.getInstancia().registrarSensorPresencia(pir);
		new Thread(new KeepUpdated("GET /Pre$", 2000)).start();
		constructor_original4();
	}
	/*** modified by dRodasLuces* modified by dRodasPresencia* modified by
	dRodasTemperatura
	 */
	private void entenderPedazo_original7(String key, String val) {
		if(key.equals("$P")) {
			String x = val.equals("1") ? "1" : "0";
			pir.setEstado(x);
		}
		entenderPedazo_original5(key, val);
	}
}