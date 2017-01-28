package intelidomo.plus.modelos.voz;

import java.util.*;
/*** added by dVoz
 */
public class TextLink implements ChainLink {
	private Map<String, ChainLink> map = new HashMap<String, ChainLink>();
	@Override
	public String atender(List<String> words) {
		String word = words.iterator().next();
		if(map.containsKey(word)) {
			words.remove(word);
			return map.get(word).atender(words);
		}
		else return null;
	}
	@Override
	public void addLink(List<String> words, ChainLink node) {
		String word = words.iterator().next();
		if(map.containsKey(word) && words.size() > 1) {
			ChainLink link = map.get(word);
			words.remove(word);
			link.addLink(words, node);
		}
		else if(! map.containsKey(word) && words.size() > 1) {
			TextLink link = new TextLink();
			words.remove(word);
			map.put(word, link);
			link.addLink(words, node);
		}
		else if(! map.containsKey(word) && words.size() == 1) {
			map.put(word, node);
		}
		else System.out.println("CAMINO INVALIDO!");
	}
}