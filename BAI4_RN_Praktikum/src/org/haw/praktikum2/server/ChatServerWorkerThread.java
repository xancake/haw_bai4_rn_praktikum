package org.haw.praktikum2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.haw.praktikum2.Protokoll;
import org.haw.praktikum2.old.shared.connection.LoggingPrintWriter;
import org.haw.praktikum2.shared.io.LoggingBufferedReader;

/**
 * Arbeitsthread, der eine existierende Socket-Verbindung zur Bearbeitung erhaelt
 */
class ChatServerWorkerThread extends Thread {
	private static final Logger LOGGER = Logger.getLogger(ChatServerWorkerThread.class.getName());
	
	private String _username;
	private ChatServer _server;
	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;
	boolean _serviceRequested = true;
	
	public ChatServerWorkerThread(Socket socket, ChatServer server) throws IOException {
		_socket = socket;
		_server = server;
		_in = new LoggingBufferedReader(_socket.getInputStream());
		_out = new LoggingPrintWriter(_socket.getOutputStream());
	}
	
	public void run() {
		LOGGER.fine("TCP Worker Thread " + _username + " is running until 'quit' is received!");
		
		try {
			while(_serviceRequested) {
				StringTokenizer tokenizer = new StringTokenizer(_in.readLine(), " ");
				String command = tokenizer.nextToken();
				
				if(Protokoll.authentification.equals(command)) {
					String username = tokenizer.nextToken();
					if(checkUsername(username)) {
						_username = username;
						_out.println(Protokoll.auth_accept);
					} else {
						_out.println(Protokoll.auth_decline);
					}
				} else if(Protokoll.message.equals(command)) {
					String message = tokenizer.nextToken();
					while(tokenizer.hasMoreTokens()) {
						message += " " + tokenizer.nextToken();
					}
					_server.sendAll(_username, message);
				} else if(Protokoll.quit.equals(command)) {
					_serviceRequested = false;
					_out.println(Protokoll.quit);
				}
			}
			
			_socket.close();
		} catch(IOException e) {
			LOGGER.warning("Connection aborted by client!");
		} finally {
			LOGGER.fine("TCP Worker Thread " + _username + " stopped!");
			_server.release(); // Platz für neuen Thread freigeben
		}
	}
	
	public void send(String message) throws IOException {
		_out.println(message);
	}
	
	private boolean checkUsername(String username) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
		return pattern.matcher(username).matches();
	}
}