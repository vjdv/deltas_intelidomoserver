package intelidomo.plus.modelos.commands;

import intelidomo.plus.modelos.Cortina;
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