package org.haw.rn.praktikum3.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

import org.haw.rn.praktikum3.Protokoll;
import org.haw.rn.praktikum3.client.ui.ChatClientUI;

public class ChatClientSenderThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatClientSenderThread.class.getName());
	
	private ChatClientUI _ui;
	private ObjectOutputStream _out;
	private String _username;
	
	public ChatClientSenderThread(ChatClientUI ui, ObjectOutputStream out, String username) {
		_ui = ui;
		_out = out;
		_username = username;
	}
	
	@Override
	public void run() {
		boolean running = true;
		_ui.showStatusmeldung("ChatClient gestartet -- beenden mit '/quit'");
		while(running) {
			try {
				_ui.showStatusmeldung("Nachricht eingeben: ");
				String input = _ui.getEingabe().trim();

				if(input.startsWith("/")) {
					if("/quit".equals(input)) {
						running = false;
						_out.writeObject(Protokoll.QUIT);
					} else if("/list-users".equals(input)) {
						_out.writeObject(Protokoll.LIST_USERS);
					}
				} else {
					_out.writeObject(input);
				}
			} catch(IOException e) {
				LOGGER.severe("Fehler beim Senden einer Nachricht!");
				LOGGER.severe(e.toString());
			}
		}
		LOGGER.fine("ClientSenderThread beendet");
	}
}
