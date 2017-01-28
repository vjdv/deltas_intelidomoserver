package intelidomo.personal.modelos.voz;

import java.util.List;
/*** added by dVoz
 */
public interface ChainLink {
	String atender(List<String> words);
	void addLink(List<String> words, ChainLink node);
}