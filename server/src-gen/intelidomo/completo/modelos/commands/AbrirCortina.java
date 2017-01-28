package intelidomo.completo.modelos.commands;

import intelidomo.completo.modelos.Cortina;
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