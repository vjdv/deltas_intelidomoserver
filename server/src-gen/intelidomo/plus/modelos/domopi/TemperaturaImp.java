package intelidomo.plus.modelos.domopi;

import intelidomo.plus.modelos.SensorTemperatura;
/*** added by dDomopiTemperatura
 */
public class TemperaturaImp extends SensorTemperatura {
	private final char cid;
	public TemperaturaImp(Domopi drpi, char id) {
		setControlador(drpi);
		cid = id;
	}
	public char getIdChar() {
		return cid;
	}
}