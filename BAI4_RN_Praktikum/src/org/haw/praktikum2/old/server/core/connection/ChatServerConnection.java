package org.haw.praktikum2.old.server.core.connection;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import org.haw.praktikum2.old.server.core.ChatServerCore;
import org.haw.praktikum2.old.server.core.connection.state.ChatServerConnectionState;
import org.haw.praktikum2.old.server.core.connection.state.NotConnectedState;
import org.haw.praktikum2.old.shared.connection.LoggingBufferedReader;
import org.haw.praktikum2.old.shared.connection.LoggingPrintWriter;

public class ChatServerConnection extends Thread implements Closeable {
	private ChatServerCore _server;
	
	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;
	
	private ChatServerConnectionState _state;
	private boolean _serviceRequested;
	
	private String _username;
	
	public ChatServerConnection(ChatServerCore server, Socket socket) throws IOException {
		_server = server;
		_state = new NotConnectedState();
		_socket = socket;
		_in = new LoggingBufferedReader(_socket.getInputStream());
		_out = new LoggingPrintWriter(_socket.getOutputStream());
	}
	
	@Override
	public void run() {
		_serviceRequested = true;
		
		try {
			while(_serviceRequested && !Thread.interrupted()) {
				String requestString = _in.readLine();
				StringTokenizer requestTokenizer = new StringTokenizer(requestString, " ");
				String command = requestTokenizer.nextToken();
				String response = _state.handle(this, command, requestTokenizer);
				_out.println(response);
			}
		} catch(IOException e) {
			
		} finally {
			try {
				close();
			} catch (IOException e) {}
		}
	}
	
	public String getUsername() {
		return _username;
	}
	
	public void setUsername(String username) {
		_username = username;
	}
	
	public ChatServerConnectionState getConnectionState() {
		return _state;
	}
	
	public void setConnectionState(ChatServerConnectionState state) {
		_state = state;
	}
	
	public boolean isServiceRequested() {
		return _serviceRequested;
	}
	
	public void setServiceRequested(boolean serviceRequested) {
		_serviceRequested = serviceRequested;
	}
	
	@Override
	public void close() throws IOException {
		_server.onConnectionDropped(this);
		_out.close();
		_in.close();
		_socket.close();
	}
}
