package intelidomo.completo.modelos.domopi;

import intelidomo.completo.modelos.Controlador;
import intelidomo.completo.modelos.Casa;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;
/*** added by dDomopiTV
 */
public class DomopiTV extends Controlador {
	private Casa casa = Casa.getInstancia();
	public DomopiTV() {
		setTipo("DomopiTV Controller");
	}
	public void constructor() {
		TeleImp tv = new TeleImp(this);
		casa.registrarTV(tv);
	}
	@Override
	public void conectar() {
		throw new UnsupportedOperationException();
	}
	public String consumirServicio(String uri) {
		uri = "http://" + getIP() + ":" + getPuerto() + uri;
		try {
			URL url = new URL(uri);
			HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream is = connection.getInputStream();
			Scanner s = new Scanner(is);
			s.useDelimiter("\\A");
			String r = s.hasNext() ? s.next() : "";
			s.close();
			return r;
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.INFO, "Error conexión DomopiTV " +
				getIP(), ex);
			return null;
		}
	}
}