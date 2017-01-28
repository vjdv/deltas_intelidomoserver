package intelidomo.cliente.fxml;

import intelidomo.cliente.Fachada;
import intelidomo.cliente.modelos.Funcion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Jassiel
 */
public class ControlTVController implements Initializable {
  //Variables
  private final Fachada fachada = Fachada.getInstancia();
  private Funcion funcion;
  @FXML
  private VBox root;
  @FXML
  private Button power;
  @FXML
  private Button k1,k2,k3,k4,k5,k6,k7,k8,k9,k0;
  @FXML
  private Button kr,kl,kt,kb,kok,kenter;
  @FXML
  private Button kcu,kcd,kvu,kvd;

  public void setFuncion(Funcion f){
    funcion = f;
  }
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    Manejador h = new Manejador();
    power.setOnAction(h);
    k1.setOnAction(h);
    k2.setOnAction(h);
    k3.setOnAction(h);
    k4.setOnAction(h);
    k5.setOnAction(h);
    k6.setOnAction(h);
    k7.setOnAction(h);
    k8.setOnAction(h);
    k9.setOnAction(h);
    k0.setOnAction(h);
    kr.setOnAction(h);
    kl.setOnAction(h);
    kt.setOnAction(h);
    kb.setOnAction(h);
    kok.setOnAction(h);
    kenter.setOnAction(h);
    kcd.setOnAction(h);
    kcu.setOnAction(h);
    kvd.setOnAction(h);
    kvu.setOnAction(h);
  }
  class Manejador implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent event) {
      String key = knowKey(event.getSource());
      funcion.setValorParaArgumento("tecla", key);
      fachada.pedirEjecucionFunciones(funcion);
    }
    private String knowKey(Object obj){
      if(obj.equals(power)) return "KEY_POWER";
      if(obj.equals(k1)) return "KEY_1";
      if(obj.equals(k2)) return "KEY_2";
      if(obj.equals(k3)) return "KEY_3";
      if(obj.equals(k4)) return "KEY_4";
      if(obj.equals(k5)) return "KEY_5";
      if(obj.equals(k6)) return "KEY_6";
      if(obj.equals(k7)) return "KEY_7";
      if(obj.equals(k8)) return "KEY_8";
      if(obj.equals(k9)) return "KEY_9";
      if(obj.equals(k0)) return "KEY_0";
      if(obj.equals(kr)) return "KEY_RIGHT";
      if(obj.equals(kl)) return "KEY_LEFT";
      if(obj.equals(kt)) return "KEY_UP";
      if(obj.equals(kb)) return "KEY_DOWN";
      if(obj.equals(kok)) return "KEY_OK";
      if(obj.equals(kenter)) return "KEY_ENTER";
      if(obj.equals(kcu)) return "KEY_CHANNELUP";
      if(obj.equals(kcd)) return "KEY_CHANNELDOWN";
      if(obj.equals(kvu)) return "KEY_VOLUMEUP";
      if(obj.equals(kvd)) return "KEY_VOLUMEDOWN";
      return null;
    }
  }
}