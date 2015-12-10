package org.haw.praktikum3.client;

import java.io.PrintWriter;
import java.util.logging.Logger;
import org.haw.praktikum3.Protokoll;
import org.haw.praktikum3.client.ui.ChatClientUI;

public class ChatClientSenderThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatClientSenderThread.class.getName());
	
	private ChatClientUI _ui;
	private PrintWriter _out;
	private String _username;
	
	public ChatClientSenderThread(ChatClientUI ui, PrintWriter out, String username) {
		_ui = ui;
		_out = out;
		_username = username;
	}
	
	@Override
	public void run() {
		boolean running = true;
		_ui.showStatusmeldung("ChatClient gestartet -- beenden mit '/quit'");
		while(running) {
			_ui.showStatusmeldung("Nachricht eingeben: ");
			String input = _ui.getEingabe().trim();
			
			if("logout".equals(input)) {
				running = false;
				_out.println(Protokoll.QUIT);
			} else if("whoisin".equals(input)) {
				_out.println(Protokoll.LIST_USERS);
			} else {
				_out.println(input);
			}
		}
		LOGGER.fine("ClientSenderThread beendet");
	}
}
