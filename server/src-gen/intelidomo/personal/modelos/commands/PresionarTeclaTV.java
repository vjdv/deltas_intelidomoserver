package intelidomo.personal.modelos.commands;

import intelidomo.estatico.Constantes;
import intelidomo.personal.modelos.Tele;
/*** added by dTV
 */
public class PresionarTeclaTV extends Funcion {
	public Tele tv;
	public PresionarTeclaTV(Tele t) {
		tv = t;
		setNombre("presionarTecla");
		setDescripcion("Como si presionaras una tecla");
		Variable v1 = new Variable(Constantes.CADENA, "tecla");
		agregarArgumento(v1);
	}
	public void ejecutar() throws Exception {
		tv.comando(getValorDeArgumento("tecla"));
	}
}