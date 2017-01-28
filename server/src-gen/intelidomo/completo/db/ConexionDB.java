package intelidomo.completo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;
/*** added by dDatabase* modified by dPgsql
 */
public class ConexionDB {
	public Connection conn = null;
	private String server = "localhost";
	private String db = "nodefined";
	private String user = "postgres";
	private String pass = "postgres";
	private Exception last_exception = null;
	public ConexionDB() {
	}
	public ConexionDB(String s, String d, String u, String p) {
		this(d, u, p);
		server = s;
	}
	public ConexionDB(String d, String u, String p) {
		db = d;
		user = u;
		pass = p;
	}
	/*** modified by dPgsql
	 */
	public boolean conectar() {
		if(isConnected()) return true;
		try {
			if(conn != null) if(conn.isValid(0)) return true;
			Class.forName("org.postgresql.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:postgresql://" + server + "/" +
				db, user, pass);
			Logger.getLogger("DomoLog").log(Level.FINE,
				"Conexión PostgreSQL creada en " + this.toString());
			return true;
		}
		catch(SQLException ex) {
			last_exception = ex;
			Logger.getLogger("DomoLog").log(Level.SEVERE, "", ex);
		}
		catch(ClassNotFoundException ex) {
			last_exception = ex;
			Logger.getLogger("DomoLog").log(Level.SEVERE,
				"No se encontró la clase del driver Postgresql", ex);
		}
		catch(InstantiationException ex) {
			last_exception = ex;
			Logger.getLogger("DomoLog").log(Level.SEVERE,
				"No se pudo instanciar el driver Postgresql", ex);
		}
		catch(IllegalAccessException ex) {
			last_exception = ex;
			Logger.getLogger("DomoLog").log(Level.SEVERE, "", ex);
		}
		return false;
	}
	public synchronized int ejecutarComando(String psStatement) {
		int ret = 0;
		try {
			Statement stmt = conn.createStatement();
			ret = stmt.executeUpdate(psStatement);
		}
		catch(Exception ex) {
			last_exception = ex;
			System.err.println(ex);
			return - 1;
		}
		return ret;
	}
	public synchronized int ejecutarComando(ArrayList<String> pvStatement) {
		return ejecutarComando(pvStatement.toArray(new String[pvStatement.size()]));
	}
	public synchronized int ejecutarComando(String statements []) {
		int ret = 0;
		try {
			conn.setAutoCommit(false);
			for(String x : statements) {
				int r = ejecutarComando(x);
				if(r == - 1) throw new
				SQLException("Error en uno de los statement de la transacción");
			}
			conn.commit();
			conn.setAutoCommit(true);
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			}
			catch(SQLException ex) {
				System.err.println("Error al hacer rollback");
				System.err.println(ex);
			}
			return - 1;
		}
		return ret;
	}
	public boolean insertarObjeto(Persistente p) {
		return insertarObjeto(p, false);
	}
	public boolean insertarObjeto(Persistente p, boolean setid) {
		try {
			PreparedStatement ps = conn.prepareStatement(p.sqlInsertar(),
				Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if(setid == true) {
				if(rs.next()) p.setId(rs.getObject(1));
				else return false;
			}
		}
		catch(Exception ex) {
			last_exception = ex;
			System.err.println("Error al insertar a persistente " + p + " autosetid=" +
				setid);
			System.err.println(ex.toString());
			return false;
		}
		return true;
	}
	public boolean actualizarObjeto(Persistente p) {
		try {
			ejecutarComando(p.sqlActualizar());
		}
		catch(Exception ex) {
			last_exception = ex;
			System.err.println("Error al actualizar persistente");
			System.err.println(ex);
			return false;
		}
		return true;
	}
	public boolean eliminarObjeto(Persistente p) {
		try {
			ejecutarComando(p.sqlEliminar());
		}
		catch(Exception ex) {
			last_exception = ex;
			System.err.println("Error al eliminar persistente");
			System.err.println(ex);
			return false;
		}
		return true;
	}
	public boolean isConnected() {
		try {
			if(conn != null) if(conn.isValid(0)) return true;
		}
		catch(SQLException ex) {
			return false;
		}
		return false;
	}
	public void setServer(String s) {
		server = s;
	}
	public String getServer() {
		return server;
	}
	public void setDb(String d) {
		db = d;
	}
	public String getDb() {
		return db;
	}
	public void setUser(String u) {
		user = u;
	}
	public String getUser() {
		return user;
	}
	public void setPassword(String p) {
		pass = p;
	}
	public String getPassword() {
		return pass;
	}
	public void setLastException(Exception ex) {
		last_exception = ex;
	}
	public Exception getLastException() {
		return last_exception;
	}
}