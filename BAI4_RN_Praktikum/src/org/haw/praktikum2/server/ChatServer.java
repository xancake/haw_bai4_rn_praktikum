package org.haw.praktikum2.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Semaphore;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.haw.praktikum2.Protokoll;

public class ChatServer {
	private static final Logger LOGGER = Logger.getLogger(ChatServer.class.getName());
	
	private static final String PROPERTIES_DEFAULT = "praktikum2/chat_server.properties";
	private static final String PROPERTY_SERVER_PORT  = "chat.server.port";
	private static final String PROPERTY_SERVER_LIMIT = "chat.server.limit";
	
	private ChatServerSocket _serverSocket;
	private ServerEingabeThread _serverEingabe;
	private Semaphore _workerThreadsSem;
	private List<ChatServerWorkerThread> _workers;
	
	public ChatServer(int serverPort, int maxThreads) throws IOException {
		_serverSocket = new ChatServerSocket(this, serverPort);
		_serverEingabe = new ServerEingabeThread(this);
		_workerThreadsSem = new Semaphore(maxThreads);
		_workers = new ArrayList<>();
	}
	
	public void run() {
		_serverSocket.start();
		_serverEingabe.start();
	}
	
	public ChatServerSocket getChatServerSocket() {
		return _serverSocket;
	}
	
	public void acquire() throws InterruptedException {
		_workerThreadsSem.acquire();
	}
	
	public void release(ChatServerWorkerThread worker) {
		_workers.remove(worker);
		_workerThreadsSem.release();
	}
	
	public void addWorker(ChatServerWorkerThread worker) {
		_workers.add(worker);
	}
	
	public void sendAll(String username, String message) throws IOException {
		for(ChatServerWorkerThread worker : _workers) {
			worker.send(Protokoll.RECEIVE_MESSAGE + " " + username + " " + message);
		}
	}
	
	public List<ChatServerWorkerThread> getConnectedWorkers() {
		return Collections.unmodifiableList(_workers);
	}
	
	public static void main(String[] args) throws Exception {
		LogManager.getLogManager().readConfiguration(ClassLoader.getSystemResourceAsStream("praktikum2/chat_server.properties"));
		
		try {
			Properties properties = new Properties();
			properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_DEFAULT));
			
			int port = Integer.parseInt(properties.getProperty(PROPERTY_SERVER_PORT));
			int limit = Integer.parseInt(properties.getProperty(PROPERTY_SERVER_LIMIT));
			
			ChatServer server = new ChatServer(port, limit);
			server.run();
		} catch(Exception e) {
			LOGGER.severe(e.toString());
		}
	}
}
