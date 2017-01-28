package intelidomo.personal.modelos.voz;

import java.util.*;
import java.util.logging.*;
import intelidomo.personal.modelos.commands.Funcion;
/*** added by dVoz
 */
public class FuncionLink implements ChainLink {
	private final Funcion funcion;
	public FuncionLink(Funcion f) {
		funcion = f;
	}
	@Override
	public String atender(List<String> words) {
		try {
			funcion.ejecutar();
			return "¡Listo!";
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.INFO, "Error ejecutando función",
				ex);
			return null;
		}
	}
	@Override
	public void addLink(List<String> words, ChainLink node) {
		throw new UnsupportedOperationException();
	}
}