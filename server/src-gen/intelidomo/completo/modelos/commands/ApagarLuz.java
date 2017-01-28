package intelidomo.completo.modelos.commands;

import intelidomo.completo.modelos.Luz;
/*** added by dLuces
 */
public class ApagarLuz extends Funcion {
	public Luz luz;
	public ApagarLuz(Luz l) {
		this();
		luz = l;
	}
	public ApagarLuz() {
		setNombre("apagarLuz");
		setDescripcion("Apaga la luz");
	}
	public void ejecutar() throws Exception {
		luz.apagar();
	}
}