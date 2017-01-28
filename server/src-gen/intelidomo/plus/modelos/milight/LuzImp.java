package intelidomo.plus.modelos.milight;

import intelidomo.plus.modelos.LuzRGB;
import intelidomo.estatico.Constantes;
import java.awt.Color;
/*** added by dMilight
 */
public class LuzImp extends LuzRGB {
	private final MiLightClient padre;
	public LuzImp(MiLightClient padre) {
		setControlador(padre);
		setEstado(Constantes.DISPONIBLE);
		this.padre = padre;
	}
	@Override
	public void encender() {
		padre.enviarComando(new byte [] {
				0x22, 0x00, 0x55
			});
	}
	@Override
	public void apagar() {
		padre.enviarComando(new byte [] {
				0x21, 0x00, 0x55
			});
	}
	@Override
	public void setColor(String c) {
		String cps [] = c.split(",");
		int r = Integer.parseInt(cps[0]);
		int g = Integer.parseInt(cps[1]);
		int b = Integer.parseInt(cps[2]);
		setColor(r, g, b);
	}
	@Override
	public void setColor(int r, int g, int b) {
		float [] hsv = new float[3];
		Color.RGBtoHSB(r, g, b, hsv);
		float h = hsv[0];
		float s = hsv[1];
		float v = hsv[2];
		int hue_int = Math.round(h * 255);
		hue_int = 255 - hue_int;
		String hex_str = Integer.toHexString(hue_int);
		int hex_int = Integer.parseInt(hex_str, 16);
		byte hue = ( byte ) hex_int;
		hue -= ( byte ) 80;
		if(s == 0f && v == 100) {
			encender();
			padre.enviarComando(new byte [] {
					0x28, 0x00, 0x55
				});
		}
		else if(s == 0 && v == 0) {
			apagar();
		}
		else {
			encender();
			padre.enviarComando(new byte [] {
					0x20, hue, 0x55
				});
		}
	}
	@Override
	public void triggerEstado(String edo) {
		switch(edo) {
			case Constantes.ON : encender();
			break;
			case Constantes.OFF : apagar();
			break;
			default : setColor(edo);
			break;
		}
	}
}