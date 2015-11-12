package org.haw.praktikum2.client.core;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

import org.haw.praktikum2.shared.exception.ChatException;
import org.haw.praktikum2.shared.protocol.Request;
import org.haw.praktikum2.shared.protocol.Response;

public class ChatClientConnection implements Closeable {
	private static final Logger LOGGER = Logger.getLogger(ChatClientConnection.class.getName());
	
	private Socket _socket;
	private ObjectInputStream _in;
	private ObjectOutputStream _out;
	
	public ChatClientConnection(String host, int port) throws IOException {
		_socket = new Socket(host, port);
		_in = new ObjectInputStream(_socket.getInputStream());
		_out = new ObjectOutputStream(_socket.getOutputStream());
	}
	
	public Response send(Request request) throws IOException, ChatException {
		try {
			send0(request);
			Response response = receive();
			
//			if(!isValidResponse(response)) { // TODO: Unerwartetes Response anhand des Zustands abfangen
//				throw new BadProtocolException(request.toString(), response.toString());
//			}
			
			return response;
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e); // TODO: evtl. spezifischere Exception
		}
	}
	
	private void send0(Request request) throws IOException {
		LOGGER.fine("[SEND] " + request);
		_out.writeObject(request);
	}
	
	private Response receive() throws IOException, ClassNotFoundException {
		Response response = (Response)_in.readObject();
		LOGGER.fine("[RECV] " + response);
		return response;
	}
	
	@Override
	public void close() throws IOException {
		_out.close();
		_in.close();
		_socket.close();
	}
}
