package intelidomo.personal.modelos.voz;

import java.util.*;
import intelidomo.personal.modelos.commands.Funcion;
/*** added by dVoz
 */
public class TextCommands {
	private TextLink raiz = new TextLink();
	public String parse(String txt) {
		System.out.println("Atendiendo: " + txt);
		List<String> words = words2list(txt);
		return raiz.atender(words);
	}
	public void addComando(String cmd, Funcion target) {
		List<String> words = words2list(cmd);
		FuncionLink flink = new FuncionLink(target);
		raiz.addLink(words, flink);
		System.out.println("comando agregado: " + cmd);
	}
	public void addComando(String cmd, ChainLink target) {
		List<String> words = words2list(cmd);
		raiz.addLink(words, target);
		System.out.println("comando agregado: " + cmd);
	}
	public List<String> words2list(String txt) {
		String words [] = txt.split(" ");
		List<String> words_list = new ArrayList<String>();
		for(String word : words) words_list.add(word);
		return words_list;
	}
	public static String number2str(int num) {
		String nums_txt [] = {
			"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho",
			"nueve", "diez", "once", "doce", "trece", "catorce", "quince"
		};
		return nums_txt[num];
	}
}