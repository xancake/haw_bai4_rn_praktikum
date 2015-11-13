package org.haw.praktikum2.old.client.core;

import java.io.Closeable;
import java.io.IOException;
import org.haw.praktikum2.old.shared.exception.ChatException;
import org.haw.praktikum2.old.shared.protocol.Response;
import org.haw.praktikum2.old.shared.protocol.authentication.AuthenticationRequest;
import org.haw.praktikum2.old.shared.protocol.quit.QuitRequest;
import org.haw.praktikum2.old_old.shared.protocol.join.JoinRequest;
import org.haw.praktikum2.old_old.shared.protocol.leave.LeaveRequest;

public class ChatClientCore implements Closeable {
	private ChatClientConnection _connection;
	
	public ChatClientCore(String host, int port, String username) throws IOException, ChatException {
		_connection = new ChatClientConnection(host, port);
		authentifizieren(username);
	}
	
	private void authentifizieren(String username) throws IOException, ChatException {
		Response response = _connection.send(new AuthenticationRequest(username));
		if(!response.isSuccessResponse()) {
			throw response.getFailureException();
		}
	}
	
	public void joinRoom(String room) throws IOException, ChatException {
		Response response = _connection.send(new JoinRequest(room));
		
	}
	
	public void leaveRoom() throws IOException, ChatException {
		Response response = _connection.send(new LeaveRequest());
		
	}
	
	public void sendMessage(String message) throws IOException, ChatException {
//		Response response = _connection.send(new SendRequest(message)); //TODO
		
	}
	
	@Override
	public void close() throws IOException {
		try {
			_connection.send(new QuitRequest());
		} catch (ChatException e) {
			throw new IOException(e);
		} finally {
			_connection.close();
		}
	}
}
