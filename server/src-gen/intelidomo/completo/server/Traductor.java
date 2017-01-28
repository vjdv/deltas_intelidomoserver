package intelidomo.completo.server;

import intelidomo.idcp.*;
import intelidomo.completo.modelos.Area;
import intelidomo.completo.modelos.Dispositivo;
import intelidomo.completo.modelos.commands.FuncionI;
import intelidomo.completo.modelos.commands.Variable;
/*** added by dServer
 */
public class Traductor {
	public static AreaX areaToX(Area a) {
		AreaX x = new AreaX();
		x.id = a.getId();
		x.grupo = a.getGrupo();
		x.descripcion = a.getNombre();
		x.tipo = a.getTipo();
		return x;
	}
	public static DispositivoX dispositivoToX(Dispositivo d) {
		DispositivoX x = new DispositivoX();
		x.id = d.getId();
		x.area = d.getArea();
		x.estado.valor = d.getEstado();
		x.estado.tipo = d.getTipoEstado();
		x.estado.solo_lectura = d.isEstadoSoloLectura();
		x.categoria = d.getCategoria();
		x.subcategoria = d.getSubcategoria();
		for(FuncionI f : d.getFunciones()) x.lista_funciones.add(funcionToX(f));
		return x;
	}
	public static FuncionX funcionToX(FuncionI f) {
		FuncionX x = new FuncionX();
		x.id = f.getId();
		x.nombre = f.getNombre();
		x.descripcion = f.getDescripcion();
		for(Variable arg : f.getArgumentosArray()) {
			EstadoX a = new EstadoX();
			a.nombre = arg.getNombre();
			a.tipo = arg.getTipo();
			x.lista_argumentos.add(a);
		}
		return x;
	}
}