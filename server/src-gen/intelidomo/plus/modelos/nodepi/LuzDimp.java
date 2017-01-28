package intelidomo.plus.modelos.nodepi;

import intelidomo.estatico.Constantes;
import intelidomo.plus.modelos.LuzRegulable;
/*** added by dNodepiLucesDim
 */
public class LuzDimp extends LuzRegulable {
	private final NodepiDimmableLights padre;
	private final int num;
	public LuzDimp(NodepiDimmableLights ctr, int n) {
		setTipoEstado(Constantes.ENTERO);
		setControlador(ctr);
		padre = ctr;
		num = n;
	}
	public String getEstadosLuz() {
		return Constantes.ON_OFF;
	}
	@Override
	public void triggerEstado(String edo) {
		try {
			int edo_int = Integer.parseInt(edo);
			setIntensidad(edo_int);
		}
		catch(Exception ex) {
			setIntensidad(1);
		}
	}
	@Override
	public void encender() {
		setIntensidad(100);
	}
	@Override
	public void apagar() {
		setIntensidad(0);
	}
	@Override
	public void setIntensidad(int i) {
		padre.obtenerValores(num, i);
	}
}