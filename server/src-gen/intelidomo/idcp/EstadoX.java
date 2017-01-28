package intelidomo.idcp;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class EstadoX {
	@XmlAttribute(name="name")
	public String nombre;
	@XmlAttribute(name="value")
	public String valor;
	@XmlAttribute(name="minValue")
	public String valor_minimo;
	@XmlAttribute(name="maxValue")
	public String valor_maximo;
	@XmlAttribute(name="type")
	public String tipo;
	@XmlAttribute(name="prefix")
	public String prefijo;
	@XmlAttribute(name="suffix")
	public String sufijo;
	@XmlAttribute(name="readOnly")
	public Boolean solo_lectura;
	@XmlElement(name="AllowedValue")
	public List<EstadoX> lista_valores = new ArrayList<>();
}