package intelidomo.plus.modelos.commands;

import intelidomo.plus.modelos.DispositivoI;
import intelidomo.estatico.Constantes;
/*** added by dModelos
 */
public class CambiarEstado extends Funcion {
	public DispositivoI dispositivo;
	public CambiarEstado(DispositivoI d) {
		this();
		dispositivo = d;
	}
	public CambiarEstado() {
		setNombre("setEstado");
		setDescripcion("Cambiar estado del dispositivo");
		Variable v1 = new Variable(Constantes.ON_OFF, "estado");
		agregarArgumento(v1);
	}
	public void ejecutar() throws Exception {
		dispositivo.triggerEstado(getValorDeArgumento("estado"));
	}
}