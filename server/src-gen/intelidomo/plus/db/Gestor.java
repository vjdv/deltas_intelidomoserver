package intelidomo.plus.db;

import java.util.logging.*;
import java.util.*;
import java.sql.*;
import intelidomo.plus.modelos.Area;
import intelidomo.plus.modelos.Controlador;
import intelidomo.plus.modelos.Usuario;
import intelidomo.plus.modelos.rodas.ArduinoRodas;
import intelidomo.plus.modelos.domopi.Domopi;
import intelidomo.plus.modelos.domopi.DomopiTV;
import intelidomo.plus.modelos.nodepi.NodepiSensors;
import intelidomo.plus.modelos.nodepi.NodepiDimmableLights;
import intelidomo.plus.modelos.nodepi.CortinaController;
import intelidomo.plus.modelos.milight.MiLightClient;
/*** added by dDatabase* modified by dDatabase* modified by dRodas* modified
by dDomopi* modified by dDomopiTV* modified by dNodepi* modified by
dNodepiLucesDim* modified by dNodepiCortina* modified by dMilight
 */
public class Gestor extends ConexionDB {
	private static Gestor singleton;
	public Gestor() {
		super("192.168.1.100", "intelidomo", "postgres", "5sfLCwh.ScXf_6RpV3/f0");
	}
	public static Gestor getSingleton() {
		return singleton;
	}
	public static void setSingleton(Gestor g) {
		singleton = g;
	}
	/*** added by dDatabase
	 */
	public Usuario obtenerUsuarioLogeado(String u, String p) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs =
			stmt.executeQuery("SELECT * FROM usuarios WHERE id_usuario = '" + u +
				"';");
			if(rs.next()) {
				Usuario user = new Usuario();
				user.setId(rs.getString("id_usuario"));
				user.setPassword(rs.getString("password"));
				user.setTipo(rs.getString("tipo_usuario"));
				user.setNombre(rs.getString("nombre"));
				user.setStatus(rs.getString("status"));
				if(user.getPassword().equals(p)) return user;
			}
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING, "Error usuario", ex);
		}
		return null;
	}
	/*** added by dDatabase
	 */
	public Area [] getAreasByQuery(String q) {
		ArrayList<Area> la = new ArrayList<Area>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()) {
				Area a = new Area();
				a.setId(rs.getInt("id_area"));
				a.setTipo(rs.getString("tipo_area"));
				a.setNombre(rs.getString("nombre"));
				la.add(a);
			}
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Error al obtener areas con consulta: " + q, ex);
			return null;
		}
		return la.toArray(new Area[la.size()]);
	}
	/*** added by dDatabase
	 */
	public Area [] getListaAreas() {
		return getAreasByQuery("SELECT * FROM areas ORDER BY nombre");
	}
	/*** added by dDatabase
	 */
	public Controlador [] getControladoresByQuery(String q) {
		ArrayList<Controlador> ld = new ArrayList<Controlador>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			while(rs.next()) {
				String tipo = rs.getString("tipo_disp");
				Controlador d = despacharControlador(tipo, rs);
				if(d != null) ld.add(d);
			}
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Error al obtener dispositivos con consulta: " + q, ex);
			return null;
		}
		return ld.toArray(new Controlador[ld.size()]);
	}
	/*** added by dDatabase
	 */
	public Controlador [] getListaControladores() {
		return getControladoresByQuery("SELECT * FROM dispositivos ORDER BY ip");
	}
	/*** added by dDatabase* modified by dRodas* modified by dDomopi* modified by
	dDomopiTV* modified by dNodepi* modified by dNodepiLucesDim* modified by
	dNodepiCortina* modified by dMilight
	 */
	private Controlador despacharControlador(final String tipo, final ResultSet
		rs) throws SQLException {
		if(tipo.equals("MiLight")) {
			MiLightClient ml = new MiLightClient();
			ml.setId(rs.getInt("id_disp"));
			ml.setArea(rs.getInt("id_area"));
			ml.setIP(rs.getString("ip"));
			return ml;
		}
		return despacharControlador_original7(tipo, rs);
	}
	/*** added by dDatabase* modified by dRodas
	 */
	private Controlador despacharControlador_original0(final String tipo, final
		ResultSet rs) throws SQLException {
		if(tipo == null || rs == null) return null;
		Logger.getLogger("DomoLog").log(Level.INFO,
			"No se conoce dispositivo tipo: " + tipo);
		return null;
	}
	/*** added by dDatabase* modified by dRodas* modified by dDomopi
	 */
	private Controlador despacharControlador_original2(final String tipo, final
		ResultSet rs) throws SQLException {
		if(tipo.equals("ArduinoRodas")) {
			ArduinoRodas ardisp = new ArduinoRodas();
			ardisp.setId(rs.getInt("id_disp"));
			ardisp.setArea(rs.getInt("id_area"));
			ardisp.setIP(rs.getString("ip"));
			ardisp.setPuerto(rs.getString("puerto"));
			ardisp.constructor();
			return ardisp;
		}
		return despacharControlador_original0(tipo, rs);
	}
	/*** added by dDatabase* modified by dRodas* modified by dDomopi* modified by
	dDomopiTV
	 */
	private Controlador despacharControlador_original3(final String tipo, final
		ResultSet rs) throws SQLException {
		if(tipo.equals("Domopi")) {
			Domopi dodisp = new Domopi();
			dodisp.setId(rs.getInt("id_disp"));
			dodisp.setArea(rs.getInt("id_area"));
			dodisp.setIP(rs.getString("ip"));
			dodisp.setPuerto(rs.getString("puerto"));
			dodisp.constructor();
			return dodisp;
		}
		return despacharControlador_original2(tipo, rs);
	}
	/*** added by dDatabase* modified by dRodas* modified by dDomopi* modified by
	dDomopiTV* modified by dNodepi
	 */
	private Controlador despacharControlador_original4(final String tipo, final
		ResultSet rs) throws SQLException {
		if(tipo.equals("DomopiTV")) {
			DomopiTV tvdisp = new DomopiTV();
			tvdisp.setId(rs.getInt("id_disp"));
			tvdisp.setArea(rs.getInt("id_area"));
			tvdisp.setIP(rs.getString("ip"));
			tvdisp.setPuerto(rs.getString("puerto"));
			tvdisp.constructor();
			return tvdisp;
		}
		return despacharControlador_original3(tipo, rs);
	}
	/*** added by dDatabase* modified by dRodas* modified by dDomopi* modified by
	dDomopiTV* modified by dNodepi* modified by dNodepiLucesDim
	 */
	private Controlador despacharControlador_original5(final String tipo, final
		ResultSet rs) throws SQLException {
		if(tipo.equals("NodepiSensors")) {
			NodepiSensors dodisp = new NodepiSensors();
			dodisp.setId(rs.getInt("id_disp"));
			dodisp.setArea(rs.getInt("id_area"));
			dodisp.setIP(rs.getString("ip"));
			dodisp.constructor();
			return dodisp;
		}
		return despacharControlador_original4(tipo, rs);
	}
	/*** added by dDatabase* modified by dRodas* modified by dDomopi* modified by
	dDomopiTV* modified by dNodepi* modified by dNodepiLucesDim* modified by
	dNodepiCortina
	 */
	private Controlador despacharControlador_original6(final String tipo, final
		ResultSet rs) throws SQLException {
		if(tipo.startsWith("NodepiDimLight")) {
			boolean dos = tipo.length() == 15;
			NodepiDimmableLights dodisp = new NodepiDimmableLights(dos);
			dodisp.setId(rs.getInt("id_disp"));
			dodisp.setArea(rs.getInt("id_area"));
			dodisp.setIP(rs.getString("ip"));
			dodisp.constructor();
			return dodisp;
		}
		return despacharControlador_original5(tipo, rs);
	}
	/*** added by dDatabase* modified by dRodas* modified by dDomopi* modified by
	dDomopiTV* modified by dNodepi* modified by dNodepiLucesDim* modified by
	dNodepiCortina* modified by dMilight
	 */
	private Controlador despacharControlador_original7(final String tipo, final
		ResultSet rs) throws SQLException {
		if(tipo.equals("NodepiCortina")) {
			CortinaController controller = new CortinaController();
			controller.setId(rs.getInt("id_disp"));
			controller.setArea(rs.getInt("id_area"));
			controller.setIP(rs.getString("ip"));
			controller.constructor();
			return controller;
		}
		return despacharControlador_original6(tipo, rs);
	}
}