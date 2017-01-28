package intelidomo.completo.modelos.nodepi;

import intelidomo.completo.modelos.SensorHumedad;
/*** added by dNodepiHumedad
 */
public class HumedadImp extends SensorHumedad {
	public HumedadImp(NodepiSensors ctr) {
		setControlador(ctr);
	}
}