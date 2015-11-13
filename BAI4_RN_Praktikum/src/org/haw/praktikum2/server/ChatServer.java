package org.haw.praktikum2.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.haw.praktikum2.Protokoll;

public class ChatServer {
	private static final Logger LOGGER = Logger.getLogger(ChatServer.class.getName());
	
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
			worker.send(Protokoll.message + " " + username + " " + message);
		}
	}
	
	public List<ChatServerWorkerThread> getConnectedWorkers() {
		return Collections.unmodifiableList(_workers);
	}
	
	public static void main(String[] args) throws Exception {
		try {
			LogManager.getLogManager().readConfiguration(ClassLoader.getSystemResourceAsStream("praktikum2/chat_server.properties"));
			ChatServer server = new ChatServer(56789, 100);
			server.run();
		} catch(Exception e) {
			LOGGER.severe(e.toString());
		}
	}
}
