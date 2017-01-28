package intelidomo.plus.modelos;

import intelidomo.estatico.Constantes;
/*** added by dLuzRGB
 */
public abstract class LuzRGB extends Luz {
	public LuzRGB() {
		setSubcategoria(Constantes.RGB);
		setTipoEstado(Constantes.CADENA);
	}
	public abstract void setColor(String i);
	public abstract void setColor(int r, int g, int b);
}