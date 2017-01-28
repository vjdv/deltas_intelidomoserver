package intelidomo.completo.modelos;

import intelidomo.estatico.Constantes;
import java.util.logging.Level;
import java.util.logging.Logger;
/*** added by dAlarma
 */
public abstract class Alarma extends Dispositivo {
	public Alarma() {
		super(false);
		setCategoria(Constantes.ALARMA);
		setTipoEstado(Constantes.YES_NO);
		setEstadoSoloLectura(true);
		setEstadoDelay(4000);
	}
	public boolean hayIncidencia() {
		return getEstado().equals(Constantes.YES);
	}
	@Override
	public void setEstado(String edo) {
		super.setEstado(edo);
		if(edo.equals(Constantes.YES)) {
			Logger.getLogger("DomoLog").log(Level.INFO, "Alarma disparada {0}",
				getId());
		}
	}
	@Override
	public void triggerEstado(String edo) {
		throw new UnsupportedOperationException();
	}
}