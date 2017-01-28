package intelidomo.completo.modelos;

import intelidomo.completo.db.Gestor;
import intelidomo.completo.modelos.commands.Funcion;
import intelidomo.completo.modelos.commands.FuncionI;
import intelidomo.completo.modelos.obs.Observado;
import intelidomo.completo.modelos.obs.Observador;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.*;
/*** added by dModelos* modified by dLuces* modified by dPresencia* modified
by dTemperatura* modified by dHumedad* modified by dCortina* modified by dTV*
modified by dAlarma
 */
public class Casa implements Observador, Observado {
	private static Casa singleton;
	private Gestor g = Gestor.getSingleton();
	private Map<Integer, Controlador> controladores_map = new HashMap<Integer,
		Controlador>();
	private Map<Integer, Area> areas_map = new HashMap<Integer, Area>();
	private List<Dispositivo> dispositivos_ls = new ArrayList<Dispositivo>();
	private Map<Integer, Funcion> funciones_map = new HashMap<Integer,
		Funcion>();
	private List<Observador> observadores_ls = new ArrayList<Observador>();
	private List<Dispositivo> lista_a_notificar;
	public void constructor() {
		Area [] areas = g.getListaAreas();
		for(Area a : areas) areas_map.put(a.getId(), a);
		Controlador [] controladores = g.getListaControladores();
		for(Controlador c : controladores) {
			controladores_map.put(c.getId(), c);
			despacharControlador(c);
		}
	}
	public Area getArea(int id_area) {
		return areas_map.get(id_area);
	}
	@Override
	public void registrarObs(Observador obs) {
		observadores_ls.add(obs);
	}
	@Override
	public void removerObs(Observador obs) {
		observadores_ls.remove(obs);
	}
	@Override
	public void notificarObs() {
		for(Observador obs : observadores_ls) {
			obs.actualizarObs(this);
		}
		lista_a_notificar.clear();
	}
	@Override
	public void actualizarObs(Observado origen) {
		Controlador obj = ( Controlador ) origen;
		lista_a_notificar = new ArrayList<Dispositivo>(obj.getCambios());
		obj.resetCambios();
		notificarObs();
	}
	public List<Dispositivo> getListaCambios() {
		return new ArrayList<Dispositivo>(lista_a_notificar);
	}
	public void despacharControlador(Controlador c) {
		c.registrarObs(this);
		return;
	}
	public void registrarDispositivo(Dispositivo disp) {
		dispositivos_ls.add(disp);
		for(FuncionI fx : disp.getFunciones()) {
			Funcion f = ( Funcion ) fx;
			funciones_map.put(f.getId(), f);
		}
	}
	public boolean ejecutarFuncion(int id_func, Map<String, String> args) {
		Funcion f = funciones_map.get(id_func);
		if(f == null) return false;
		for(Entry<String, String> entry : args.entrySet()) {
			f.setValorDeArgumento(entry.getKey(), entry.getValue());
		}
		try {
			f.ejecutar();
			return true;
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"No se pudo ejecutar la funcion #" + f.toString());
			return false;
		}
	}
	public Area [] getAreasArray() {
		return areas_map.values().toArray(new Area[areas_map.size()]);
	}
	public Dispositivo [] getDispositivosArray() {
		return dispositivos_ls.toArray(new Dispositivo[dispositivos_ls.size()]);
	}
	public static Casa getInstancia() {
		if(singleton == null) singleton = new Casa();
		return singleton;
	}
	/*** added by dLuces
	 */
	public Map<Integer, Luz> mapaLuces = new HashMap<Integer, Luz>();
	/*** added by dLuces
	 */
	private static int luz_count = 1;
	/*** added by dLuces
	 */
	public void registrarLuz(Luz l) {
		l.setId(luz_count);
		mapaLuces.put(luz_count, l);
		registrarDispositivo(l);
		luz_count ++;
	}
	/*** added by dPresencia
	 */
	public Map<Integer, SensorPresencia> mapaPir = new HashMap<Integer,
		SensorPresencia>();
	/*** added by dPresencia
	 */
	private static int pir_count = 1;
	/*** added by dPresencia
	 */
	public void registrarSensorPresencia(SensorPresencia pir) {
		pir.setId(pir_count);
		mapaPir.put(pir_count, pir);
		registrarDispositivo(pir);
		pir_count ++;
	}
	/*** added by dTemperatura
	 */
	public Map<Integer, SensorTemperatura> mapaTemperatura = new HashMap<Integer,
		SensorTemperatura>();
	/*** added by dTemperatura
	 */
	private static int st_count = 1;
	/*** added by dTemperatura
	 */
	public void registrarSensorTemperatura(SensorTemperatura st) {
		st.setId(st_count);
		mapaTemperatura.put(st_count, st);
		registrarDispositivo(st);
		st_count ++;
	}
	/*** added by dHumedad
	 */
	public Map<Integer, SensorHumedad> mapaHumedad = new HashMap<Integer,
		SensorHumedad>();
	/*** added by dHumedad
	 */
	private static int sh_count = 1;
	/*** added by dHumedad
	 */
	public void registrarSensorHumedad(SensorHumedad sh) {
		sh.setId(sh_count);
		mapaHumedad.put(sh_count, sh);
		registrarDispositivo(sh);
		sh_count ++;
	}
	/*** added by dCortina
	 */
	public Map<Integer, Cortina> mapaCortinas = new HashMap<Integer, Cortina>();
	/*** added by dCortina
	 */
	private static int cortina_count = 1;
	/*** added by dCortina
	 */
	public void registrarCortina(Cortina c) {
		c.setId(cortina_count);
		mapaCortinas.put(cortina_count, c);
		registrarDispositivo(c);
		cortina_count ++;
	}
	/*** added by dTV
	 */
	public Map<Integer, Tele> mapaTeles = new HashMap<Integer, Tele>();
	/*** added by dTV
	 */
	private static int tv_count = 1;
	/*** added by dTV
	 */
	public void registrarTV(Tele tv) {
		tv.setId(tv_count);
		mapaTeles.put(tv_count, tv);
		registrarDispositivo(tv);
		tv_count ++;
	}
	/*** added by dAlarma
	 */
	public Map<Integer, Alarma> mapaAlarmas = new HashMap<Integer, Alarma>();
	/*** added by dAlarma
	 */
	private static int alarma_count = 1;
	/*** added by dAlarma
	 */
	public void registrarAlarma(Alarma a) {
		a.setId(alarma_count);
		registrarDispositivo(a);
		mapaAlarmas.put(alarma_count, a);
		alarma_count ++;
	}
}