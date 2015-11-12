package org.haw.praktikum2.shared.exception;

import java.text.MessageFormat;

@SuppressWarnings("serial")
public class BadProtocolException extends ChatException {
	public BadProtocolException(String message) {
		super(message);
	}
	
	public BadProtocolException(String request, String response) {
		super(MessageFormat.format("Schlechtes Protokoll: gesendet '{0}', empfangen '{1}'", request, response));
	}
}
