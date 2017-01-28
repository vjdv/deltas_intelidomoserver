package intelidomo.personal.modelos.commands;

import intelidomo.personal.modelos.LuzRegulable;
import intelidomo.estatico.Constantes;
/*** added by dLuces
 */
public class SetIntensidad extends Funcion {
	public LuzRegulable luz;
	public SetIntensidad(LuzRegulable l) {
		this();
		luz = l;
		Variable v1 = new Variable(Constantes.ENTERO, "value");
		agregarArgumento(v1);
	}
	public SetIntensidad() {
		setNombre("setIntensidad");
	}
	public void ejecutar() throws Exception {
		try {
			int val = Integer.parseInt(getValorDeArgumento("value"));
			luz.setIntensidad(val);
		}
		catch(Exception ex) {
			luz.setIntensidad(1);
		}
	}
}