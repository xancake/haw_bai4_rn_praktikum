package org.haw.praktikum2.server.core;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

import org.haw.praktikum2.server.core.connection.ChatServerConnection;

public class ChatServerSocket extends Thread implements Closeable {
	private ChatServerCore _server;
	private ServerSocket _serverSocket;
	
	public ChatServerSocket(ChatServerCore server, int port) throws IOException {
		_server = Objects.requireNonNull(server);
		_serverSocket = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
				_server.acquireConnection();
				
				Socket socket = _serverSocket.accept();
				
				ChatServerConnection connection = new ChatServerConnection(_server, socket);
				_server.onConnectionReceived(connection);
				
			} catch(IOException e) {
				// TODO: Exception handlen: ggf. kann ein Listener registriert werden
				//       über den z.B. die GUI benachrichtigt werden kann
				e.printStackTrace();
			} catch(InterruptedException e) {
				
			}
		}
	}
	
	@Override
	public void close() throws IOException {
		_serverSocket.close();
	}
}
