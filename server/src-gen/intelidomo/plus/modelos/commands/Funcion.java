package intelidomo.plus.modelos.commands;

import java.util.*;
/*** added by dModelos
 */
public abstract class Funcion implements FuncionI {
	private static int count = 0;
	private int id;
	public Funcion() {
		id = count;
		count ++;
	}
	@Override
	public int getId() {
		return id;
	}
	private String descripcion;
	@Override
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String d) {
		descripcion = d;
	}
	private String nombre;
	@Override
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String n) {
		nombre = n;
	}
	private Map<String, Variable> mapa_argumentos = new HashMap<String,
		Variable>();
	public void agregarArgumento(Variable v) {
		mapa_argumentos.put(v.getNombre(), v);
	}
	@Override
	public Variable [] getArgumentosArray() {
		return mapa_argumentos.values().toArray(new
			Variable[mapa_argumentos.size()]);
	}
	@Override
	public void setValorDeArgumento(String nombre, String valor) {
		Variable v = mapa_argumentos.get(nombre);
		if(v == null) return;
		v.setValor(valor);
	}
	@Override
	public String getValorDeArgumento(String nombre) {
		Variable v = mapa_argumentos.get(nombre);
		if(v == null) return null;
		return v.getValor();
	}
	@Override
	public String toString() {
		return "#" + id + " " + nombre;
	}
}