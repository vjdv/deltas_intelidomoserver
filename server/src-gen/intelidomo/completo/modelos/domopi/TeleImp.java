package intelidomo.completo.modelos.domopi;

import intelidomo.completo.modelos.Tele;
import intelidomo.completo.modelos.commands.PowerTV;
import intelidomo.completo.modelos.commands.ChannelTV;
/*** added by dDomopiTV
 */
public class TeleImp extends Tele {
	private final DomopiTV padre;
	public TeleImp(DomopiTV drpi) {
		setControlador(drpi);
		padre = drpi;
		addFuncion(new PowerTV(this));
		addFuncion(new ChannelTV(this));
	}
	@Override
	public void comando(String cmd) {
		padre.consumirServicio("/key/" + cmd);
	}
	@Override
	public void cambiarCanal(String chn) {
		padre.consumirServicio("/channel/" + chn);
	}
	@Override
	public void onoff() {
		padre.consumirServicio("/key/KEY_POWER");
	}
	@Override
	public void triggerEstado(String edo) {
		throw new UnsupportedOperationException();
	}
}