package org.haw.praktikum2.shared.exception;

@SuppressWarnings("serial")
public class ChatException extends Exception {
	public ChatException(String message) {
		super(message);
	}
	
	public ChatException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ChatException(Throwable cause) {
		super(cause);
	}
}
