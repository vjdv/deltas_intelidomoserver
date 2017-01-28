package intelidomo.personal.modelos.nodepi;

import intelidomo.personal.modelos.Cortina;
import java.util.logging.*;
/*** added by dNodepiCortina
 */
public class CortinaImp extends Cortina {
	private final CortinaController padre;
	public long lastUpdate = 0;
	public CortinaImp(CortinaController ctr) {
		setControlador(ctr);
		padre = ctr;
		Thread t = new Thread(new Updater());
		t.setDaemon(true);
		t.start();
	}
	@Override
	public void triggerEstado(String edo) {
		setPosicion(edo);
	}
	@Override
	public void setPosicion(String pos) {
		try {
			int pos_int = Integer.parseInt(pos);
			padre.enviarComando("set position " + pos_int);
		}
		catch(NumberFormatException ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING, "Posición no válida: {0}",
				pos);
		}
	}
	@Override
	public void abrir() {
		padre.enviarComando("set position 0");
	}
	@Override
	public void cerrar() {
		padre.enviarComando("set position 100");
	}
	public class Updater implements Runnable {
		@Override
		public void run() {
			while(true) {
				long now = System.currentTimeMillis();
				if((now - lastUpdate) > 150000) padre.enviarComando("get position");
				try {
					Thread.sleep(15000);
				}
				catch(InterruptedException ex) {
					System.out.println("INTERRUPTED");
				}
			}
		}
	}
}