package intelidomo.completo.modelos.domopi;

import intelidomo.estatico.Constantes;
import intelidomo.completo.modelos.Luz;
import java.util.logging.*;
/*** added by dDomopiLuces
 */
public class LuzImp extends Luz {
	private final Domopi padre;
	private final char cid;
	public LuzImp(Domopi drpi, char id) {
		setControlador(drpi);
		padre = drpi;
		cid = id;
	}
	public String getEstadosLuz() {
		return Constantes.ON_OFF;
	}
	public char getIdChar() {
		return cid;
	}
	@Override
	public void triggerEstado(String edo) {
		if(edo.equals(Constantes.ON)) encender();
		else if(edo.equals(Constantes.OFF)) apagar();
		else Logger.getLogger("DomoLog").log(Level.INFO, "Estado " + edo +
			" para Luz " + cid + " no válido en " + padre.getIP());
	}
	@Override
	public void encender() {
		padre.enviarComando("SET LUZ" + cid + " ON");
	}
	@Override
	public void apagar() {
		padre.enviarComando("SET LUZ" + cid + " OFF");
	}
}