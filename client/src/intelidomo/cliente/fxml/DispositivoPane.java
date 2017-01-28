package intelidomo.cliente.fxml;

import intelidomo.cliente.img.Recursos;
import intelidomo.cliente.modelos.Dispositivo;
import intelidomo.estatico.Constantes;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Jassiel
 */
public final class DispositivoPane extends HBox{
  private final StringProperty estado_requerido = new SimpleStringProperty();
  private final Recursos recursos = Recursos.getInstancia();
  private final ImageView icon = new ImageView(recursos.getAreaImage());
  private final Label texto = new Label("Dispositivo");
  private final VBox vbox = new VBox();
  private InicioController inicio;
  private Dispositivo dispositivo;
  private ToggleButton toggle_btn;
  private Slider slider_btn;
  private Button control_btn;
  private Label valor_lb;
  //Constructores
  public DispositivoPane(Dispositivo d){
    setEstadoRequerido(Constantes.INDISPONIBLE);
    texto.getStyleClass().add("titulo");
    icon.setFitHeight(50);
    icon.setFitWidth(50);
    setPadding(new Insets(5));
    setSpacing(5);
    getStyleClass().add("disp_pane");
    getChildren().addAll(icon,vbox);
    vbox.getChildren().addAll(texto);
    setDispositivo(d);
  }
  private void setDispositivo(Dispositivo d){
    dispositivo = d;
    switch(d.getTipo()){
      case Constantes.LUZ:
        texto.setText("Luz "+d.getId());
        icon.setImage(recursos.getLuzImage());
        break;
      case Constantes.AIRE_ACONDICIONADO:
        texto.setText("Aire Acondicionado");
        icon.setImage(recursos.getAireAcondImage());
        break;
      case Constantes.SENSOR_TEMPERATURA:
        texto.setText("Temperatura "+d.getId());
        icon.setImage(recursos.getTermometroImage());
        break;
      case Constantes.SENSOR_HUMEDAD:
        texto.setText("Humedad "+d.getId());
        icon.setImage(recursos.getImage("humedad.png"));
        break;
      case Constantes.SENSOR_PRESENCIA:
        texto.setText("Presencia");
        icon.setImage(recursos.getPresenciaImage());
        break;
      case Constantes.ALARMA:
        String nombre = "Alarma "+dispositivo.getId();
        if(dispositivo.getSubtipo()==null) nombre += " SC";
        else if(dispositivo.getSubtipo().equals(Constantes.GAS)) nombre += " de gas";
        else if(dispositivo.getSubtipo().equals(Constantes.FUEGO)) nombre += " de fuego";
        else nombre += " de "+dispositivo.getSubtipo();
        texto.setText(nombre);
        icon.setImage(recursos.getAlarmaImage());
        break;
      case Constantes.TV:
        texto.setText("Televisión "+d.getId());
        icon.setImage(recursos.getImage("tv.png"));
        break;
      case Constantes.CORTINA:
        texto.setText("Cortina "+d.getId());
        icon.setImage(recursos.getImage("cortina.png"));
        slider_btn = new Slider(0, 100, 0);
        slider_btn.setShowTickMarks(false);
        slider_btn.setBlockIncrement(10f);
        slider_btn.setPrefWidth(90);
        slider_btn.setOnMouseReleased((MouseEvent event) -> {
          String val = ((Integer)((Double)slider_btn.getValue()).intValue()).toString();
          setEstadoRequerido(val);
        });
        vbox.getChildren().add(slider_btn);
        break;
    }
    if(d.getEstados()==null) d.setEstados("");
    switch(d.getEstados()){
      case Constantes.ON_OFF:
        toggle_btn = new ToggleButton("ON/OFF");
        toggle_btn.setOnAction((ActionEvent event) -> {
          boolean edo = toggle_btn.isSelected();
          if(edo){
            toggle_btn.setText("Encendiendo");
            setEstadoRequerido(Constantes.ON);
          }else{
            toggle_btn.setText("Apagando");
            setEstadoRequerido(Constantes.OFF);
          }
        });
        vbox.getChildren().add(toggle_btn);
        break;
      default:
        valor_lb = new Label();
        vbox.getChildren().add(valor_lb);
        break;
    }
    //LUZ REGULABLE
    if(d.getTipo().equals(Constantes.LUZ) && d.getSubtipo()!=null && d.getSubtipo().equals(Constantes.REGULABLE)){
      slider_btn = new Slider(0, 100, 0);
      slider_btn.setShowTickMarks(false);
      slider_btn.setBlockIncrement(10f);
      slider_btn.setPrefWidth(90);
      slider_btn.setOnMouseReleased((MouseEvent event) -> {
        String val = ((Integer) ((Double) slider_btn.getValue()).intValue()).toString();
        setEstadoRequerido(val);
      });
      vbox.getChildren().add(slider_btn);
    }
    //LUZ RGB
    if(d.getTipo().equals(Constantes.LUZ) && d.getSubtipo()!=null && d.getSubtipo().equals(Constantes.RGB)){
      icon.setImage(recursos.getImage("luz_rgb.png"));
      ColorPicker picker = new ColorPicker();
      picker.setMaxWidth(100);
      picker.setOnAction((ActionEvent event) -> {
        Color c = picker.getValue();
        long r = Math.round(c.getRed()*255);
        long g = Math.round(c.getGreen()*255);
        long b = Math.round(c.getBlue()*255);
        setEstadoRequerido(r+","+g+","+b);
      });
      vbox.getChildren().clear();
      vbox.getChildren().addAll(texto,picker);
    }
    tratarDispositivosEspeciales(d);
    //Actualizamos el estado mostrado cuando cambie en el dispositivo original
    dispositivo.estadoProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
      Platform.runLater(() -> actualizarEstado());
    });
    setEstadoRequerido(dispositivo.getEstado());
    actualizarEstado();
  }
  private void actualizarEstado(){
    //Si el estado es indisponible la etiqueta muestra indisponible
    if(dispositivo.getEstado().equals(Constantes.INDISPONIBLE)){
      if(valor_lb!=null) valor_lb.setText("No disponible");
      if(toggle_btn!=null) {
        toggle_btn.setDisable(true);
        toggle_btn.setText("No disponible");
      }
      if(slider_btn!=null) slider_btn.setDisable(true);
      return;
    }
    //Se muestra el estado en base al tipo de dispositivo
    switch(dispositivo.getTipo()){
      case Constantes.SENSOR_PRESENCIA:
        boolean x = dispositivo.getEstado().equals(Constantes.YES);
        valor_lb.setText(x ? "Hay presencia" : "Sin presencia");
        return;
      case Constantes.SENSOR_TEMPERATURA:
        valor_lb.setText(dispositivo.getEstado() + "°C");
        return;
      case Constantes.SENSOR_HUMEDAD:
        valor_lb.setText(dispositivo.getEstado() + "%");
        return;
      case Constantes.CORTINA:
        slider_btn.setDisable(false);
        int edo = Integer.parseInt(dispositivo.getEstado());
        String txt = edo==0 ? "Abierta" : (edo==100 ? "Cerrada" : "Al "+edo+"%");
        valor_lb.setText(txt);
        return;
      case Constantes.ALARMA:
        if(dispositivo.getEstado().equals(Constantes.YES)){
          valor_lb.setText("¡Detectado!");
          icon.setImage(recursos.getAlarmaAnimadaImage());
          recursos.getAlarmaSound().play();
        }else{
          valor_lb.setText("Sin incidencia");
          icon.setImage(recursos.getAlarmaImage());
          recursos.getAlarmaSound().stop();
        }
        return;
    }
    //Según el tipo de estados
    switch(dispositivo.getEstados()){
      case Constantes.ON_OFF:
        if(dispositivo.getEstado().equals(Constantes.ON)){
          toggle_btn.setDisable(false);
          toggle_btn.setSelected(true);
          toggle_btn.setText("Encendido");
        }
        if(dispositivo.getEstado().equals(Constantes.OFF)){
          toggle_btn.setDisable(false);
          toggle_btn.setSelected(false);
          toggle_btn.setText("Apagado");
        }
        break;
      case Constantes.DECIMAL:
        valor_lb.setText(dispositivo.getEstado());
        break;
      default:
        valor_lb.setText(dispositivo.getEstado());
        break;
    }
    //LUZ REGULABLE
    if(dispositivo.getTipo().equals(Constantes.LUZ) && dispositivo.getSubtipo()!=null && dispositivo.getSubtipo().equals(Constantes.REGULABLE)){
      if(dispositivo.getEstado().equals("0")) valor_lb.setText("Apagada");
      else valor_lb.setText("Encendida al "+dispositivo.getEstado()+"%");
    }
  }
  public void tratarDispositivosEspeciales(Dispositivo d){
    switch(d.getTipo()){
      case Constantes.TV:
        control_btn = new Button("Control");
        vbox.getChildren().clear();
        vbox.getChildren().addAll(texto,control_btn);
        try{
          FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlTV.fxml"));
          VBox pane = (VBox) loader.load();
          final ControlTVController tvc = loader.<ControlTVController>getController();
          tvc.setFuncion(d.getFuncion("presionarTecla"));
          control_btn.setOnAction((ActionEvent event) -> {
            inicio.mostrarControlesDispositivo(d, this, pane);
          });
        }catch(IOException ex){
          Logger.getLogger("DomoLog").log(Level.SEVERE,"ERROR FXML",ex);
          control_btn.setText("ERROR");
        }
        break;
    }
  }
  public final String getEstadoRequerido(){
    return estado_requerido.get();
  }
  public final void setEstadoRequerido(String value){
    estado_requerido.set(value);
  }
  public StringProperty estadoRequeridoProperty(){
    return estado_requerido;
  }
  public void setInicio(InicioController inicio) {
    this.inicio = inicio;
  }
  public Image getImage(){
    return icon.getImage();
  }
  public String getNombre(){
    return texto.getText();
  }
}