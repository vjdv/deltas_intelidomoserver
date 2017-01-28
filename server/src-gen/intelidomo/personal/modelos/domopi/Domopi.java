package intelidomo.personal.modelos.domopi;

import intelidomo.estatico.Constantes;
import intelidomo.personal.modelos.Controlador;
import intelidomo.personal.modelos.Dispositivo;
import intelidomo.personal.modelos.Casa;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
/*** added by dDomopi* modified by dDomopiLuces* modified by dDomopiPresencia*
modified by dDomopiTemperatura* modified by dDomopiHumedad
 */
public class Domopi extends Controlador {
	private List<Dispositivo> dispositivos = new ArrayList<Dispositivo>();
	private Casa casa = Casa.getInstancia();
	private Conexion conn;
	public Domopi() {
		setTipo("Domopi Controller");
	}
	public void constructor() {
		conectar();
	}
	@Override
	public void conectar() {
		conn = new Conexion();
		new Thread(conn).start();
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
	/*** modified by dDomopiLuces* modified by dDomopiPresencia* modified by
	dDomopiTemperatura* modified by dDomopiHumedad
	 */
	private void entenderPedazo(String key, String val) {
		if(key.startsWith("HUMD")) {
			Character id = key.length() > 4 ? key.charAt(4) : '0';
			HumedadImp sh = mapa_sh.get(id);
			if(sh == null) {
				sh = new HumedadImp(this, id);
				dispositivos.add(sh);
				mapa_sh.put(id, sh);
				casa.registrarSensorHumedad(sh);
			}
			sh.setEstado(val);
		}
		entenderPedazo_original4(key, val);
	}
	public void enviarComando(String cmd) {
		conn.out.println(cmd);
	}
	public void setIndisponibles() {
		for(Dispositivo d : dispositivos) d.setEstado(Constantes.INDISPONIBLE);
		if(hayCambios()) notificarObs();
	}
	public class Conexion implements Runnable {
		String comando;
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		private long tiempo_reconn = 10000;
		private int intentos_reconn = 0;
		@Override
		public synchronized void run() {
			try {
				socket = new Socket(getIP(), getPuertoInt());
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
			catch(UnknownHostException ex) {
				Logger.getLogger("DomoLog").log(Level.WARNING,
					"DomoPi: No se encontró dispositivo " + getIP());
				desconectar();
				esperarConexion();
				return;
			}
			catch(IOException ex) {
				Logger.getLogger("DomoLog").log(Level.WARNING,
					"DomoPi: No se conectó con dispositivo " + getIP());
				desconectar();
				esperarConexion();
				return;
			}
			try {
				intentos_reconn = 0;
				out.println("GET ALL");
				out.flush();
				String cadena;
				while((cadena = in.readLine()) != null) {
					if(! cadena.equals("")) entenderCadena(cadena);
				}
			}
			catch(IOException ex) {
				Logger.getLogger("DomoLog").log(Level.SEVERE,
					"DomoPi: Se perdió conexión con dispositivo " + getIP());
			}
			desconectar();
			esperarConexion();
		}
		private void esperarConexion() {
			intentos_reconn ++;
			if(intentos_reconn >= 10) tiempo_reconn = 60000;
			else if(intentos_reconn >= 3) tiempo_reconn = 30000;
			else tiempo_reconn = 10000;
			try {
				Logger.getLogger("DomoLog").log(Level.INFO,
					"DomoPi: Intento de conexión #" + intentos_reconn + " con dispositivo " +
					getIP() + " en " + tiempo_reconn + "ms");
				Thread.sleep(tiempo_reconn);
			}
			catch(InterruptedException ex) {
				Logger.getLogger("DomoLog").log(Level.SEVERE,
					"DomoPi: No fue posible esperar por reconexión", ex);
			}
			conectar();
		}
		public void desconectar() {
			if(socket == null) return;
			try {
				setIndisponibles();
				out.close();
				in.close();
				socket.close();
				socket = null;
			}
			catch(IOException ex) {
				Logger.getLogger("DomoLog").log(Level.WARNING,
					"DomoPi: Error al cerrar conexión abierta de dispositivo " + getIP(),
					ex);
			}
		}
	}
	/*** added by dDomopiLuces
	 */
	Map<Character, LuzImp> mapa_luces = new HashMap<Character, LuzImp>();
	/*** modified by dDomopiLuces
	 */
	private void entenderPedazo_original0(String key, String val) {
	}
	/*** added by dDomopiPresencia
	 */
	Map<Character, PresenciaImp> mapa_pir = new HashMap<Character,
		PresenciaImp>();
	/*** modified by dDomopiLuces* modified by dDomopiPresencia
	 */
	private void entenderPedazo_original2(String key, String val) {
		if(key.startsWith("LUZ")) {
			Character id = key.charAt(3);
			LuzImp luz = mapa_luces.get(id);
			if(luz == null) {
				luz = new LuzImp(this, id);
				mapa_luces.put(id, luz);
				Casa.getInstancia().registrarLuz(luz);
			}
			String x = val.equals("ON") ? "1" : "0";
			luz.setEstado(x);
		}
		entenderPedazo_original0(key, val);
	}
	/*** added by dDomopiTemperatura
	 */
	Map<Character, TemperaturaImp> mapa_st = new HashMap<Character,
		TemperaturaImp>();
	/*** modified by dDomopiLuces* modified by dDomopiPresencia* modified by
	dDomopiTemperatura
	 */
	private void entenderPedazo_original3(String key, String val) {
		if(key.startsWith("PIR")) {
			Character id = key.length() > 4 ? key.charAt(4) : '0';
			PresenciaImp pir = mapa_pir.get(id);
			if(pir == null) {
				pir = new PresenciaImp(this, id);
				dispositivos.add(pir);
				mapa_pir.put(id, pir);
				casa.registrarSensorPresencia(pir);
			}
			pir.hay(val.equals("SI"));
		}
		entenderPedazo_original2(key, val);
	}
	/*** added by dDomopiHumedad
	 */
	Map<Character, HumedadImp> mapa_sh = new HashMap<Character, HumedadImp>();
	/*** modified by dDomopiLuces* modified by dDomopiPresencia* modified by
	dDomopiTemperatura* modified by dDomopiHumedad
	 */
	private void entenderPedazo_original4(String key, String val) {
		if(key.startsWith("TEMP")) {
			Character id = key.length() > 4 ? key.charAt(4) : '0';
			TemperaturaImp st = mapa_st.get(id);
			if(st == null) {
				st = new TemperaturaImp(this, id);
				dispositivos.add(st);
				mapa_st.put(id, st);
				casa.registrarSensorTemperatura(st);
			}
			st.setEstado(val);
		}
		entenderPedazo_original3(key, val);
	}
}