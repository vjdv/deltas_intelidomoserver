package intelidomo.idcp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Message")
public class Mensaje {
	//Constantes
	public final static int COMANDO = 1;
	public final static int EXITO = 2;
	public final static int FALLO = 3;
	public final static int EVENTO = 4;
	public final static int UNKNOWN = 5;
	public final static int ILLEGAL = 6;
	public final static String ACCESO = "LOGIN";
	public static final String INFORMACION_DOMOTICA = "DOMOINFO";
	public static final String ACTUALIZAR_ESTADOS = "ACTUALIZAR";
	public static final String EJECUTAR_FUNCIONES = "EXECUTE";
	public static final String COMANDO_VOZ = "VOICE_CMD";
	//Datos
	@XmlAttribute
	public Integer id; 
	@XmlAttribute(name="type")
	public int tipo = 0;
	@XmlAttribute(name="action")
	public String accion = "";
	@XmlAttribute(name="details")
	public String detalle;
	@XmlElement(name="User")
	public UsuarioX usuario;
	@XmlElement(name="Area")
	public List<AreaX> lista_areas= new ArrayList<>();
	@XmlElement(name="Device")
	public List<DispositivoX> lista_dispositivos = new ArrayList<>();
	@XmlElement(name="Execute")
	public Execute ejecutar;
	@XmlElement(name="TextCommand")
	public ComandoLN cmdln;
	@XmlElement(name="Service")
	public List<ServicioX> lista_servicios = new ArrayList<>();
	public static class Execute{
		@XmlElement(name="Function")
		public List<FuncionX> lista_funciones = new ArrayList<>();
	}
}