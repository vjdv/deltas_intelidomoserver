package intelidomo.personal.server;

import intelidomo.personal.db.Gestor;
import intelidomo.personal.modelos.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.*;
/*** added by dServer
 */
public class SessionPool {
	static private Gestor g = Gestor.getSingleton();
	private static Map<String, Session> pool = new HashMap<String, Session>();
	public static Usuario credencialValida(String user, String pass) {
		if(user == null || pass == null) return null;
		else if(user.isEmpty() || pass.isEmpty()) return null;
		return g.obtenerUsuarioLogeado(user, pass);
	}
	public static boolean tokenValido(String token, Cliente c) {
		Session s = pool.get(token);
		if(s == null) return false;
		if(! s.ip.equals(c.getIPv4())) return false;
		return true;
	}
	public static Session getSession(Usuario u, Cliente c) {
		Session s = new Session();
		s.usuario = u;
		s.ip = c.getIPv4();
		s.expire =(System.currentTimeMillis() / 1000l) + 86400;
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update((u.getId() + s.ip + s.expire).getBytes());
			byte [] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashkey = bigInt.toString(16);
			pool.put(hashkey, s);
			s.id = hashkey;
			return s;
		}
		catch(NoSuchAlgorithmException ex) {
			Logger.getLogger("DomoLog").log(Level.SEVERE,
				"Servidor HTTP: No fue posible crear token_hash", ex);
			return null;
		}
	}
}