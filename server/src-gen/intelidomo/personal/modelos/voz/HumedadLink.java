package intelidomo.personal.modelos.voz;

import java.util.*;
import intelidomo.personal.modelos.SensorHumedad;
import intelidomo.estatico.Constantes;
/*** added by dVozHumd
 */
public class HumedadLink implements ChainLink {
	private final SensorHumedad sh;
	public HumedadLink(SensorHumedad sh) {
		this.sh = sh;
	}
	@Override
	public String atender(List<String> words) {
		if(sh.getEstado().equals(Constantes.INDISPONIBLE)) return
		"Dispositivo no disponible";
		else {
			float val = sh.getHumedad();
			String consideracion = "está debajo de lo recomendado";
			if(val > 80f) consideracion = "se considera un nivel muy alto";
			else if(val > 70f) consideracion = "por arriba de lo recomendado";
			else if(val > 30f) consideracion = "es adecuado";
			return "La humedad relativa es de " + sh.getEstado() + "%, " +
			consideracion + ".";
		}
	}
	@Override
	public void addLink(List<String> words, ChainLink node) {
		throw new UnsupportedOperationException();
	}
}