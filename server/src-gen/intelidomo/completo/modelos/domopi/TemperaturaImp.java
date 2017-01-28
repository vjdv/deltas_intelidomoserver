package intelidomo.completo.modelos.domopi;

import intelidomo.completo.modelos.SensorTemperatura;
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