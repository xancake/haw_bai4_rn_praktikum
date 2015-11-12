package org.haw.praktikum2.shared.exception;

@SuppressWarnings("serial")
public class RequestException extends ChatException {
	public RequestException(String message) {
		super(message);
	}
	
	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RequestException(Throwable cause) {
		super(cause);
	}
}
