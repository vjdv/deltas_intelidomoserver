package intelidomo.completo.modelos.commands;

/*** added by dModelos
 */
public interface FuncionI {
	int getId();
	String getDescripcion();
	String getNombre();
	void ejecutar() throws Exception;
	Variable [] getArgumentosArray();
	void setValorDeArgumento(String k, String v);
	String getValorDeArgumento(String k);
}