package intelidomo.personal.modelos;

import intelidomo.estatico.Constantes;
/*** added by dPresencia
 */
public abstract class SensorPresencia extends Dispositivo {
	public SensorPresencia() {
		super(false);
		setCategoria(Constantes.SENSOR_PRESENCIA);
		setTipoEstado(Constantes.YES_NO);
		setEstadoSoloLectura(true);
	}
	public boolean hayPresencia() {
		return getEstado().equals(Constantes.YES);
	}
	@Override
	public void triggerEstado(String edo) {
		throw new UnsupportedOperationException();
	}
}