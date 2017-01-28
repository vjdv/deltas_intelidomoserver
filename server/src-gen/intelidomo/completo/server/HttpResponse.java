package intelidomo.completo.server;

/*** added by dServer
 */
public class HttpResponse {
	public static final int OK = 200, BAD_REQUEST = 400, FORBIDDEN = 403,
	NOT_FOUND = 404;
	private StringBuilder content = new StringBuilder();
	private String contentType = "text/html";
	private int code = OK;
	public HttpResponse() {
	}
	public HttpResponse(int code) {
		this.code = code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public void setContentType(String ct) {
		contentType = ct;
	}
	public void appendContent(String str) {
		content.append(str);
	}
	private String getCodeString(int code) {
		switch(code) {
			case 200 : return "200 OK";
			case 201 : return "201 Created";
			case 202 : return "202 Accepted";
			case 400 : return "400 Bad Request";
			case 401 : return "401 Unauthorized";
			case 402 : return "402 Payment Required";
			case 403 : return "403 Forbidden";
			case 404 : return "404 Not Found";
			case 411 : return "411 Length Required";
			case 500 : return "500 Internal Server Error";
			default : return "999 ???";
		}
	}
	public String getResponse() {
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + getCodeString(code) + "\n");
		sb.append("Content-Type: " + contentType + "; charset=utf-8" + "\n");
		sb.append("X-Powered-By: Intelidomo" + "\n");
		sb.append("Server: intelidomo" + "\n");
		sb.append("Content-Length: " + length(content) + "\n");
		sb.append("\n");
		sb.append(content);
		return sb.toString();
	}
	private int length(CharSequence sequence) {
		int count = 0;
		for(int i = 0, len = sequence.length();
			i < len;
			i ++) {
			char ch = sequence.charAt(i);
			if(ch <= 0x7F) {
				count ++;
			}
			else if(ch <= 0x7FF) {
				count += 2;
			}
			else if(Character.isHighSurrogate(ch)) {
				count += 4;
				++ i;
			}
			else {
				count += 3;
			}
		}
		return count;
	}
}