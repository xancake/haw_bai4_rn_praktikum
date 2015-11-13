package org.haw.praktikum2.server;

import java.util.Scanner;
import java.util.StringTokenizer;

public class ServerEingabeThread extends Thread {
	private ChatServer _server;
	
	public ServerEingabeThread(ChatServer server) {
		_server = server;
	}
	
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		while(running) {
			System.out.print("Befehl: ");
			StringTokenizer tokenizer = new StringTokenizer(scanner.next().trim(), " ");
			String command = tokenizer.nextToken();
			
			if("quit".equals(command)) {
				_server.getChatServerSocket().stop();
				running = false;
			} else {
				System.out.println("Der Befehl " + command + " existiert nicht");
			}
		}
		
		scanner.close();
	}
}