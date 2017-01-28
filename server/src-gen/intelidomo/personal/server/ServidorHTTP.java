package intelidomo.personal.server;

import intelidomo.personal.aplicacion.Config;
import java.util.logging.*;
import java.io.IOException;
import java.net.*;
/*** added by dServer
 */
public class ServidorHTTP implements Runnable {
	ServerSocket conexion;
	public void run() {
		try {
			conexion = new ServerSocket(Config.PUERTO_HTTP_SERVER);
			Logger.getLogger("DomoLog").log(Level.FINE,
				"Servidor HTTP: Servicio Iniciado");
			while(true) new ClienteHTTP(conexion.accept());
		}
		catch(IOException ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Servidor HTTP: No fue posible inciar ServerSocket en puerto " +
				Config.PUERTO_TCP_SERVER, ex);
		}
	}
}