package intelidomo.completo.modelos.rules;

import intelidomo.completo.modelos.Dispositivo;
import javax.swing.Timer;
import java.util.*;
/*** added by dRules
 */
public class Fabrica {
	private Map<Dispositivo, Regla> rules_map = new HashMap<Dispositivo,
		Regla>();
	private List<Regla> rules_timer = new ArrayList<Regla>();
	public static int curHora = - 1, curMinuto = - 1, curSegundo = - 1;
	public Fabrica() {
		java.awt.event.ActionListener actionListener = new
		java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
				Calendar calendar = Calendar.getInstance();
				curHora = calendar.get(Calendar.HOUR_OF_DAY);
				curMinuto = calendar.get(Calendar.MINUTE);
				curSegundo = calendar.get(Calendar.SECOND);
				if(curSegundo == 0) for(Regla r : rules_timer) {
					if(r.evaluar() == true) r.ejecutar();
				}
			}
		};
		Timer timer = new Timer(999, actionListener);
		timer.start();
	}
}