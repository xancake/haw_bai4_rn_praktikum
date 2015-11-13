package org.haw.praktikum2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.haw.praktikum2.Protokoll;
import org.haw.praktikum2.old.shared.connection.LoggingPrintWriter;
import org.haw.praktikum2.shared.io.LoggingBufferedReader;

public class ChatClient {
	private static final Logger LOGGER = Logger.getLogger(ChatClient.class.getName());
	
	private Socket socket;
	private BufferedReader _in;
	private PrintWriter _out;
	
	public ChatClient(String hostname, int serverPort) throws IOException {
		socket = new Socket(hostname, serverPort);
		_in = new LoggingBufferedReader(socket.getInputStream());
		_out = new LoggingPrintWriter(socket.getOutputStream());
	}
	
	public void start() {
		Scanner scanner = new Scanner(System.in);
		
		try {
			boolean authentifiziert = false;
			do {
				System.out.print("Bitte geben Sie ihren Benutzernamen ein: ");
				String username = scanner.nextLine();
				authentifiziert = authenticate(username);
				if(!authentifiziert) {
					System.out.println("Der Benutzername wurde vom Server nicht akzeptiert");
				}
			} while(!authentifiziert);
			
			ChatClientEmpfaengerThread empfaenger = new ChatClientEmpfaengerThread(_in);
			ChatClientSenderThread sender = new ChatClientSenderThread(_out, scanner);
			empfaenger.start();
			sender.start();
			
			empfaenger.join();
			sender.join();
			
		} catch(IOException e) {
			LOGGER.severe("Connection aborted by server!");
		} catch(InterruptedException e) {
			LOGGER.severe(e.toString());
		} finally {
			scanner.close();
			try {
				socket.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		LOGGER.fine("TCP Client stopped!");
	}
	
	private boolean authenticate(String username) throws IOException {
		_out.println(Protokoll.authentification + " " + username);
		String response = _in.readLine();
		if(Protokoll.auth_accept.equals(response)) {
			return true;
		} else if(Protokoll.auth_decline.equals(response)) {
			return false;
		} else {
			// Könnte auch eine Exception aufgrund fehlerhaften Protokolls werfen
			return false;
		}
	}
	
	public static void main(String[] args) {
		try {
			LogManager.getLogManager().readConfiguration(ClassLoader.getSystemResourceAsStream("praktikum2/chat_client.properties"));
			ChatClient client = new ChatClient("localhost", 56789);
			client.start();
		} catch(Exception e) {
			LOGGER.severe(e.toString());
		}
	}
}
