package intelidomo.completo.modelos.rodas;

import intelidomo.completo.modelos.SensorTemperatura;
/*** added by dRodasTemperatura
 */
public class ArduinoRodasTemp extends SensorTemperatura {
	public ArduinoRodasTemp(ArduinoRodas ar) {
		setControlador(ar);
	}
}