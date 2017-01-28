package intelidomo.idcp;

import javax.xml.bind.annotation.XmlAttribute;

public class AreaX {
	@XmlAttribute
	public int id;
	@XmlAttribute(name="type")
	public String tipo;
	@XmlAttribute(name="description")
	public String descripcion;
	@XmlAttribute(name="group")
	public String grupo;
}