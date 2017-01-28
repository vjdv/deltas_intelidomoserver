package intelidomo.plus.modelos.voz;

import java.util.*;
import intelidomo.plus.modelos.SensorTemperatura;
import intelidomo.estatico.Constantes;
/*** added by dVozTemperatura
 */
public class TemperaturaLink implements ChainLink {
	private final SensorTemperatura st;
	public TemperaturaLink(SensorTemperatura st) {
		this.st = st;
	}
	@Override
	public String atender(List<String> words) {
		if(st.getEstado().equals(Constantes.INDISPONIBLE)) return
		"Dispositivo no disponible";
		else {
			float val = st.getTemperatura();
			String consideracion = "muy frío";
			if(val > 35f) consideracion = "demasiado caliente";
			else if(val > 30f) consideracion = "muy cálido";
			else if(val > 27f) consideracion = "cálido";
			else if(val > 20f) consideracion = "adecuado";
			else if(val > 16f) consideracion = "frío";
			return "La temperatura es de " + st.getEstado() + "°C, se considera " +
			consideracion + ".";
		}
	}
	@Override
	public void addLink(List<String> words, ChainLink node) {
		throw new UnsupportedOperationException();
	}
}