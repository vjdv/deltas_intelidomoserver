package intelidomo.cliente.modelos;

import intelidomo.idcp.AreaX;
import intelidomo.idcp.DispositivoX;
import intelidomo.idcp.EstadoX;
import intelidomo.idcp.FuncionX;
import java.util.Map;

/**
 *
 * @author Jassiel
 */
public class Traductor {
  public static Area xToArea(AreaX a){
    Area area = new Area();
    area.setId(a.id);
    area.setNombre(a.descripcion);
    area.setTipo(a.tipo);
    area.setGrupo(a.grupo);
    return area;
  }
  public static Dispositivo xToDispositivo(DispositivoX d){
    Dispositivo disp = new Dispositivo();
    disp.setId(d.id);
    disp.setArea(d.area);
    disp.setTipo(d.categoria);
    disp.setSubtipo(d.subcategoria);
    disp.setEstado(d.estado.valor);
    disp.setEstados(d.estado.tipo);
    d.lista_funciones.stream().forEach((f)->{
      Funcion func = new Funcion();
      func.setInstanceId(f.id);
      func.setNombre(f.nombre);
      f.lista_argumentos.stream().forEach((a)->{
        func.nuevoArgumento(a.nombre);
      });
      disp.agregarFuncion(func);
    });
    return disp;
  }
  public static FuncionX funcionToX(Funcion func){
    FuncionX f = new FuncionX();
    f.id = func.getInstanceId();
    func.getArgumentos().entrySet().stream().map((entry) -> {
      EstadoX arg = new EstadoX();
      arg.nombre = entry.getKey();
      arg.valor = entry.getValue();
      return arg;
    }).forEach((arg) -> {
      f.lista_argumentos.add(arg);
    });
    return f;
  }
}