package org.haw.praktikum2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import org.haw.praktikum2.Protokoll;

public class ChatClientEmpfaengerThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatClientEmpfaengerThread.class.getName());
	
	private BufferedReader _in;
	
	public ChatClientEmpfaengerThread(BufferedReader in) {
		_in = in;
	}
	
	@Override
	public void run() {
		boolean running = true;
		try {
			while(running) {
				StringTokenizer tokenizer = new StringTokenizer(_in.readLine(), " ");
				String command = tokenizer.nextToken();
				
				if(Protokoll.message.equals(command)) {
					String username = tokenizer.nextToken();
					String message = tokenizer.nextToken();
					while(tokenizer.hasMoreTokens()) {
						message += " " + tokenizer.nextToken();
					}
					System.out.println(username + ": " + message);
				} else if(Protokoll.quit.equals(command)) {
					running = false;
				}
			}
		} catch(IOException e) {
			LOGGER.severe("Fehler beim Erhalten einer Nachricht!");
			LOGGER.severe(e.toString());
		}
		LOGGER.fine("ClientEmpfaengerThread beendet");
	}
}