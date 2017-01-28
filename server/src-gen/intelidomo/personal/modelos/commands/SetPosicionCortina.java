package intelidomo.personal.modelos.commands;

import intelidomo.personal.modelos.Cortina;
import intelidomo.estatico.Constantes;
/*** added by dCortina
 */
public class SetPosicionCortina extends Funcion {
	public Cortina cortina;
	public SetPosicionCortina(Cortina c) {
		cortina = c;
		setNombre("setPosition");
		Variable v1 = new Variable(Constantes.ENTERO, "position");
		agregarArgumento(v1);
	}
	public void ejecutar() throws Exception {
		cortina.setPosicion(getValorDeArgumento("position"));
	}
}