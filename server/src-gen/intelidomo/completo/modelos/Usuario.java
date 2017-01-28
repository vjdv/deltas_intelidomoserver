package intelidomo.completo.modelos;

import intelidomo.completo.db.Persistente;
/*** added by dModelos
 */
public class Usuario implements Persistente {
	private String id, password, tipo, nombre, status;
	public void setId(Object id) {
		this.id = id.toString();
	}
	@Override
	public String getId() {
		return id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pasword) {
		this.password = pasword;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
}