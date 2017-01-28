package intelidomo.cliente.fxml;

import intelidomo.cliente.Fachada;
import intelidomo.cliente.Principal;
import intelidomo.cliente.img.Recursos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Jassiel
 */
public class LoginController implements Initializable {
  //Campos
  private final Fachada fachada = Fachada.getInstancia();
  private final Label acceso_edo = new Label();
  private final ProgressIndicator circulo = new ProgressIndicator();
  @FXML
  private TextField servidor_txt, usuario_txt, password_txt;
  @FXML
  private CheckBox seguro_check;
  @FXML
  private HBox caja;
  @FXML
  private Button acceder_btn, salir_btn;
  private final Button ok_btn = new Button("OK");
  
  //Disable fields
  private void desactivarControles(){
    servidor_txt.setDisable(true);
    usuario_txt.setDisable(true);
    password_txt.setDisable(true);
    seguro_check.setDisable(true);
    caja.getChildren().clear();
  }
  @FXML
  private void acceder(ActionEvent event) {
    if(servidor_txt.getText().isEmpty()){
      servidor_txt.requestFocus();
      return;
    }
    if(usuario_txt.getText().isEmpty()){
      usuario_txt.requestFocus();
      return;
    }
    if(password_txt.getText().isEmpty()){
      password_txt.requestFocus();
      return;
    }
    fachada.setServer(servidor_txt.getText());
    fachada.setUser(usuario_txt.getText());
    fachada.setPassword(password_txt.getText());
    fachada.setSecure(seguro_check.isSelected());
    desactivarControles();
    acceso_edo.setText("Accediendo al servidor");
    caja.getChildren().addAll(circulo, acceso_edo);
    Task task = new Task<Void>() {
      @Override
      public Void call() throws Exception{
        try{
          boolean exito = fachada.login();
          if(exito){
            Platform.runLater(()->acceso_edo.setText("Obteniendo dispositivos"));
            exito = fachada.pedirDispositivos();
            if(exito){
              Platform.runLater(()->acceso_edo.setText("Iniciando interfaz"));
              iniciar();
            }else notificacion("Error de carga");
          }else notificacion("Usuario/Contraseña inválidos");
        }catch(IOException ex){
          notificacion("Error de conexión");
        }
        return Void.TYPE.newInstance();
      }
    };
    Thread th = new Thread(task);
    th.setDaemon(true);
    th.start();
  }
  private void notificacion(String txt){
    Platform.runLater (()->{
      caja.getChildren().remove(circulo);
      caja.getChildren().add(ok_btn);
      acceso_edo.setText(txt);
      ok_btn.requestFocus();
    });
  }
  private void iniciar() {
    Platform.runLater(() -> {
      
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        Parent root = loader.<Parent>load();
        InicioController controller = loader.<InicioController>getController();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        Principal.inicioStage = stage;
        stage.getIcons().add(Recursos.getInstancia().getCasaImage());
        stage.setTitle("Sistema Intelidomo");
        stage.initStyle(StageStyle.UNIFIED);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent event) -> {
          controller.terminar();
        });
        Principal.loginStage.close();
      } catch (IOException ex) {
        Logger.getLogger("DomoLog").log(Level.SEVERE, null, ex);
      }
    });
  }
  @FXML
  private void salir(ActionEvent event) {
    Principal.loginStage.close();
  }
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    circulo.setProgress(-1);
    circulo.setPrefSize(35, 35);
    ok_btn.getStyleClass().add("ok");
    ok_btn.setOnAction((ActionEvent event) -> {
      servidor_txt.setDisable(false);
      usuario_txt.setDisable(false);
      password_txt.setDisable(false);
      seguro_check.setDisable(false);
      caja.getChildren().clear();
      caja.getChildren().addAll(acceder_btn, salir_btn);
    });
    Platform.runLater(()->usuario_txt.requestFocus());
  }
}