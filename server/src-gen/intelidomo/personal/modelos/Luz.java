package intelidomo.personal.modelos;

import intelidomo.estatico.Constantes;
import intelidomo.personal.modelos.commands.EncenderLuz;
import intelidomo.personal.modelos.commands.ApagarLuz;
/*** added by dLuces
 */
public abstract class Luz extends Dispositivo {
	public Luz() {
		setCategoria(Constantes.LUZ);
		setTipoEstado(Constantes.ON_OFF);
		addFuncion(new EncenderLuz(this));
		addFuncion(new ApagarLuz(this));
	}
	public abstract void encender();
	public abstract void apagar();
}