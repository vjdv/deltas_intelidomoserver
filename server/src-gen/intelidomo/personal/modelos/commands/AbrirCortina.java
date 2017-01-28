package intelidomo.personal.modelos.commands;

import intelidomo.personal.modelos.Cortina;
/*** added by dCortina
 */
public class AbrirCortina extends Funcion {
	public Cortina cortina;
	public AbrirCortina(Cortina c) {
		cortina = c;
		setNombre("open");
	}
	public void ejecutar() throws Exception {
		cortina.abrir();
	}
}