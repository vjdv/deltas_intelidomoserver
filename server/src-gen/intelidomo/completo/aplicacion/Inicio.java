package intelidomo.completo.aplicacion;

import intelidomo.completo.db.Gestor;
import intelidomo.completo.modelos.Casa;
import intelidomo.completo.modelos.rules.Fabrica;
import intelidomo.completo.server.ServidorTCP;
import intelidomo.completo.server.ServidorHTTP;
/*** added by dBase* modified by dRules* modified by dServer
 */
public class Inicio {
	public Inicio() {
		constructor();
	}
	/*** modified by dRules* modified by dServer
	 */
	public void constructor() {
		constructor_original2();
		new Thread(new ServidorTCP()).start();
		new Thread(new ServidorHTTP()).start();
	}
	public static void main(String args []) {
		new Inicio();
	}
	/*** modified by dRules
	 */
	public void constructor_original0() {
		System.out.println("--- Iniciando Sistema Domótico v1.3 RUN_ID = " +
			Config.RUN_ID + " ---");
		Config.cargarValores();
		Bitacora.setMinLevel(Bitacora.MESSAGES);
		Gestor g = new Gestor();
		boolean exito = g.conectar();
		if(exito == false) {
			Bitacora.error("Hubo un problema al conectar a la base de datos, el servicio no se puede iniciar.");
			System.exit(0);
		}
		Gestor.setSingleton(g);
		Casa.getInstancia().constructor();
	}
	/*** modified by dRules* modified by dServer
	 */
	public void constructor_original2() {
		constructor_original0();
		new Fabrica();
	}
}