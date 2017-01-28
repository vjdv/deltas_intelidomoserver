package intelidomo.personal.modelos.nodepi;

import intelidomo.personal.modelos.SensorHumedad;
/*** added by dNodepiHumedad
 */
public class HumedadImp extends SensorHumedad {
	public HumedadImp(NodepiSensors ctr) {
		setControlador(ctr);
	}
}