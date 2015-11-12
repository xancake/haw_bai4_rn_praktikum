package org.haw.praktikum2.server.core;

import org.haw.praktikum2.server.core.connection.ChatServerConnection;

public interface ChatServerCore {
	/**
	 * Startet den Chatserver.
	 * Der Aufruf ist nicht blockierend.
	 */
	void startup();
	
	/**
	 * Stoppt den Chatserver.
	 * Das hat zur Folge, dass der Server keine weiteren Verbindungen mehr annimmt
	 * und alle vorhandenen Verbindungen abbricht.
	 */
	void shutdown();
	
	/**
	 * TODO Beschreibung
	 */
	void acquireConnection() throws InterruptedException;
	
	/**
	 * Wird aufgerufen, wenn eine Connection erhalten und aufgebaut wurde.
	 * @param connection Die Connection
	 */
	void onConnectionReceived(ChatServerConnection connection);
	
	/**
	 * Wird aufgerufen, wenn eine Connection geschlossen wurde.
	 * @param connection Die Connection
	 */
	void onConnectionDropped(ChatServerConnection connection);
}
