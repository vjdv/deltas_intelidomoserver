package intelidomo.idcp;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class FuncionX {
	@XmlAttribute(name="instanceId")
	public Integer id;
	@XmlAttribute(name="name")
	public String nombre;
	@XmlAttribute(name="description")
	public String descripcion;
	@XmlElement(name="Arg")
	public List<EstadoX> lista_argumentos = new ArrayList<>();
}