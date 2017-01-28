package intelidomo.plus.modelos.commands;

import intelidomo.estatico.Constantes;
import intelidomo.plus.modelos.Tele;
/*** added by dTV
 */
public class ChannelTV extends Funcion {
	public Tele tv;
	public ChannelTV(Tele t) {
		tv = t;
		setNombre("setChannel");
		Variable v1 = new Variable(Constantes.CADENA, "canal");
		agregarArgumento(v1);
	}
	public void ejecutar() throws Exception {
		tv.cambiarCanal(getValorDeArgumento("canal"));
	}
}