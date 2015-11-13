package org.haw.praktikum2.old.server.core;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.haw.praktikum2.old.server.core.connection.ChatServerConnection;

public class ChatServerImpl implements ChatServerCore {
	private Semaphore _maxConnections;
	private ChatServerSocket _serverSocket;
	private List<ChatServerConnection> _connections;
	
	public ChatServerImpl(int port, int maxConnections) throws IOException {
		_serverSocket = new ChatServerSocket(this, port);
		_maxConnections = new Semaphore(maxConnections);
	}
	
	@Override
	public void startup() {
		_serverSocket.start();
	}
	
	@Override
	public void shutdown() {
		_serverSocket.interrupt();
		// TODO: Connections killen
	}
	
	@Override
	public void acquireConnection() throws InterruptedException {
		_maxConnections.acquire();
	}
	
	@Override
	public void onConnectionReceived(ChatServerConnection connection) {
		_connections.add(connection);
		connection.start();
	}

	@Override
	public void onConnectionDropped(ChatServerConnection connection) {
		_connections.remove(connection);
		_maxConnections.release();
	}
}
