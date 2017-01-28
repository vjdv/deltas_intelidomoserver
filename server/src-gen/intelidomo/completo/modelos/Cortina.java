package intelidomo.completo.modelos;

import intelidomo.estatico.Constantes;
import intelidomo.completo.modelos.commands.AbrirCortina;
import intelidomo.completo.modelos.commands.CerrarCortina;
import intelidomo.completo.modelos.commands.SetPosicionCortina;
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