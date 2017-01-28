package intelidomo.cliente;

import intelidomo.cliente.modelos.Area;
import intelidomo.cliente.modelos.Dispositivo;
import intelidomo.cliente.modelos.Funcion;
import intelidomo.cliente.modelos.Traductor;
import intelidomo.idcp.FuncionX;
import intelidomo.idcp.Mensaje;
import intelidomo.idcp.UsuarioX;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 *
 * @author Jassiel
 */
public class Fachada {
  private String server="localhost", user="default", password="default", token="";
  private boolean secure = false, autorized = false;
  private static Fachada self;
  private UsuarioX usuario;
  private final Map<Integer,Area> areas_map = new HashMap<>();
  private final Map<String,Dispositivo> dispositivos_map = new HashMap<>();
  private Fachada(){
    
  }
  public boolean login() throws IOException{
    PostMethod post = new PostMethod("http://"+server+":"+(secure ? 4481:4480)+"/login.do");
    post.setRequestHeader("Content-type", "text/xml; charset=UTF-8");
    post.addParameter("user", user);
    post.addParameter("password", password);
    HttpClient client = new HttpClient();
    try{
      int result = client.executeMethod(post);
      if(result!=200) return false;
      Mensaje m = string2mensaje(post.getResponseBodyAsString());
      if(m.tipo!=2) return false;
      usuario = m.usuario;
      token = m.usuario.token;
      return true;
    }finally{
      post.releaseConnection();
    }
  }
  public boolean pedirDispositivos() throws IOException{
    GetMethod get = new GetMethod("http://"+server+":"+(secure ? 4481:4480)+"/info.do?token="+token);
    get.setRequestHeader("Content-type", "text/xml; charset=UTF-8");
    HttpClient client = new HttpClient();
    try{
      int result = client.executeMethod(get);
      if(result!=200) return false;
      System.out.println(get.getResponseBodyAsString());
      Mensaje m = string2mensaje(get.getResponseBodyAsString());
      if(m==null) return false;
      m.lista_areas.stream().forEach((a)->{
        Area area = Traductor.xToArea(a);
        areas_map.put(area.getId(), area);
        sumarArea();
      });
      m.lista_dispositivos.stream().forEach((d)->{
        Dispositivo disp = Traductor.xToDispositivo(d);
        dispositivos_map.put(disp.getTipo()+disp.getId(), disp);
        sumarDispositivo();
      });
      return true;
    }finally{
      get.releaseConnection();
    }
  }
  private Mensaje string2mensaje(String str){
    try {
      JAXBContext contexto = JAXBContext.newInstance(Mensaje.class);
      Unmarshaller u = contexto.createUnmarshaller();
      Mensaje m = (Mensaje) u.unmarshal(new StreamSource(new StringReader(str)));
      return m;
    } catch (Exception ex) {
      Logger.getLogger("DomoLog").log(Level.WARNING, "No fue posible interpretar mensaje >>>\n" + str.trim() + "\n<<<", ex);
      return null;
    }
  }
  public void pedirActualizacionEstado(Dispositivo d, String valor){
    Funcion func = d.getFuncion("setEstado");
    func.setValorParaArgumento("estado", valor);
    FuncionX f = Traductor.funcionToX(func);
    Mensaje m = new Mensaje();
    m.tipo = Mensaje.COMANDO;
    m.accion = "EXECUTE";
    m.ejecutar = new Mensaje.Execute();
    m.ejecutar.lista_funciones.add(f);
    enviarMensaje(m);
  }
  public void pedirEjecucionFunciones(Funcion... funcs){
    Mensaje m = new Mensaje();
    m.tipo = Mensaje.COMANDO;
    m.accion = Mensaje.EJECUTAR_FUNCIONES;
    m.ejecutar = new Mensaje.Execute();
    for(Funcion f : funcs) m.ejecutar.lista_funciones.add(Traductor.funcionToX(f));
    enviarMensaje(m);
  }
  public Area[] getAreaArray(){
    return areas_map.values().toArray(new Area[areas_map.size()]);
  }
  public Area getAreaById(int id){
    return areas_map.get(id);
  }
  public Dispositivo[] getDispositivoArray(){
    return dispositivos_map.values().toArray(new Dispositivo[dispositivos_map.size()]);
  }
  public Dispositivo getDispositivo(String tipo, int id){
    return dispositivos_map.get(tipo+id);
  }
  public String getUsername(){
    return usuario.nombre;
  }
  //GETTERS Y SETTERS
  public static Fachada getInstancia(){
    if(self==null) self = new Fachada();
    return self;
  }
  public String getServer() {
    return server;
  }
  public void setServer(String server) {
    this.server = server;
  }
  public String getUser() {
    return user;
  }
  public void setUser(String user) {
    this.user = user;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getToken() {
    return token;
  }
  public boolean isSecure() {
    return secure;
  }
  public void setSecure(boolean secure) {
    this.secure = secure;
  }
  public boolean isAutorized() {
    return autorized;
  }
  public void setAutorized(boolean autorized) {
    this.autorized = autorized;
  }
  //Propiedad numero de áreas
  public final IntegerProperty areasCount = new SimpleIntegerProperty();
  public final Integer getAreasCount(){
    return areasCount.get();
  }
  public void sumarArea(){
    areasCount.set(areasCount.get()+1);
  }
  public IntegerProperty areasCountProperty(){
    return areasCount;
  }
  //Propiedad numero dispositivos
  public final IntegerProperty dispositivosCount = new SimpleIntegerProperty();
  public final Integer getDispositivosCount(){
    return dispositivosCount.get();
  }
  public void sumarDispositivo(){
    dispositivosCount.set(dispositivosCount.get()+1);
  }
  public IntegerProperty dispositivosCountProperty(){
    return dispositivosCount;
  }
  //Variables de comunicacion socket alive
  private Socket socket;
  private PrintWriter out;
  private ComunicacionEntrante in;
  private int intentos_conexion = 0;
  //Propiedad de conectado
  private final IntegerProperty conectado = new SimpleIntegerProperty();
  public final Integer getConectado() {
    return conectado.get();
  }
  public final void setConectado(Integer i) {
    intentos_conexion = i.equals(CONECTADO) ? 0 : intentos_conexion+1;
    this.conectado.set(i);
  }
  public IntegerProperty ConectadoProperty(){
    return conectado;
  }
  public static final int SIN_CONEXION = 0, CONECTANDO = 1, CONECTADO = 2;
  //Conectar a servicio alive
  public void conectar(){
    if(socket==null){
      try {
        setConectado(CONECTANDO);
        socket = new Socket(server,(secure ? 4491:4490));
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new ComunicacionEntrante(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        Thread t = new Thread(in);
        t.setDaemon(true);
        t.start();
      } catch (IOException ex) {
        setConectado(SIN_CONEXION);
        Logger.getLogger("DomoLog").log(Level.SEVERE, "Fracaso al conectar con servidor. Causa: {0}", ex.getMessage());
        socket = null;
        try{
          Thread.sleep(Math.min(intentos_conexion*5000,60000));
          conectar();
        }catch(InterruptedException ex2){
          System.out.println("No fue posible esperar");
        }
      }
    }
  }
  //Envía un mensaje al servidor
  private void enviarMensaje(Mensaje m){
    try {
      JAXBContext contexto = JAXBContext.newInstance(Mensaje.class);
      Marshaller marshal = contexto.createMarshaller();
      marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      marshal.marshal(m, out);
      out.println();
    } catch (JAXBException ex) {
      Logger.getLogger("DomoLog").log(Level.WARNING,"Cliente: error al enviar solicitud");
    } catch (IllegalArgumentException ex) {
      Logger.getLogger("DomoLog").log(Level.FINE, "Cliente: m=null ");
    }
  }
  //Despachar un mensaje alive
  private void despacharMensaje(Mensaje m){
    if(m.tipo==Mensaje.EVENTO && m.accion.equals(Mensaje.ACTUALIZAR_ESTADOS)){
      m.lista_dispositivos.stream().forEach((d) -> {
        Dispositivo disp = getDispositivo(d.categoria, d.id);
        disp.setEstado(d.estado.valor);
      });
    }
  }
  //CLASE QUE LEE LA COMUNICACION ENTRANTE
  class ComunicacionEntrante implements Runnable{
    public final BufferedReader stream;
    public ComunicacionEntrante(BufferedReader br){
      stream = br;
    }
    @Override
    public void run() {
      try {
        setConectado(CONECTADO);
        StringBuilder mensaje = new StringBuilder();
        String fromServer;
        while((fromServer = stream.readLine()) != null) {
          if(fromServer.equals("")){
            System.out.println(mensaje.toString());
            Mensaje msg = string2mensaje(mensaje.toString());
            if(msg!=null) despacharMensaje(msg);
            mensaje = new StringBuilder();
          }else{
            mensaje.append(fromServer);
            mensaje.append("\n");
          }
        }
      } catch (Exception ex) {
        Logger.getLogger("DomoLog").log(Level.WARNING, "Conexión perdida debido a {0}", ex.getMessage());
      }
      try{
        stream.close();
        out.close();
        socket.close();
        socket = null;
        setConectado(SIN_CONEXION);
        Thread.sleep(Math.min(intentos_conexion*1500,60000));
        conectar();
      }catch(IOException | InterruptedException ex){
        Logger.getLogger("DomoLog").log(Level.WARNING,"Error de cierre por {0}", ex.getMessage());
      }
    }
  }
}