package intelidomo.plus.modelos.milight;

import intelidomo.plus.modelos.Controlador;
import intelidomo.plus.modelos.Casa;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
/*** added by dMilight
 */
public class MiLightClient extends Controlador {
	Casa casa = Casa.getInstancia();
	LuzImp luz;
	public MiLightClient() {
		setTipo("MiLight Cliente APP");
		luz = new LuzImp(this);
		casa.registrarLuz(luz);
	}
	@Override
	public void conectar() {
	}
	public void enviarComando(byte sendData []) {
		try {
			final DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(getIP());
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
				IPAddress, 8899);
			clientSocket.send(sendPacket);
			esperar(100);
			clientSocket.close();
		}
		catch(Exception ex) {
			Logger.getLogger("DomoLog").log(Level.WARNING,
				"No se logró enviar comando", ex);
		}
	}
	private void esperar(long time) {
		try {
			Thread.sleep(time);
		}
		catch(InterruptedException ex) {
		}
	}
}