package intelidomo.plus.modelos.rules;

/*** added by dRules
 */
public abstract class ReglaHora extends Regla {
	private int hora = 0, minuto = 0;
	public boolean evaluar() {
		return(Fabrica.curHora == hora && Fabrica.curMinuto == minuto);
	}
	public void setHora(int x) {
		hora = x;
	}
	public int getHora() {
		return hora;
	}
	public void setMinuto(int x) {
		minuto = x;
	}
	public int getMinuto() {
		return minuto;
	}
}