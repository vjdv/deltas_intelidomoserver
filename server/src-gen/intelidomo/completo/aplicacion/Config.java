package intelidomo.completo.aplicacion;

/*** added by dBase
 */
public class Config {
	public static final long RUN_ID = System.currentTimeMillis();
	public static int PUERTO_TCP_SERVER = 4490;
	public static int PUERTO_SSL_SERVER = 4491;
	public static int PUERTO_HTTP_SERVER = 4480;
	public static int PUERTO_HTTPS_SERVER = 4481;
	public static String LOG_DIR = "/var/";
	public static int LOG_LEVEL = 2;
	public static void cargarValores() {
	}
}