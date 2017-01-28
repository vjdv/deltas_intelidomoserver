package intelidomo.completo.server;

import intelidomo.idcp.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.bind.*;
import java.util.logging.*;
import java.io.*;
import java.net.*;
/*** added by dServer
 */
class ClienteTCP implements Cliente, Runnable {
	private final String endMessage = "quit";
	private DispatcherI dispatcher;
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	ClienteTCP(Socket sckt) {
		socket = sckt;
		dispatcher = DispatcherTCP.getInstancia();
		dispatcher.registrarCliente(this);
	}
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			ClienteStream cs = new ClienteStream(in);
			new Thread(cs).start();
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch(IOException ex) {
			Logger.getLogger("DomoLog").log(Level.SEVERE,
				"Servidor: No fue posible crear flujos IO de cliente " + getIP(), ex);
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
	public void despacharCadena(String str) {
		if(str.equals("")) return;
		System.out.println("Mensaje recibido:\n" + str);
		try {
			JAXBContext contexto = JAXBContext.newInstance(Mensaje.class);
			Unmarshaller u = contexto.createUnmarshaller();
			Mensaje m = ( Mensaje ) u.unmarshal(new StreamSource(new
					StringReader(str)));
			Mensaje respuesta = dispatcher.despacha(m);
			if(respuesta != null) enviarRespuesta(respuesta);
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"No fue posible interpretar mensaje >>>\n" + str.trim() +
				"\n<<< de cliente " + getIP(), ex);
		}
	}
	private synchronized void enviarRespuesta(Mensaje r) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Mensaje.class);
			Marshaller m = contexto.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			m.marshal(r, out);
			out.println();
		}
		catch(JAXBException ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Servidor: error al enviar respuesta a cliente " + getIP());
		}
	}
	public void envia(Mensaje msg) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Mensaje.class);
			Marshaller m = contexto.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			m.marshal(msg, out);
			out.println();
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Servidor: error al enviar respuesta a cliente " + getIP(), ex);
		}
	}
	public void cerrar() {
		try {
			dispatcher.removerCliente(this);
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
	class ClienteStream implements Runnable {
		BufferedReader stream;
		public ClienteStream(BufferedReader br) {
			stream = br;
		}
		@Override
		public void run() {
			try {
				StringBuilder mensaje = new StringBuilder();
				String fromServer;
				while((fromServer = stream.readLine()) != null) {
					if(fromServer.equals(endMessage)) {
						cerrar();
						return;
					}
					else if(fromServer.equals("")) {
						despacharCadena(mensaje.toString());
						mensaje = new StringBuilder();
					}
					else {
						mensaje.append(fromServer);
						mensaje.append("\n");
					}
				}
			}
			catch(IOException ex) {
				String [] params = {
					getIP(), ex.getMessage()
				};
				Logger.getLogger("DomoLog").log(Level.WARNING,
					"Servidor: Error IO cliente {0} debido a {1}", params);
			}
			catch(NullPointerException ex) {
				Logger.getLogger("DomoLog").log(Level.INFO,
					"Servidor: Conexión cerrada por cliente " + getIP(), ex);
			}
			finally {
				cerrar();
			}
		}
	}
}