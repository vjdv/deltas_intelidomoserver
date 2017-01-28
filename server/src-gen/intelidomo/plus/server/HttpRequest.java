package intelidomo.plus.server;

import java.util.HashMap;
import java.util.Map;
/*** added by dServer
 */
public class HttpRequest {
	public static int GET = 1, POST = 2;
	public String method, url, content = "";
	public int contentLength = 0;
	public Map<String, String> datos_get = new HashMap<String, String>();
	public Map<String, String> datos_post = new HashMap<String, String>();
	private boolean firstLineReceived = false;
	public void addHeader(String str) throws InvalidRequestException {
		if(firstLineReceived) {
			if(str.startsWith("Content-Length: ")) contentLength =
			Integer.parseInt(str.split(" ")[1]);
		}
		else if(str.startsWith("GET /") || str.startsWith("POST /")) {
			method = str.startsWith("GET") ? "GET" : "POST";
			String parts [] = str.split(" ")[1].split("\\?");
			url = parts[0];
			if(parts.length == 2) {
				String params [] = parts[1].split("&");
				for(String param : params) {
					String [] key_value = param.split("=");
					datos_get.put(key_value[0], key_value[1]);
				}
			}
			firstLineReceived = true;
		}
		else throw new InvalidRequestException();
	}
	public boolean requiresContent() throws InvalidRequestException {
		if(method == null) throw new InvalidRequestException();
		return method.equals("POST") && content.length() < contentLength;
	}
	public void addContent(String str) throws InvalidRequestException {
		content += str;
		if(content.length() >= contentLength) parseContent();
	}
	public void addContent(char c) throws InvalidRequestException {
		content += c;
		if(content.length() >= contentLength) parseContent();
	}
	public boolean uriEquals(String str) {
		return str.equals(method + " " + url);
	}
	public void parseContent() {
		String params [] = content.split("&");
		for(String param : params) {
			String key_value [] = param.split("=");
			datos_post.put(key_value[0], key_value[1]);
		}
	}
	public static class InvalidRequestException extends Exception {
		private static final long serialVersionUID = 1L;
		public InvalidRequestException() {
		}
		public InvalidRequestException(String msg) {
			super(msg);
		}
	}
}