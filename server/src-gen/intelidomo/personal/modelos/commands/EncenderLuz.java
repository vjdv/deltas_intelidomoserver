package intelidomo.personal.modelos.commands;

import intelidomo.personal.modelos.Luz;
/*** added by dLuces
 */
public class EncenderLuz extends Funcion {
	public Luz luz;
	public EncenderLuz(Luz l) {
		this();
		luz = l;
	}
	public EncenderLuz() {
		setNombre("encenderLuz");
		setDescripcion("Encender luz");
	}
	public void ejecutar() throws Exception {
		luz.encender();
	}
}