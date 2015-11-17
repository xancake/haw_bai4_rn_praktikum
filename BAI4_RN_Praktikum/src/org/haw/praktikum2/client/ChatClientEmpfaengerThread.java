package org.haw.praktikum2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import org.haw.praktikum2.Protokoll;
import org.haw.praktikum2.client.ui.ChatClientUI;

public class ChatClientEmpfaengerThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatClientEmpfaengerThread.class.getName());
	
	private ChatClientUI _ui;
	private BufferedReader _in;
	
	public ChatClientEmpfaengerThread(ChatClientUI ui, BufferedReader in) {
		_ui = ui;
		_in = in;
	}
	
	@Override
	public void run() {
		boolean running = true;
		try {
			while(running) {
				String input = _in.readLine();
				StringTokenizer tokenizer = new StringTokenizer(input, " ");
				String command = tokenizer.nextToken();
				
				if(Protokoll.RECEIVE_MESSAGE.equals(command)) {
					String username = tokenizer.nextToken();
					String message = tokenizer.nextToken();
					while(tokenizer.hasMoreTokens()) {
						message += " " + tokenizer.nextToken();
					}
					_ui.showMessage(username, message);
				} else if(Protokoll.USERS.equals(command)) {
					List<String> users = new ArrayList<String>();
					while(tokenizer.hasMoreTokens()) {
						users.add(tokenizer.nextToken());
					}
					_ui.showUsers(users);
				} else if(Protokoll.QUIT.equals(command)) {
					running = false;
				} else {
					LOGGER.warning("Cannot understand input '" + input + "'");
				}
			}
		} catch(IOException e) {
			LOGGER.severe("Fehler beim Erhalten einer Nachricht!");
			LOGGER.severe(e.toString());
		}
		LOGGER.fine("ClientEmpfaengerThread beendet");
	}
}