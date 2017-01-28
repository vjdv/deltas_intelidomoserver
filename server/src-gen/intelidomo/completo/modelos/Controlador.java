package intelidomo.completo.modelos;

import intelidomo.estatico.Constantes;
import intelidomo.completo.modelos.obs.Observador;
import intelidomo.completo.modelos.obs.Observado;
import java.util.*;
/*** added by dModelos
 */
public abstract class Controlador implements Observado {
	private Casa casa = Casa.getInstancia();
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String ip;
	public String getIP() {
		return ip;
	}
	public void setIP(String ip) {
		this.ip = ip;
	}
	private String puerto;
	public String getPuerto() {
		return puerto;
	}
	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	public int getPuertoInt() {
		return Integer.parseInt(puerto);
	}
	private String tipo;
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	private int area;
	public void setArea(int a) {
		area = a;
	}
	public int getArea() {
		return area;
	}
	public void conectar() {
	};
	private List<Dispositivo> lista_dispositivos = new ArrayList<Dispositivo>();
	public void registrarDispositivo(Dispositivo d) {
		lista_dispositivos.add(d);
		d.setArea(area);
	}
	public void marcarIndisponibles() {
		for(Dispositivo d : lista_dispositivos)
		d.setEstado(Constantes.INDISPONIBLE);
		if(hayCambios()) notificarObs();
	}
	public void marcarIndisponibles(Dispositivo ds []) {
		for(Dispositivo d : ds) d.setEstado(Constantes.INDISPONIBLE);
		if(hayCambios()) notificarObs();
	}
	private List<Dispositivo> lista_cambios = new ArrayList<Dispositivo>();
	public void agregarCambio(Dispositivo d) {
		lista_cambios.add(d);
	}
	public void resetCambios() {
		lista_cambios.clear();
	}
	public List<Dispositivo> getCambios() {
		return lista_cambios;
	}
	public boolean hayCambios() {
		return lista_cambios.size() > 0;
	}
	private List<Observador> lista_obs = new ArrayList<Observador>();
	public void registrarObs(Observador o) {
		lista_obs.add(o);
	}
	public void removerObs(Observador o) {
		lista_obs.remove(o);
	}
	public Observador [] getObservadores() {
		return lista_obs.toArray(new Observador[lista_obs.size()]);
	}
	public void notificarObs() {
		for(Observador obs : getObservadores()) obs.actualizarObs(this);
	}
}