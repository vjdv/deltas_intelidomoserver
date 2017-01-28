package intelidomo.plus.modelos;

import intelidomo.plus.db.Gestor;
import intelidomo.plus.modelos.commands.Funcion;
import intelidomo.plus.modelos.commands.FuncionI;
import intelidomo.plus.modelos.obs.Observado;
import intelidomo.plus.modelos.obs.Observador;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.*;
import intelidomo.plus.modelos.voz.TextCommands;
import intelidomo.plus.modelos.voz.HumedadLink;
import intelidomo.plus.modelos.voz.TemperaturaLink;
import intelidomo.plus.modelos.voz.TemperaturasLink;
import intelidomo.plus.modelos.voz.CanalTvLink;
/*** added by dModelos* modified by dLuces* modified by dPresencia* modified
by dTemperatura* modified by dHumedad* modified by dCortina* modified by dTV*
modified by dAlarma* modified by dVoz* modified by dVozLuces* modified by
dVozHumd* modified by dVozTemperatura* modified by dVozCortina* modified by
dVozTV
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
	/*** modified by dVozTemperatura
	 */
	public void constructor() {
		constructor_original4();
		TemperaturasLink f = new TemperaturasLink(mapaTemperatura);
		voz.addComando("temperatura promedio", f);
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
	/*** added by dLuces* modified by dVozLuces
	 */
	public void registrarLuz(Luz l) {
		registrarLuz_original0(l);
		Funcion f1 = ( Funcion ) l.getFuncion("encenderLuz");
		Funcion f2 = ( Funcion ) l.getFuncion("apagarLuz");
		voz.addComando("encender luz " + l.getId(), f1);
		voz.addComando("apagar luz " + l.getId(), f2);
		voz.addComando("encender luz " + TextCommands.number2str(l.getId()), f1);
		voz.addComando("apagar luz " + TextCommands.number2str(l.getId()), f2);
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
	/*** added by dTemperatura* modified by dVozTemperatura
	 */
	public void registrarSensorTemperatura(SensorTemperatura st) {
		registrarSensorTemperatura_original6(st);
		TemperaturaLink f = new TemperaturaLink(st);
		voz.addComando("temperatura " + st.getId(), f);
		voz.addComando("temperatura " + TextCommands.number2str(st.getId()), f);
	}
	/*** added by dHumedad
	 */
	public Map<Integer, SensorHumedad> mapaHumedad = new HashMap<Integer,
		SensorHumedad>();
	/*** added by dHumedad
	 */
	private static int sh_count = 1;
	/*** added by dHumedad* modified by dVozHumd
	 */
	public void registrarSensorHumedad(SensorHumedad sh) {
		registrarSensorHumedad_original2(sh);
		HumedadLink f = new HumedadLink(sh);
		voz.addComando("humedad " + sh.getId(), f);
		voz.addComando("humedad " + TextCommands.number2str(sh.getId()), f);
	}
	/*** added by dCortina
	 */
	public Map<Integer, Cortina> mapaCortinas = new HashMap<Integer, Cortina>();
	/*** added by dCortina
	 */
	private static int cortina_count = 1;
	/*** added by dCortina* modified by dVozCortina
	 */
	public void registrarCortina(Cortina c) {
		registrarCortina_original8(c);
		Funcion f1 = ( Funcion ) c.getFuncion("open");
		Funcion f2 = ( Funcion ) c.getFuncion("close");
		voz.addComando("abrir cortina " + c.getId(), f1);
		voz.addComando("cerrar cortina " + c.getId(), f2);
		voz.addComando("abrir cortina " + TextCommands.number2str(c.getId()), f1);
		voz.addComando("cerrar cortina " + TextCommands.number2str(c.getId()), f2);
	}
	/*** added by dTV
	 */
	public Map<Integer, Tele> mapaTeles = new HashMap<Integer, Tele>();
	/*** added by dTV
	 */
	private static int tv_count = 1;
	/*** added by dTV* modified by dVozTV
	 */
	public void registrarTV(Tele tv) {
		registrarTV_original10(tv);
		Funcion f1 = ( Funcion ) tv.getFuncion("power");
		if(f1 != null) {
			voz.addComando("encender television " + tv.getId(), f1);
			voz.addComando("apagar television " + tv.getId(), f1);
			voz.addComando("encender televisión " + tv.getId(), f1);
			voz.addComando("apagar televisión " + tv.getId(), f1);
			voz.addComando("encender television " +
				TextCommands.number2str(tv.getId()), f1);
			voz.addComando("apagar television " + TextCommands.number2str(tv.getId()),
				f1);
			voz.addComando("encender televisión " +
				TextCommands.number2str(tv.getId()), f1);
			voz.addComando("apagar televisión " + TextCommands.number2str(tv.getId()),
				f1);
		}
		Funcion f2 = ( Funcion ) tv.getFuncion("setChannel");
		if(f2 != null) {
			CanalTvLink tvlink = new CanalTvLink(f2);
			voz.addComando("television " + tv.getId() + " canal", tvlink);
			voz.addComando("televisión " + tv.getId() + " canal", tvlink);
			voz.addComando("television " + TextCommands.number2str(tv.getId()) +
				" canal", tvlink);
			voz.addComando("televisión " + TextCommands.number2str(tv.getId()) +
				" canal", tvlink);
		}
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
	/*** added by dVoz
	 */
	private final TextCommands voz = new TextCommands();
	/*** added by dVoz
	 */
	public String despacharComandoTexto(String cmd) {
		return voz.parse(cmd);
	}
	/*** added by dLuces* modified by dVozLuces
	 */
	public void registrarLuz_original0(Luz l) {
		l.setId(luz_count);
		mapaLuces.put(luz_count, l);
		registrarDispositivo(l);
		luz_count ++;
	}
	/*** added by dHumedad* modified by dVozHumd
	 */
	public void registrarSensorHumedad_original2(SensorHumedad sh) {
		sh.setId(sh_count);
		mapaHumedad.put(sh_count, sh);
		registrarDispositivo(sh);
		sh_count ++;
	}
	/*** modified by dVozTemperatura
	 */
	public void constructor_original4() {
		Area [] areas = g.getListaAreas();
		for(Area a : areas) areas_map.put(a.getId(), a);
		Controlador [] controladores = g.getListaControladores();
		for(Controlador c : controladores) {
			controladores_map.put(c.getId(), c);
			despacharControlador(c);
		}
	}
	/*** added by dTemperatura* modified by dVozTemperatura
	 */
	public void registrarSensorTemperatura_original6(SensorTemperatura st) {
		st.setId(st_count);
		mapaTemperatura.put(st_count, st);
		registrarDispositivo(st);
		st_count ++;
	}
	/*** added by dCortina* modified by dVozCortina
	 */
	public void registrarCortina_original8(Cortina c) {
		c.setId(cortina_count);
		mapaCortinas.put(cortina_count, c);
		registrarDispositivo(c);
		cortina_count ++;
	}
	/*** added by dTV* modified by dVozTV
	 */
	public void registrarTV_original10(Tele tv) {
		tv.setId(tv_count);
		mapaTeles.put(tv_count, tv);
		registrarDispositivo(tv);
		tv_count ++;
	}
}