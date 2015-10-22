package org.haw.praktikum1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;

public class MailSender {
	private static final Logger LOGGER = Logger.getLogger(MailSender.class.getName());
	
	private static final int PORT_SSL = 465;
	
	private String _smtpServer;
	private int _smtpPort;
	private String _username;
	private String _password;
	
	public MailSender(String smtpServer, int smtpPort, String username, String password) {
		_smtpServer = smtpServer;
		_smtpPort = smtpPort;
		_username = username;
		_password = password;
	}
	
	public void sendMail(String sender, String recipient, String... filePaths) throws UnknownHostException, IOException {
		Socket socket = connect(_smtpServer, _smtpPort);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		
	}
	
	private static Socket connect(String smtpServer, int smtpPort) throws UnknownHostException, IOException {
		Socket socket = null;
		if(smtpPort == PORT_SSL) {
			socket = SSLSocketFactory.getDefault().createSocket(smtpServer, smtpPort);
		} else {
			socket = new Socket(smtpServer, smtpPort);
		}
		return socket;
	}
	
	private void send(PrintWriter out, String string) {
		out.println(string);
		LOGGER.fine("[SEND] " + string);
	}
	
	private String receive(BufferedReader in) throws IOException {
		String string = in.readLine();
		LOGGER.fine("[RECEIVE] " + string);
		return string;
	}
}
