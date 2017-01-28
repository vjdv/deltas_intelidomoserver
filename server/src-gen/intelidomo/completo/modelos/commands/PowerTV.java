package intelidomo.completo.modelos.commands;

import intelidomo.completo.modelos.Tele;
/*** added by dTV
 */
public class PowerTV extends Funcion {
	public Tele tv;
	public PowerTV(Tele t) {
		tv = t;
		setNombre("power");
	}
	public void ejecutar() throws Exception {
		tv.onoff();
	}
}