package org.haw.praktikum3.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.haw.praktikum3.Protokoll;
import org.haw.praktikum3.client.ui.ChatClientUI;

public class ChatClientEmpfaengerThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatClientEmpfaengerThread.class.getName());
	
	private ChatClientUI _ui;
	private BufferedReader _in;
	private String _username;
	
	public ChatClientEmpfaengerThread(ChatClientUI ui, BufferedReader in, String username) {
		_ui = ui;
		_in = in;
		_username = username;
	}
	
	@Override
	public void run() {
		boolean running = true;
		try {
			while(running) {
				String input = _in.readLine();
				StringTokenizer tokenizer = new StringTokenizer(input, " ");
				String command = tokenizer.nextToken();
				
				if((Protokoll.BYE + " " + _username).equals(command)) {
					running = false;
				} else {
					String username = tokenizer.nextToken();
					String message = tokenizer.nextToken();
					while(tokenizer.hasMoreTokens()) {
						message += " " + tokenizer.nextToken();
					}
					_ui.showMessage(username, message);
				}
			}
		} catch(IOException e) {
			LOGGER.severe("Fehler beim Erhalten einer Nachricht!");
			LOGGER.severe(e.toString());
		}
		LOGGER.fine("ClientEmpfaengerThread beendet");
	}
}