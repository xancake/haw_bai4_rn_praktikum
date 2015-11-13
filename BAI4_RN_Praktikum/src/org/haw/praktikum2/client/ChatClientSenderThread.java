package org.haw.praktikum2.client;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Logger;
import org.haw.praktikum2.Protokoll;

public class ChatClientSenderThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatClientSenderThread.class.getName());
	
	private PrintWriter _out;
	private Scanner _scanner;
	
	public ChatClientSenderThread(PrintWriter out, Scanner scanner) {
		_out = out;
		_scanner = scanner;
	}
	
	@Override
	public void run() {
		boolean running = true;
		while(running) {
			System.out.print("Nachricht eingeben: ");
			String input = _scanner.nextLine();
			
			if(Protokoll.quit.equals(input)) {
				running = false;
				_out.println(input);
			} else {
				_out.println(Protokoll.message + " " + input);
			}
		}
		LOGGER.fine("ClientSenderThread beendet");
	}
}
