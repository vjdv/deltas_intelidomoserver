package intelidomo.completo.modelos.nodepi;

import intelidomo.completo.modelos.SensorTemperatura;
/*** added by dNodepiTemperatura
 */
public class TemperaturaImp extends SensorTemperatura {
	public TemperaturaImp(NodepiSensors ctr) {
		setControlador(ctr);
	}
}