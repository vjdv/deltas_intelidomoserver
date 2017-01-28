package intelidomo.plus.modelos.voz;

import java.util.*;
import java.text.DecimalFormat;
import intelidomo.plus.modelos.SensorTemperatura;
import intelidomo.estatico.Constantes;
/*** added by dVozTemperatura
 */
public class TemperaturasLink implements ChainLink {
	private final DecimalFormat decimal = new DecimalFormat("#.#");
	private final Map<Integer, SensorTemperatura> st_map;
	public TemperaturasLink(Map<Integer, SensorTemperatura> stm) {
		st_map = stm;
	}
	@Override
	public String atender(List<String> words) {
		if(st_map.size() == 0) return "No hay sensores de temperatura registrados.";
		int i = 0;
		float v = 0;
		for(SensorTemperatura st : st_map.values()) {
			if(st.getEstado().equals(Constantes.INDISPONIBLE)) break;
			v += st.getTemperatura();
			i ++;
		}
		if(i == 0) return "No hay sensores de temperatura disponibles.";
		float val = v / i;
		String consideracion = "muy frío";
		if(val > 35f) consideracion = "demasiado caliente";
		else if(val > 30f) consideracion = "muy cálido";
		else if(val > 27f) consideracion = "cálido";
		else if(val > 20f) consideracion = "adecuado";
		else if(val > 16f) consideracion = "frío";
		return "La temperatura promedio es de " + decimal.format(val) +
		"°C, se considera " + consideracion + ".";
	}
	@Override
	public void addLink(List<String> words, ChainLink node) {
		throw new UnsupportedOperationException();
	}
}