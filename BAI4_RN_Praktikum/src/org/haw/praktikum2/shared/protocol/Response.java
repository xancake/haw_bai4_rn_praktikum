package org.haw.praktikum2.shared.protocol;

import java.io.Serializable;

import org.haw.praktikum2.shared.ChatClient;
import org.haw.praktikum2.shared.connection.ChatConnection;
import org.haw.praktikum2.shared.exception.ChatException;

public interface Response extends Serializable {
	/**
	 * Gibt das {@link Request} zu dem dieses Response die Antwort ist zurück.
	 * @return Das {@link Request}, das durch dieses Response beantwortet wird
	 */
	Request getRequest();
	
	/**
	 * Gibt die Exception zurück, die den Fehlschlag des Responses ausgelöst hat.
	 * @return Die {@link ChatException}
	 */
	ChatException getFailureException();
	
	/**
	 * Führt das Response aus.
	 * @param client Der Client der das Response ausführt
	 * @param connection Die Connection, die das Response empfangen hat
	 * @throws ChatException Wenn ein Fehler beim ausführen des Responses auftritt
	 */
	void execute(ChatClient client, ChatConnection connection) throws ChatException;
}
