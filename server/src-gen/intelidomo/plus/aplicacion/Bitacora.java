package intelidomo.plus.aplicacion;

import java.util.logging.*;
import java.io.IOException;
/*** added by dBase
 */
public class Bitacora {
	public final static int ALL = 0, ALMOST_ALL = 1, MESSAGES = 2, NOTICES = 3,
	WARNINGS = 4, ERRORS = 5;
	private static int min_level = 2;
	static Logger logger = Logger.getLogger("DomoLog");
	static {
		setMinLevel(Config.LOG_LEVEL);
		try {
			FileHandler fh = new FileHandler(Config.LOG_DIR + "domo_" + Config.RUN_ID +
				".txt");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		}
		catch(IOException ex) {
			System.err.println("Bitácora: No fue posible crear archivo log");
			System.err.println(ex);
		}
		catch(SecurityException ex) {
			System.err.println("Bitácora: Problemas de seguridad");
			System.err.println(ex);
		}
	}
	public static void setMinLevel(int level) {
		if(min_level != level) {
			min_level = level;
			if(level == ALL) logger.setLevel(Level.ALL);
			if(level == ALMOST_ALL) logger.setLevel(Level.FINER);
			if(level == MESSAGES) logger.setLevel(Level.FINE);
			else if(level == NOTICES) logger.setLevel(Level.INFO);
			else if(level == WARNINGS) logger.setLevel(Level.WARNING);
			else if(level == ERRORS) logger.setLevel(Level.SEVERE);
			logger.warning("Log Level cambiado a " + level);
		}
	}
	public static void trace(String txt) {
		logger.finest(txt);
	}
	public static void ok(String txt) {
		logger.finer(txt);
	}
	public static void message(String txt) {
		logger.fine(txt);
	}
	public static void notice(String txt) {
		logger.info(txt);
	}
	public static void warning(String txt) {
		logger.warning(txt);
	}
	public static void error(String txt) {
		logger.severe(txt);
	}
}