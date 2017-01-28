package intelidomo.personal.modelos;

import intelidomo.estatico.Constantes;
/*** added by dLuces
 */
public abstract class LuzRegulable extends Luz {
	public LuzRegulable() {
		setSubcategoria(Constantes.REGULABLE);
	}
	public abstract void setIntensidad(int i);
}