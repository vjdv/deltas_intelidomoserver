package intelidomo.cliente;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Jassiel
 */
public class Principal extends Application {
  public static Stage loginStage, inicioStage;
  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
    Scene scene = new Scene(root);
    scene.setFill(Color.TRANSPARENT);
    loginStage = stage;
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.setScene(scene);
    stage.show();
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}