package intelidomo.plus.modelos.nodepi;

import intelidomo.estatico.Constantes;
import intelidomo.plus.modelos.Alarma;
/*** added by dNodepiGas
 */
public class GasImp extends Alarma {
	public GasImp(NodepiSensors ctr) {
		setControlador(ctr);
		setSubcategoria(Constantes.GAS);
	}
}