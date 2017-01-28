package intelidomo.personal.modelos.nodepi;

import intelidomo.personal.modelos.SensorTemperatura;
/*** added by dNodepiTemperatura
 */
public class TemperaturaImp extends SensorTemperatura {
	public TemperaturaImp(NodepiSensors ctr) {
		setControlador(ctr);
	}
}