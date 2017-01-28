package intelidomo.plus.modelos;

import intelidomo.plus.db.Persistente;
import java.util.*;
/*** added by dModelos* modified by dLuces* modified by dPresencia* modified
by dTemperatura* modified by dHumedad* modified by dAlarma
 */
public class Area implements Persistente {
	private int id;
	private String tipo, nombre, grupo;
	@Override
	public String sqlConsultar() {
		return null;
	}
	@Override
	public String sqlInsertar() {
		return null;
	}
	@Override
	public String sqlActualizar() {
		return null;
	}
	@Override
	public String sqlEliminar() {
		return null;
	}
	@Override
	public void setId(Object id) {
		this.id = Integer.parseInt(id.toString());
	}
	@Override
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	/*** added by dLuces
	 */
	public List<Luz> listaLuces = new ArrayList<Luz>();
	/*** added by dLuces
	 */
	public void agregarLuz(Luz l) {
		listaLuces.add(l);
	}
	/*** added by dPresencia
	 */
	public List<SensorPresencia> listaPir = new ArrayList<SensorPresencia>();
	/*** added by dPresencia
	 */
	public void agregarSensorPresencia(SensorPresencia pir) {
		listaPir.add(pir);
	}
	/*** added by dTemperatura
	 */
	public List<SensorTemperatura> listaTemperatura = new
	ArrayList<SensorTemperatura>();
	/*** added by dTemperatura
	 */
	public void agregarSensorTemperatura(SensorTemperatura st) {
		listaTemperatura.add(st);
	}
	/*** added by dHumedad
	 */
	public List<SensorHumedad> listaHumedad = new ArrayList<SensorHumedad>();
	/*** added by dHumedad
	 */
	public void agregarSensorHumedad(SensorHumedad sh) {
		listaHumedad.add(sh);
	}
	/*** added by dAlarma
	 */
	public List<Alarma> listaAlarmas = new ArrayList<Alarma>();
	/*** added by dAlarma
	 */
	public void agregarAlarma(Alarma a) {
		listaAlarmas.add(a);
	}
}