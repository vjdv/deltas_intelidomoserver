package intelidomo.plus.modelos.rodas;

import intelidomo.plus.modelos.SensorTemperatura;
/*** added by dRodasTemperatura
 */
public class ArduinoRodasTemp extends SensorTemperatura {
	public ArduinoRodasTemp(ArduinoRodas ar) {
		setControlador(ar);
	}
}