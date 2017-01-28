package intelidomo.plus.modelos.voz;

import java.util.*;
import java.util.logging.*;
import intelidomo.plus.modelos.commands.Funcion;
/*** added by dVozTV
 */
public class CanalTvLink implements ChainLink {
	private final Funcion funcion;
	public CanalTvLink(Funcion f) {
		funcion = f;
	}
	@Override
	public String atender(List<String> words) {
		if(words.size() != 1) return null;
		String channel = words.iterator().next();
		try {
			funcion.setValorDeArgumento("canal", channel);
			funcion.ejecutar();
			return "¡Listo!";
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.INFO, "Error ejecutando función",
				ex);
			return "Error de ejecución";
		}
	}
	@Override
	public void addLink(List<String> words, ChainLink node) {
		throw new UnsupportedOperationException();
	}
}