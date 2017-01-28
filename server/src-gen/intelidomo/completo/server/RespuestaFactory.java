package intelidomo.completo.server;

import intelidomo.idcp.*;
import intelidomo.completo.modelos.Area;
import intelidomo.completo.modelos.Casa;
import intelidomo.completo.modelos.Dispositivo;
import intelidomo.completo.modelos.Usuario;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
/*** added by dServer
 */
public class RespuestaFactory {
	static Casa casa = Casa.getInstancia();
	public static Mensaje errorLogin(String detalle) {
		Mensaje r = new Mensaje();
		r.tipo = Mensaje.FALLO;
		r.accion = Mensaje.ACCESO;
		r.detalle = detalle;
		return r;
	}
	public static Mensaje loginCorrecto(Usuario u) {
		Mensaje r = new Mensaje();
		r.usuario = new UsuarioX();
		r.usuario.clave = u.getId();
		r.usuario.nombre = u.getNombre();
		r.usuario.tipo = u.getTipo();
		r.tipo = Mensaje.EXITO;
		r.accion = Mensaje.ACCESO;
		return r;
	}
	public static Mensaje infoDomotica() {
		Mensaje r = new Mensaje();
		r.tipo = Mensaje.EXITO;
		r.accion = Mensaje.INFORMACION_DOMOTICA;
		for(Area area : casa.getAreasArray()) {
			r.lista_areas.add(Traductor.areaToX(area));
		}
		for(Dispositivo d : casa.getDispositivosArray()) {
			r.lista_dispositivos.add(Traductor.dispositivoToX(d));
		}
		return r;
	}
	public static Mensaje eventoEstados(List<Dispositivo> lista) {
		Mensaje r = new Mensaje();
		r.tipo = Mensaje.EVENTO;
		r.accion = Mensaje.ACTUALIZAR_ESTADOS;
		for(Dispositivo sd : lista) {
			DispositivoX d = Traductor.dispositivoToX(sd);
			r.lista_dispositivos.add(d);
		}
		return r;
	}
	public static Mensaje mensajeBase() {
		Mensaje r = new Mensaje();
		r.tipo = Mensaje.EXITO;
		r.accion = "base";
		r.lista_servicios.add(new ServicioX("login", "/login.post"));
		r.lista_servicios.add(new ServicioX("allinfo", "/info.get"));
		return r;
	}
	public static Mensaje mensaje404() {
		Mensaje r = new Mensaje();
		r.tipo = Mensaje.UNKNOWN;
		r.accion = "404";
		r.detalle = "action or url described no exists";
		return r;
	}
	public static Mensaje mensaje401() {
		Mensaje r = new Mensaje();
		r.tipo = Mensaje.ILLEGAL;
		r.accion = "401";
		r.detalle = "valid token is required";
		return r;
	}
	public static Mensaje loginFailed() {
		Mensaje r = new Mensaje();
		r.tipo = Mensaje.ILLEGAL;
		r.accion = "LOGIN";
		r.detalle = "login has failed";
		return r;
	}
	public static Mensaje loginSuccess(Session s) {
		Mensaje r = new Mensaje();
		r.tipo = Mensaje.EXITO;
		r.accion = "LOGIN";
		r.usuario = new UsuarioX();
		r.usuario.clave = s.usuario.getId();
		r.usuario.nombre = s.usuario.getNombre();
		r.usuario.tipo = s.usuario.getTipo();
		r.usuario.token = s.id;
		r.usuario.token_expire = s.expire;
		return r;
	}
	public static String mensaje2string(Mensaje msg) {
		try {
			StringWriter sw = new StringWriter();
			JAXBContext contexto = JAXBContext.newInstance(Mensaje.class);
			Marshaller m = contexto.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			m.marshal(msg, sw);
			return sw.toString();
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING, "Error al parsear mensaje",
				ex);
			return null;
		}
	}
}