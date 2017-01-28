package intelidomo.personal.modelos.commands;

import intelidomo.personal.modelos.Cortina;
/*** added by dCortina
 */
public class CerrarCortina extends Funcion {
	public Cortina cortina;
	public CerrarCortina(Cortina c) {
		cortina = c;
		setNombre("close");
	}
	public void ejecutar() throws Exception {
		cortina.cerrar();
	}
}