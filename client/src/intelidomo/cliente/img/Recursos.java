package intelidomo.cliente.img;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

/**
 *
 * @author Jassiel
 */
public class Recursos {
  private static Recursos singleton;
  private Image casa_img,area_img,habitacion_img,luz_img,termometros_img,ac_img,pre_img,salir_img;
  private Image alarma_img,alarma2_img, tv_img;
  private AudioClip alarma_sound;
  private Recursos(){}
  public static Recursos getInstancia(){
    if(singleton==null) singleton = new Recursos();
    return singleton;
  }
  public Image getCasaImage(){
    if(casa_img==null) casa_img = new Image(getClass().getResourceAsStream("logo_icon.png"));
    return casa_img;
  }
  public ImageView getCasaIcon(){
    return new ImageView(casa_img);
  }
  public Image getAreaImage(){
    if(area_img==null) area_img = new Image(getClass().getResourceAsStream("area.png"));
    return area_img;
  }
  public ImageView getAreaIcon(){
    return new ImageView(getAreaImage());
  }
  public Image getHabitacionImage(){
    if(habitacion_img==null) habitacion_img = new Image(getClass().getResourceAsStream("puerta.png"));
    return habitacion_img;
  }
  public ImageView getHabitacionIcon(){
    return new ImageView(getHabitacionImage());
  }
  public Image getLuzImage(){
    if(luz_img==null) luz_img = new Image(getClass().getResourceAsStream("luz.png"));
    return luz_img;
  }
  public ImageView getLuzIcon(){
    return new ImageView(getLuzImage());
  }
  public Image getTermometroImage(){
    if(termometros_img==null) termometros_img = new Image(getClass().getResourceAsStream("termometros.png"));
    return termometros_img;
  }
  public ImageView getTermometroIcon(){
    return new ImageView(getTermometroImage());
  }
  public Image getAireAcondImage(){
    if(ac_img==null) ac_img = new Image(getClass().getResourceAsStream("aire.png"));
    return ac_img;
  }
  public ImageView getAireAcondIcon(){
    return new ImageView(getAireAcondImage());
  }
  public Image getPresenciaImage(){
    if(pre_img==null) pre_img = new Image(getClass().getResourceAsStream("presencia.png"));
    return pre_img;
  }
  public ImageView getPresenciaIcon(){
    return new ImageView(getPresenciaImage());
  }
  public ImageView getSalirIcon(){
    if(salir_img==null) salir_img = new Image(getClass().getResourceAsStream("salir.png"));
    return new ImageView(salir_img);
  }
  public Image getAlarmaImage(){
    if(alarma_img==null) alarma_img = new Image(getClass().getResourceAsStream("alarma.png"));
    return alarma_img;
  }
  public ImageView getAlarmaIcon(){
    return new ImageView(getAlarmaImage());
  }
  public Image getAlarmaAnimadaImage(){
    if(alarma2_img==null) alarma2_img = new Image(getClass().getResourceAsStream("alarma.gif"));
    return alarma2_img;
  }
  public ImageView getAlarmaAnimadaIcon(){
    return new ImageView(getAlarmaAnimadaImage());
  }
  public AudioClip getAlarmaSound(){
    if(alarma_sound==null){
      alarma_sound = new AudioClip(getClass().getResource("alarma.aiff").toString());
      alarma_sound.setCycleCount(AudioClip.INDEFINITE);
    }
    return alarma_sound;
  }
  public Image getTvImage(){
    if(tv_img==null) tv_img = new Image(getClass().getResourceAsStream("tv.png"));
    return tv_img;
  }
  public ImageView getTvIcon(){
    return new ImageView(getTvImage());
  }
  private final Map<String,Image> images_map = new HashMap<>();
  public Image getImage(String name){
    Image img = images_map.get(name);
    if(img==null){
      img = new Image(getClass().getResourceAsStream(name));
      images_map.put(name, img);
    }
    return img;
  }
  public ImageView getIcon(String name){
    return new ImageView(getImage(name));
  }
}