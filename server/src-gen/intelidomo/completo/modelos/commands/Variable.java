package intelidomo.completo.modelos.commands;

import java.util.ArrayList;
import java.util.List;
/*** added by dModelos
 */
public class Variable {
	public Variable() {
	}
	public Variable(String tipo, String nombre) {
		this();
		this.tipo = tipo;
		this.nombre = nombre;
	}
	public Variable(String tipo, String nombre, String valor) {
		this(tipo, nombre);
		this.valor = valor;
	}
	private String valor, valor_minimo, valor_maximo;
	public void setValor(String x) {
		valor = x;
	}
	public String getValor() {
		return valor;
	}
	public void setValorMinimo(String x) {
		valor_minimo = x;
	}
	public String getValorMinimo() {
		return valor_minimo;
	}
	public void setValorMaximo(String x) {
		valor_maximo = x;
	}
	public String getValorMaximo() {
		return valor_maximo;
	}
	private String tipo, nombre, prefijo, sufijo;
	public void setTipo(String x) {
		tipo = x;
	}
	public String getTipo() {
		return tipo;
	}
	public void setNombre(String x) {
		nombre = x;
	}
	public String getNombre() {
		return nombre;
	}
	public void setPrefijo(String x) {
		prefijo = x;
	}
	public String getPrefijo() {
		return prefijo;
	}
	public void setSufijo(String x) {
		sufijo = x;
	}
	public String getSufijo() {
		return sufijo;
	}
	private Boolean solo_lectura;
	public void setSoloLectura(boolean x) {
		solo_lectura = x;
	}
	public Boolean isSoloLectura() {
		return solo_lectura;
	}
	public List<Variable> valores_permitidos = new ArrayList<Variable>();
}