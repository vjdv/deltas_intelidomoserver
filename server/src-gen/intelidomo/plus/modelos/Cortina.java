package intelidomo.plus.modelos;

import intelidomo.estatico.Constantes;
import intelidomo.plus.modelos.commands.AbrirCortina;
import intelidomo.plus.modelos.commands.CerrarCortina;
import intelidomo.plus.modelos.commands.SetPosicionCortina;
/*** added by dCortina
 */
public abstract class Cortina extends Dispositivo {
	public Cortina() {
		super(true);
		setCategoria(Constantes.CORTINA);
		setTipoEstado(Constantes.ENTERO);
		setEstadoSoloLectura(false);
		addFuncion(new AbrirCortina(this));
		addFuncion(new CerrarCortina(this));
		addFuncion(new SetPosicionCortina(this));
	}
	public abstract void setPosicion(String pos);
	public abstract void abrir();
	public abstract void cerrar();
}