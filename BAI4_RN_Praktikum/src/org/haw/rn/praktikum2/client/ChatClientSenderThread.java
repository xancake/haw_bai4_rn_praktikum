package org.haw.rn.praktikum2.client;

import java.io.PrintWriter;
import java.util.logging.Logger;

import org.haw.rn.praktikum2.Protokoll;
import org.haw.rn.praktikum2.client.ui.ChatClientUI;

public class ChatClientSenderThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatClientSenderThread.class.getName());
	
	private ChatClientUI _ui;
	private PrintWriter _out;
	
	public ChatClientSenderThread(ChatClientUI ui, PrintWriter out) {
		_ui = ui;
		_out = out;
	}
	
	@Override
	public void run() {
		boolean running = true;
		_ui.showStatusmeldung("ChatClient gestartet -- beenden mit '/quit'");
		while(running) {
			_ui.showStatusmeldung("Nachricht eingeben: ");
			String input = _ui.getEingabe().trim();
			
			if(input.startsWith("/")) {
				if("/quit".equals(input)) {
					running = false;
					_out.println(Protokoll.QUIT);
				} else if("/list-users".equals(input)) {
					_out.println(Protokoll.LIST_USERS);
				}
			} else {
				_out.println(Protokoll.SEND_MESSAGE + " " + input);
			}
		}
		LOGGER.fine("ClientSenderThread beendet");
	}
}
