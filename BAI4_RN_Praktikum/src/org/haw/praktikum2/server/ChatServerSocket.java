package org.haw.praktikum2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ChatServerSocket implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ChatServerSocket.class.getName());
	
	private Thread _thread;
	private boolean _serviceRequested;
	
	private int _port;
	private ChatServer _server;
	private ServerSocket _serverSocket;
	
	public ChatServerSocket(ChatServer server, int port) throws IOException {
		_server = server;
		_port = port;
	}
	
	public void start() {
		if(_thread == null) {
			_thread = new Thread(this);
			_serviceRequested = true;
			_thread.start();
		}
	}
	
	public void stop() {
		if(_thread != null) {
			_serviceRequested = false;
			_thread.interrupt();
		}
	}
	
	@Override
	public void run() {
		try {
			_serverSocket = new ServerSocket(_port);
			
			while(_serviceRequested && !Thread.interrupted()) {
				_server.acquire();
				
				LOGGER.fine("TCP Server is waiting for connection - listening TCP port " + _serverSocket.getLocalPort());
				Socket socket = _serverSocket.accept();
				
				ChatServerWorkerThread worker = new ChatServerWorkerThread(socket, _server);
				worker.start();
			}
			
			_serverSocket.close();
		} catch(Exception e) {
			LOGGER.severe(e.toString());
		}
	}
}
