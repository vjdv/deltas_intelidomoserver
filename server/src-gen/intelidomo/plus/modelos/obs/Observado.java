package intelidomo.plus.modelos.obs;

/*** added by dModelos
 */
public interface Observado {
	void registrarObs(Observador obs);
	void removerObs(Observador obs);
	void notificarObs();
}