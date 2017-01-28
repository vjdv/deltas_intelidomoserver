package intelidomo.completo.modelos.nodepi;

import intelidomo.estatico.Constantes;
import intelidomo.completo.modelos.Alarma;
/*** added by dNodepiGas
 */
public class GasImp extends Alarma {
	public GasImp(NodepiSensors ctr) {
		setControlador(ctr);
		setSubcategoria(Constantes.GAS);
	}
}