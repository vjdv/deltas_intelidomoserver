package intelidomo.cliente.modelos;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jassiel
 */
public class Funcion {
  private int instanceId;
  private String nombre;
  private Map<String,String> argumentos = new HashMap<>();
  
  public int getInstanceId() {
    return instanceId;
  }
  public void setInstanceId(int instanceId) {
    this.instanceId = instanceId;
  }
  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public Map<String,String> getArgumentos() {
    return argumentos;
  }
  public void nuevoArgumento(String arg){
    argumentos.put(arg, "");
  }
  public void setValorParaArgumento(String arg, String val){
    if(argumentos.get(arg)!=null) argumentos.put(arg, val);
  }
}