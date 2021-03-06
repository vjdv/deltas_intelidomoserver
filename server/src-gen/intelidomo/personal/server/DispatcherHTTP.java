package intelidomo.personal.server;

import intelidomo.idcp.Mensaje;
import intelidomo.personal.modelos.Usuario;
import intelidomo.personal.modelos.Casa;
import java.util.logging.*;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
/*** added by dServer* modified by dVoz
 */
public class DispatcherHTTP {
	private Casa casa = Casa.getInstancia();
	private static DispatcherHTTP self;
	/*** modified by dVoz
	 */
	public HttpResponse despacha(HttpRequest request, Cliente cliente) {
		if(request.uriEquals("POST /text_command.do")) {
			String txt = decode(request.datos_post.get("text"));
			String respuesta = casa.despacharComandoTexto(txt);
			Mensaje m = RespuestaFactory.respuestaVoz(respuesta);
			String content = RespuestaFactory.mensaje2string(m);
			HttpResponse response = new HttpResponse(200);
			response.setContentType("text/plain");
			response.appendContent(content);
			return response;
		}
		else return despacha_original0(request, cliente);
	}
	private String decode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		}
		catch(UnsupportedEncodingException ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"Servidor: error decodificando", ex);
			return null;
		}
	}
	public static DispatcherHTTP getInstancia() {
		if(self == null) self = new DispatcherHTTP();
		return self;
	}
	/*** modified by dVoz
	 */
	public HttpResponse despacha_original0(HttpRequest request, Cliente cliente)
	{
		HttpResponse response;
		if(request.uriEquals("GET /")) {
			response = new HttpResponse(200);
			response.appendContent("<h1>Intelidomo</h1><p>Home automation.</p>");
		}
		else if(request.uriEquals("POST /login.do")) {
			String u = request.datos_post.get("user");
			String p = request.datos_post.get("password");
			Usuario user = SessionPool.credencialValida(u, p);
			response = new HttpResponse(200);
			response.setContentType("text/plain");
			if(user == null) {
				Mensaje m = RespuestaFactory.loginFailed();
				String content = RespuestaFactory.mensaje2string(m);
				response.appendContent(content);
			}
			else {
				Session s = SessionPool.getSession(user, cliente);
				Mensaje m = RespuestaFactory.loginSuccess(s);
				String content = RespuestaFactory.mensaje2string(m);
				response.appendContent(content);
			}
		}
		else if(request.uriEquals("GET /info.do")) {
			String token = request.datos_get.get("token");
			boolean token_valido =(token == null) ? false :
			SessionPool.tokenValido(token, cliente);
			if(token_valido) {
				Mensaje m = RespuestaFactory.infoDomotica();
				String content = RespuestaFactory.mensaje2string(m);
				response = new HttpResponse(200);
				response.setContentType("text/plain");
				response.appendContent(content);
			}
			else {
				Mensaje m = RespuestaFactory.mensaje401();
				String content = RespuestaFactory.mensaje2string(m);
				response = new HttpResponse(401);
				response.setContentType("text/plain");
				response.appendContent(content);
			}
		}
		else {
			response = new HttpResponse(404);
			response.appendContent("<h1>Not Found</h1><p>Document was not found on this server.</p>");
		}
		return response;
	}
}