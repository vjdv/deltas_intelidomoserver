package intelidomo.cliente.modelos;

import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jassiel
 */
public class Dispositivo {
  private int id,area;
  private String tipo,subtipo,estados;
  private final StringProperty estado = new SimpleStringProperty();
  private final Map<String,Funcion> funciones = new HashMap<>();
  
  public void agregarFuncion(Funcion f){
    funciones.put(f.getNombre(), f);
  }
  public Funcion getFuncion(String nombre){
    return funciones.get(nombre);
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getArea() {
    return area;
  }
  public void setArea(int area) {
    this.area = area;
  }
  public final String getEstado() {
    return estado.get();
  }
  public final void setEstado(String estado) {
    this.estado.set(estado);
  }
  public StringProperty estadoProperty(){
    return estado;
  }
  public String getTipo() {
    return tipo;
  }
  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  public String getEstados() {
    return estados;
  }
  public void setEstados(String estados) {
    this.estados = estados;
  }
  public String getSubtipo() {
    return subtipo;
  }
  public void setSubtipo(String subtipo) {
    this.subtipo = subtipo;
  }
}