package intelidomo.completo.modelos;

import intelidomo.estatico.Constantes;
/*** added by dHumedad
 */
public abstract class SensorHumedad extends Dispositivo {
	public SensorHumedad() {
		super(false);
		setCategoria(Constantes.SENSOR_HUMEDAD);
		setTipoEstado(Constantes.DECIMAL);
		setEstadoSoloLectura(true);
	}
	public float getHumedad() {
		return Float.parseFloat(getEstado());
	}
	@Override
	public void triggerEstado(String edo) {
		throw new UnsupportedOperationException();
	}
}