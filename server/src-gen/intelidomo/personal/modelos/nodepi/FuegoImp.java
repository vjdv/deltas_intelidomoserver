package intelidomo.personal.modelos.nodepi;

import intelidomo.estatico.Constantes;
import intelidomo.personal.modelos.Alarma;
/*** added by dNodepiFuego
 */
public class FuegoImp extends Alarma {
	public FuegoImp(NodepiSensors ctr) {
		setControlador(ctr);
		setSubcategoria(Constantes.FUEGO);
	}
}