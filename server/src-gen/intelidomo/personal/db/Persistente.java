package intelidomo.personal.db;

/*** added by dDatabase
 */
public interface Persistente {
	void setId(Object id);
	Object getId();
	String sqlConsultar();
	String sqlInsertar();
	String sqlActualizar();
	String sqlEliminar();
}