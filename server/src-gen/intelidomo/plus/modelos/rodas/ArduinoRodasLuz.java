package intelidomo.plus.modelos.rodas;

import intelidomo.estatico.Constantes;
import intelidomo.plus.modelos.Luz;
import java.util.logging.*;
/*** added by dRodasLuces
 */
public class ArduinoRodasLuz extends Luz {
	private final ArduinoRodas padre;
	public ArduinoRodasLuz(ArduinoRodas ar) {
		setControlador(ar);
		padre = ar;
	}
	@Override
	public void triggerEstado(String edo) {
		if(edo.equals(Constantes.ON)) encender();
		else if(edo.equals(Constantes.OFF)) apagar();
		else Logger.getLogger("DomoLog").log(Level.INFO, "Estado " + edo +
			" para luz no válido en {0}", padre.getIP());
	}
	@Override
	public void encender() {
		padre.enviarComando("GET /Onlight$");
	}
	@Override
	public void apagar() {
		padre.enviarComando("GET /Offlight$");
	}
}