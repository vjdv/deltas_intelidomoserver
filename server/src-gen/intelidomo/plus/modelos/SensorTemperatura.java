package intelidomo.plus.modelos;

import intelidomo.estatico.Constantes;
/*** added by dTemperatura
 */
public abstract class SensorTemperatura extends Dispositivo {
	public SensorTemperatura() {
		super(false);
		setCategoria(Constantes.SENSOR_TEMPERATURA);
		setTipoEstado(Constantes.DECIMAL);
		setEstadoSoloLectura(true);
	}
	public float getTemperatura() {
		return Float.parseFloat(getEstado());
	}
	@Override
	public void triggerEstado(String edo) {
		throw new UnsupportedOperationException();
	}
}