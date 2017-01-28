package intelidomo.cliente.fxml;

import intelidomo.cliente.Fachada;
import intelidomo.cliente.img.Recursos;
import intelidomo.cliente.modelos.Area;
import intelidomo.cliente.modelos.Dispositivo;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Jassiel
 */
public class InicioController implements Initializable {
  //Variables
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private final Fachada fachada = Fachada.getInstancia();
  private final Recursos recursos = Recursos.getInstancia();
  private ScheduledFuture<?> clockHandle = null;
//  private final Calendar calendar = Calendar.getInstance();
  @FXML
  private ScrollPane areas_scroll, alldisp_scroll, extra_scroll;
  @FXML
  private FlowPane areas_pane, disp_pane, alldisp_pane;
  @FXML
  private AnchorPane root_pane, inicio_pane, extra_pane;
  @FXML
  private TabPane tab_pane;
  @FXML
  private ImageView home_icon, bg_icon, area_icon, dispositivo_icon;
  @FXML
  private Label areaNom_lb, areaId_lb, areaSize_lb, hora_lb;
  @FXML
  private Label dispNom_lb, dispInfo_lb;
  @FXML
  private Label user_lb, areacount_lb, dispositivocount_lb;
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    Runnable clock = () -> {
      Calendar calendar = Calendar.getInstance();
      int h = calendar.get(Calendar.HOUR_OF_DAY);
      int m = calendar.get(Calendar.MINUTE);
      int s = calendar.get(Calendar.SECOND);
      String mm = m<10 ? "0"+m : ""+m;
      String ss = s%2==1 ? "." : "";
      Platform.runLater(()->hora_lb.setText(h+":"+mm+ss));
    };
    clockHandle = scheduler.scheduleAtFixedRate(clock, 0, 100, TimeUnit.MILLISECONDS);
    user_lb.setText("¡Bienvenido "+fachada.getUsername()+"!");
    areacount_lb.textProperty().bind(fachada.areasCount.asString("%d areas(s)"));
    dispositivocount_lb.textProperty().bind(fachada.dispositivosCount.asString("%d dispositivo(s)"));
    //Imagen de fondo
    bg_icon.fitWidthProperty().bind(inicio_pane.widthProperty());
    bg_icon.fitHeightProperty().bind(inicio_pane.heightProperty());
    //Propiedades
    areas_pane.prefWidthProperty().bind(areas_scroll.widthProperty().subtract(15));
    alldisp_pane.prefWidthProperty().bind(alldisp_scroll.widthProperty().subtract(15));
    alldisp_pane.prefHeightProperty().bind(alldisp_scroll.heightProperty().subtract(10));
    Map<Integer,AreaPane> areas_map = new HashMap<>();
    for(Area area : fachada.getAreaArray()){
      AreaPane area_pane = new AreaPane(area);
      areas_map.put(area.getId(), area_pane);
      areas_pane.getChildren().add(area_pane);
      area_pane.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent evt) -> {
        areaNom_lb.setText(area.getNombre());
        areaId_lb.setText("Area #"+area.getId());
        areaSize_lb.setText(area_pane.getDispositivoPanes().size()+" dispositivo(s)");
        area_icon.setImage(area_pane.getImage());
        disp_pane.getChildren().clear();
        disp_pane.getChildren().addAll(area_pane.getDispositivoPanes());
      });
    }
    for(Dispositivo disp : fachada.getDispositivoArray()){
      DispositivoPane dp1 = new DispositivoPane(disp);
      DispositivoPane dp2 = new DispositivoPane(disp);
      CambiarEstadoListener dpl = new CambiarEstadoListener(disp);
      dp1.estadoRequeridoProperty().addListener(dpl);
      dp2.estadoRequeridoProperty().addListener(dpl);
      AreaPane area = areas_map.get(disp.getArea());
      if(area!=null) area.agregarDispositivo(dp1);
      alldisp_pane.getChildren().add(dp2);
      dp1.setInicio(this);
      dp2.setInicio(this);
    }
    fachada.ConectadoProperty().addListener((ObservableValue<? extends Number> obs, Number oldValue, Number newValue) -> {
      if(newValue.equals(Fachada.CONECTADO)){
        home_icon.setImage(recursos.getImage("logo_icon.png"));
      }
      else if(newValue.equals(Fachada.CONECTANDO)){
        home_icon.setImage(recursos.getImage("logo_amarillo.png"));
      }
      else if(newValue.equals(Fachada.SIN_CONEXION)){
        home_icon.setImage(recursos.getImage("logo_rojo.png"));
      }
    });
    fachada.conectar();
    ocultarExtra(null);
  }
  @FXML
  private void ocultarExtra(ActionEvent event) {
    extra_pane.setVisible(false);
    AnchorPane.setRightAnchor(alldisp_scroll,0d);
  }
  public void mostrarControlesDispositivo(Dispositivo d, DispositivoPane dpane, Pane pane){
    extra_pane.setVisible(true);
    dispositivo_icon.setImage(dpane.getImage());
    dispNom_lb.setText(dpane.getNombre());
    dispInfo_lb.setText("Disponible");
    AnchorPane.setRightAnchor(alldisp_scroll, pane.getPrefWidth()+15);
    extra_scroll.setContent(pane);
    extra_pane.setPrefWidth(pane.getPrefWidth()+17);
    tab_pane.getSelectionModel().select(2);
  }
  public void terminar(){
    clockHandle.cancel(true);
    scheduler.shutdown();
  }
  class CambiarEstadoListener implements ChangeListener<String>{
    private final Dispositivo d;
    public CambiarEstadoListener(Dispositivo d){
      this.d = d;
    }
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
      System.out.println("Listener");
      fachada.pedirActualizacionEstado(d, newValue);
    }
  }
}