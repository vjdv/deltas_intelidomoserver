package intelidomo.idcp;

import javax.xml.bind.annotation.XmlAttribute;

public class ServicioX {
	public ServicioX(String n,String d){
		nombre = n;
		direccion = d;
	}
	@XmlAttribute(name="name")
	public String nombre;
	@XmlAttribute(name="url")
	public String direccion;
}