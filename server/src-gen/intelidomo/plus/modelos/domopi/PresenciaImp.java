package intelidomo.plus.modelos.domopi;

import intelidomo.plus.modelos.SensorPresencia;
/*** added by dDomopiPresencia
 */
public class PresenciaImp extends SensorPresencia {
	private final char cid;
	public PresenciaImp(Domopi drpi, char id) {
		setControlador(drpi);
		cid = id;
	}
	public char getIdChar() {
		return cid;
	}
}