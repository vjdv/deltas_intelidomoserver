package intelidomo.plus.modelos;

import intelidomo.plus.modelos.commands.FuncionI;
/*** added by dModelos
 */
public interface DispositivoI {
	void setId(int id);
	int getId();
	void setArea(int id);
	int getArea();
	void setCategoria(String cat);
	String getCategoria();
	void setSubcategoria(String cat);
	String getSubcategoria();
	void setControlador(Controlador c);
	Controlador getControlador();
	void triggerEstado(String edo);
	void setEstado(String edo);
	String getEstado();
	void setTipoEstado(String tipo);
	String getTipoEstado();
	void setEstadoSoloLectura(boolean b);
	boolean isEstadoSoloLectura();
	FuncionI [] getFunciones();
	FuncionI getFuncion(String n);
}