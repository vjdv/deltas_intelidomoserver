package intelidomo.completo.modelos;

import intelidomo.estatico.Constantes;
import intelidomo.completo.modelos.commands.FuncionI;
import intelidomo.completo.modelos.commands.CambiarEstado;
import java.util.*;
/*** added by dModelos
 */
public abstract class Dispositivo implements DispositivoI {
	public Dispositivo() {
		this(true);
	}
	public Dispositivo(boolean cambiaEstado) {
		if(cambiaEstado == true) addFuncion(new CambiarEstado(this));
	}
	private Map<String, FuncionI> funciones = new HashMap<String, FuncionI>();
	public void addFuncion(FuncionI f) {
		funciones.put(f.getNombre(), f);
	}
	public FuncionI [] getFunciones() {
		return funciones.values().toArray(new FuncionI[funciones.size()]);
	}
	public FuncionI getFuncion(String nombre) {
		return funciones.get(nombre);
	}
	private String estado = Constantes.INDISPONIBLE;
	@Override
	public void setEstado(String edo) {
		if(! equalsEstado(edo)) padre.agregarCambio(this);
		estado = edo;
	}
	public String getEstado() {
		return estado;
	}
	public boolean equalsEstado(String x) {
		return getEstado().equals(x);
	}
	private String estado_tipo = Constantes.CADENA;
	public void setTipoEstado(String tipo) {
		estado_tipo = tipo;
	}
	public String getTipoEstado() {
		return estado_tipo;
	}
	private boolean estado_sl = true;
	public void setEstadoSoloLectura(boolean b) {
		estado_sl = b;
	}
	public boolean isEstadoSoloLectura() {
		return estado_sl;
	}
	private int id;
	@Override
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public int getId() {
		return id;
	}
	private int area;
	@Override
	public void setArea(int id_area) {
		area = id_area;
	}
	@Override
	public int getArea() {
		return area;
	}
	private String cat;
	@Override
	public void setCategoria(String c) {
		cat = c;
	}
	@Override
	public String getCategoria() {
		return cat;
	}
	private String scat;
	@Override
	public void setSubcategoria(String c) {
		scat = c;
	}
	@Override
	public String getSubcategoria() {
		return scat;
	}
	private Controlador padre;
	@Override
	public void setControlador(Controlador c) {
		padre = c;
		padre.registrarDispositivo(this);
	}
	@Override
	public Controlador getControlador() {
		return padre;
	}
	private long last_time;
	private boolean continua;
	private long delayTime = 15000;
	public void setEstadoDelay(long x) {
		delayTime = x;
	}
	public void hay(boolean hayx) {
		if(hayx &&(equalsEstado(Constantes.INDISPONIBLE) ||
				equalsEstado(Constantes.NO))) {
			last_time = System.currentTimeMillis();
			continua = false;
			setEstado(Constantes.YES);
			new Thread(new Tiempo()).start();
		}
		else if(! hayx && estado == Constantes.INDISPONIBLE) {
			setEstado(Constantes.NO);
		}
		else if(hayx) {
			System.out.println("Recontando");
			last_time = System.currentTimeMillis();
		}
		else {
			continua = true;
		}
	}
	class Tiempo implements Runnable {
		@Override
		public void run() {
			while(System.currentTimeMillis() - last_time < delayTime || continua ==
				false) {
				try {
					Thread.sleep(100);
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			setEstado(Constantes.NO);
			padre.notificarObs();
		}
	}
}