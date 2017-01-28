package intelidomo.completo.modelos.nodepi;

import intelidomo.completo.modelos.Controlador;
import intelidomo.completo.modelos.Casa;
import java.io.*;
import java.net.*;
import java.util.logging.*;
import intelidomo.estatico.Constantes;
/*** added by dNodepiCortina
 */
public class CortinaController extends Controlador {
	private Casa casa = Casa.getInstancia();
	private CortinaImp cortina;
	public CortinaController() {
		setTipo("Cortina Controller");
	}
	public void constructor() {
		cortina = new CortinaImp(this);
		casa.registrarCortina(cortina);
	}
	@Override
	public void conectar() {
		throw new UnsupportedOperationException();
	}
	public void interpretar(String msg) {
		if(msg == null || msg.isEmpty()) return;
		msg = msg.replaceAll("#", "");
		String partes [] = msg.split("\\|");
		if(partes[0].equals("position")) {
			cortina.setEstado(partes[1]);
			cortina.lastUpdate = System.currentTimeMillis();
		}
		if(hayCambios()) notificarObs();
	}
	public void enviarComando(String cmd) {
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		try {
			socket = new Socket(getIP(), 80);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(Exception ex) {
			String params [] = {
				getIP(), ex.getMessage()
			};
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Error conexión con cortina IP:{0} EX:{1}", params);
			cortina.setEstado(Constantes.INDISPONIBLE);
			if(hayCambios()) notificarObs();
			return;
		}
		try {
			out.print(cmd);
			out.flush();
			String mensaje;
			while((mensaje = in.readLine()) != null) {
				interpretar(mensaje);
			}
			in.close();
			out.close();
			socket.close();
		}
		catch(IOException ex) {
			Logger.getLogger("DomoLog").log(Level.SEVERE, "Conexión perdida IP {0}",
				getIP());
			cortina.setEstado(Constantes.INDISPONIBLE);
		}
		if(hayCambios()) notificarObs();
	}
}