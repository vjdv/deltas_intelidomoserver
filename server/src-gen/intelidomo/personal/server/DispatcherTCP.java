package intelidomo.personal.server;

import intelidomo.idcp.*;
import intelidomo.personal.modelos.obs.Observador;
import intelidomo.personal.modelos.obs.Observado;
import intelidomo.personal.modelos.Dispositivo;
import intelidomo.personal.modelos.Casa;
import java.util.*;
/*** added by dServer
 */
public class DispatcherTCP implements DispatcherI, Observador {
	private Casa casa = Casa.getInstancia();
	private static DispatcherTCP self;
	private DispatcherTCP() {
		casa.registrarObs(this);
	}
	@Override
	public void actualizarObs(Observado obj) {
		if(!(obj instanceof Casa)) return;
		final List<Dispositivo> lista = casa.getListaCambios();
		Mensaje m = RespuestaFactory.eventoEstados(lista);
		for(ClienteTCP c : clientes_list) c.envia(m);
	}
	public Mensaje despacha(Mensaje m) {
		if(m.tipo != Mensaje.COMANDO) return null;
		if(m.accion.equals(Mensaje.EJECUTAR_FUNCIONES)) {
			for(FuncionX f : m.ejecutar.lista_funciones) {
				Map<String, String> args = new HashMap<String, String>();
				for(EstadoX arg : f.lista_argumentos) args.put(arg.nombre, arg.valor);
				casa.ejecutarFuncion(f.id, args);
			}
		}
		return null;
	}
	private List<ClienteTCP> clientes_list = new ArrayList<ClienteTCP>();
	@Override
	public void registrarCliente(Cliente c) {
		clientes_list.add(( ClienteTCP ) c);
	}
	@Override
	public void removerCliente(Cliente c) {
		clientes_list.remove(c);
	}
	public static DispatcherTCP getInstancia() {
		if(self == null) self = new DispatcherTCP();
		return self;
	}
}