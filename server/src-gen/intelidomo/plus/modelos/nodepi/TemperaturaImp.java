package intelidomo.plus.modelos.nodepi;

import intelidomo.plus.modelos.SensorTemperatura;
/*** added by dNodepiTemperatura
 */
public class TemperaturaImp extends SensorTemperatura {
	public TemperaturaImp(NodepiSensors ctr) {
		setControlador(ctr);
	}
}