package intelidomo.plus.modelos;

import intelidomo.estatico.Constantes;
import intelidomo.plus.modelos.commands.PresionarTeclaTV;
/*** added by dTV
 */
public abstract class Tele extends Dispositivo {
	public Tele() {
		super(false);
		setCategoria(Constantes.TV);
		setTipoEstado(Constantes.CADENA);
		setEstadoSoloLectura(true);
		addFuncion(new PresionarTeclaTV(this));
	}
	public abstract void comando(String cmd);
	public abstract void cambiarCanal(String chn);
	public abstract void onoff();
}