package intelidomo.completo.server;

import intelidomo.idcp.Mensaje;
/*** added by dServer
 */
public interface DispatcherI {
	void registrarCliente(Cliente c);
	void removerCliente(Cliente c);
	Mensaje despacha(Mensaje m) throws Exception;
}