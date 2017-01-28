package intelidomo.idcp;

import javax.xml.bind.annotation.XmlAttribute;

public class UsuarioX {
	@XmlAttribute(name="username")
	public String clave = "";
	@XmlAttribute
	public String password;
	@XmlAttribute(name="type")
	public String tipo;
	@XmlAttribute(name="name")
	public String nombre;
	@XmlAttribute
	public String token;
	@XmlAttribute
	public long token_expire;
	@XmlAttribute
	public String ultimo_login;
}