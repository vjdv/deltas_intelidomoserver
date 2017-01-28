package intelidomo.personal.server;

import intelidomo.personal.aplicacion.Config;
import java.util.logging.*;
import java.io.IOException;
import java.net.*;
/*** added by dServer
 */
public class ServidorTCP implements Runnable {
	ServerSocket conexion;
	public void run() {
		try {
			conexion = new ServerSocket(Config.PUERTO_TCP_SERVER);
			Logger.getLogger("DomoLog").log(Level.FINE, "Servidor: Servicio Iniciado");
			while(true) {
				ClienteTCP cliente = new ClienteTCP(conexion.accept());
				Logger.getLogger("DomoLog").log(Level.INFO,
					"Servidor: conexion aceptada en " + cliente.getIP());
				new Thread(cliente).start();
			}
		}
		catch(IOException ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"ServidorComandos: No fue posible inciar ServerSocket en puerto" +
				Config.PUERTO_TCP_SERVER, ex);
		}
	}
}