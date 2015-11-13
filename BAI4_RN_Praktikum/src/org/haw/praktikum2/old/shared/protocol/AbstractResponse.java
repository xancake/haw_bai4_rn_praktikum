package org.haw.praktikum2.old.shared.protocol;

import org.haw.praktikum2.old.shared.ChatClient;
import org.haw.praktikum2.old.shared.connection.ChatConnection;
import org.haw.praktikum2.old.shared.exception.ChatException;
import org.haw.praktikum2.old.shared.exception.RequestException;

public abstract class AbstractResponse implements Response {
	private Request _request;
	private ChatException _exception;
	
	/**
	 * Erzeugt einen erfolgreichen Response ohne Nachricht.
	 * @param request Das {@link Request}, zu dem dieses Response die Antwort ist
	 */
	public AbstractResponse(Request request) {
		_request = request;
	}
	
	/**
	 * Erzeugt einen fehlgeschlagenen Response mit einer {@link ChatException}.
	 * @param request Das {@link Request}, zu dem dieses Response die Antwort ist
	 * @param exception Die Exception
	 */
	public AbstractResponse(Request request, ChatException exception) {
		_request = request;
		_exception = exception;
	}
	
	@Override
	public Request getRequest() {
		return _request;
	}
	
	@Override
	public ChatException getFailureException() {
		return _exception;
	}
	
	@Override
	public final void execute(ChatClient client, ChatConnection connection) throws ChatException {
		if(getFailureException() != null) {
			throw new RequestException("Bei der Verarbeitung des Requests auf dem Server ist ein Fehler aufgetreten", getFailureException());
		}
		execute0(client, connection);
	}
	
	/**
	 * Wird von {@link #execute(ChatClient, ChatConnection)} aufgerufen, wenn {@link #getFailureException()} {@code == null} ist.
	 * Hier geschieht tatsächliche Ausführung des Responses.
	 * @param client Der Client der das Response ausführt
	 * @param connection Die Connection, die das Response empfangen hat
	 * @throws ChatException Wenn ein Fehler beim ausführen des Responses auftritt
	 */
	protected abstract void execute0(ChatClient client, ChatConnection connection) throws ChatException;
}
