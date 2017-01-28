package intelidomo.idcp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class DispositivoX {
	/*** ATRIBUTOS ***/
	@XmlAttribute
	public int id;
	@XmlAttribute(name="category")
	public String categoria;
	@XmlAttribute(name="subcategory")
	public String subcategoria;
	@XmlAttribute(name="description")
	public String descripcion;
	@XmlAttribute(name="area")
	public int area = 0;
	@XmlElement(name="State")
	public EstadoX estado = new EstadoX();
	@XmlElement(name="Function")
	public List<FuncionX> lista_funciones = new ArrayList<FuncionX>();
	@XmlElement(name="Service")
	public List<ServicioX> lista_servicios = new ArrayList<ServicioX>();
}