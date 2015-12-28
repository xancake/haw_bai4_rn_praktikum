package org.haw.rn.praktikum3.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.haw.rn.praktikum3.Protokoll;
import org.haw.rn.praktikum3.client.ui.ChatClientUI;

public class ChatClientEmpfaengerThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatClientEmpfaengerThread.class.getName());
	
	private ChatClientUI _ui;
	private ObjectInputStream _in;
	private String _username;
	
	public ChatClientEmpfaengerThread(ChatClientUI ui, ObjectInputStream in, String username) {
		_ui = ui;
		_in = in;
		_username = username;
	}
	
	@Override
	public void run() {
		boolean running = true;
		try {
			while(running) {
				String input = ((String)_in.readObject()).trim();
				StringTokenizer tokenizer = new StringTokenizer(input, " ");
				String command = tokenizer.nextToken();
				
				if(Protokoll.BYE.equals(command)) {
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
		} catch (ClassNotFoundException e) {
			LOGGER.severe("Fehler beim Erhalten einer Nachricht!");
			LOGGER.severe(e.toString());
		}
		LOGGER.fine("ClientEmpfaengerThread beendet");
	}
}