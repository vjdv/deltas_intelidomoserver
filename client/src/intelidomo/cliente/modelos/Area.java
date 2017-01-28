package intelidomo.cliente.modelos;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jassiel
 */
public class Area {
  private int id;
  private final StringProperty grupo = new SimpleStringProperty();
  private final StringProperty nombre = new SimpleStringProperty();
  private final StringProperty tipo = new SimpleStringProperty();
  private final List<Dispositivo> lista_disp = new ArrayList<>();

  public void agregarDispositivo(String tipo, int num, String estados, int estado){
    Dispositivo d = new Dispositivo();
    d.setTipo(tipo);
    d.setId(num);
    d.setEstados(estados);
    //d.setEstado(estado);
    agregarDispositivo(d);
  }
  public void agregarDispositivo(Dispositivo d){
    d.setArea(id);
    lista_disp.add(d);
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public final String getGrupo() {
    return grupo.get();
  }
  public final void setGrupo(String grupo) {
    this.grupo.set(grupo);
  }
  public StringProperty grupoProperty(){
    return grupo;
  }
  public final String getNombre() {
    return nombre.get();
  }
  public final void setNombre(String nombre) {
    this.nombre.set(nombre);
  }
  public StringProperty nombreProperty(){
    return nombre;
  }
  public final String getTipo() {
    return tipo.get();
  }
  public final void setTipo(String tipo) {
    this.tipo.set(tipo);
  }
  public StringProperty tipoProperty(){
    return tipo;
  }
  public List<Dispositivo> getListaDispositivos(){
    return lista_disp;
  }
}