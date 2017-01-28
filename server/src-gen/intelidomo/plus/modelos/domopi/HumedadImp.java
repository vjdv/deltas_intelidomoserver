package intelidomo.plus.modelos.domopi;

import intelidomo.plus.modelos.SensorHumedad;
/*** added by dDomopiHumedad
 */
public class HumedadImp extends SensorHumedad {
	private final char cid;
	public HumedadImp(Domopi drpi, char id) {
		setControlador(drpi);
		cid = id;
	}
	public char getIdChar() {
		return cid;
	}
}