package intelidomo.plus.modelos.commands;

import intelidomo.plus.modelos.LuzRGB;
import intelidomo.estatico.Constantes;
/*** added by dLuzRGB
 */
public class SetColor extends Funcion {
	public LuzRGB luz;
	public SetColor(LuzRGB l) {
		luz = l;
		setNombre("setColor");
		Variable v1 = new Variable(Constantes.CADENA, "value");
		agregarArgumento(v1);
	}
	public void ejecutar() throws Exception {
		try {
			luz.setColor(getValorDeArgumento("value"));
		}
		catch(Exception ex) {
			luz.apagar();
		}
	}
}