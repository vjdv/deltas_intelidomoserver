package intelidomo.cliente.fxml;

import intelidomo.cliente.img.Recursos;
import intelidomo.cliente.modelos.Area;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jassiel
 */
public class AreaPane extends VBox{
  private final List<DispositivoPane> dispane_list = new ArrayList<>();
  private final Recursos recursos = Recursos.getInstancia();
  private final Label nombre_lb = new Label("AREA");
  private final Area area;
  private final ImageView img;
  public AreaPane(Area a){
    area = a;
    nombre_lb.textProperty().bind(area.nombreProperty());
    img = new ImageView(recursos.getImage("area_icon.png"));
    img.setFitWidth(60);
    img.setFitHeight(60);
    setSpacing(7);
    setAlignment(Pos.CENTER);
    setPrefWidth(100);
    getChildren().addAll(img,nombre_lb);
    getStyleClass().add("area_box");
  }
  public void agregarDispositivo(DispositivoPane dp){
    dispane_list.add(dp);
  }
  public List<DispositivoPane> getDispositivoPanes(){
    return dispane_list;
  }
  public Image getImage(){
    return img.getImage();
  }
}