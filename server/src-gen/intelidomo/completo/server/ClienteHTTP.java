package intelidomo.completo.server;

import java.util.logging.*;
import java.io.*;
import java.net.*;
/*** added by dServer
 */
class ClienteHTTP implements Cliente {
	private DispatcherHTTP dispatcher;
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	ClienteHTTP(Socket sckt) {
		socket = sckt;
		dispatcher = DispatcherHTTP.getInstancia();
		try {
			InputStream is = socket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			out = new PrintWriter(socket.getOutputStream(), true);
			new Thread(new InputReader()).start();
			Logger.getLogger("DomoLog").log(Level.INFO, "Cliente aceptado {0}",
				getIP());
		}
		catch(IOException ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Servidor: No fue posible aceptar cliente {0}", getIP());
		}
	}
	public String getIP() {
		return socket.getRemoteSocketAddress().toString();
	}
	public String getIPv4() {
		InetSocketAddress isa = ( InetSocketAddress )
		socket.getRemoteSocketAddress();
		return isa.getAddress().toString();
	}
	public void envia(HttpResponse response) {
		out.print(response.getResponse());
		out.flush();
		cerrar();
	}
	public void cerrar() {
		try {
			in.close();
			out.close();
			socket.close();
			Logger.getLogger("DomoLog").log(Level.FINE,
				"Servidor: conexión cerrada adecuadamente de cliente" + getIP());
		}
		catch(IOException ex) {
			Logger.getLogger("DomoLog").log(Level.SEVERE,
				"Servidor: error al cerrar conexión adecuadamente de cliente " + getIP(),
				ex);
		}
	}
	class InputReader implements Runnable {
		@Override
		public void run() {
			try {
				String incoming;
				HttpRequest request = new HttpRequest();
				while((incoming = in.readLine()) != null) {
					System.out.println(incoming);
					if(incoming.isEmpty()) break;
					request.addHeader(incoming);
				}
				while(request.requiresContent()) {
					int val = in.read();
					if(val == - 1) {
						request.parseContent();
						break;
					}
					request.addContent(( char ) val);
				}
				HttpResponse r = dispatcher.despacha(request, ClienteHTTP.this);
				envia(r);
			}
			catch(HttpRequest.InvalidRequestException ex) {
				HttpResponse response = new HttpResponse(400);
				response.appendContent("<h1>Bad Request</h1><p>Wrong headers or protocol.</p>");
				envia(response);
			}
			catch(IOException ex) {
				String params [] = {
					getIP(), ex.getMessage()
				};
				Logger.getLogger("Domopi").log(Level.WARNING,
					"Error IO en cliente {0} por {1}", params);
			}
			finally {
				cerrar();
			}
		}
	}
}