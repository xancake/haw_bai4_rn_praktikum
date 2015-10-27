package org.haw.praktikum1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;

public class SMTPMailSender implements AutoCloseable {
	private static final Logger LOGGER = Logger.getLogger(SMTPMailSender.class.getName());
	
	private static final int PORT_SSL = 465;
	
	private static final String BOUNDARY = "98766789";
	
	private String _username;
	private String _password;
	
	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;
	private OutputStream _outS;
	
	public SMTPMailSender(String smtpServer, int smtpPort, String username, String password) throws UnknownHostException, IOException {
		_username = username;
		_password = password;
		_socket = createSocket(smtpServer, smtpPort);
		_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		_out = new PrintWriter(_socket.getOutputStream(), true);
		_outS = _socket.getOutputStream();
		connect();
	}
	
	private static Socket createSocket(String smtpServer, int smtpPort) throws UnknownHostException, IOException {
		Socket socket = null;
		if(smtpPort == PORT_SSL) {
			socket = SSLSocketFactory.getDefault().createSocket(smtpServer, smtpPort);
		} else {
			socket = new Socket(smtpServer, smtpPort);
		}
		return socket;
	}
	
	private void connect() throws IOException {
		receive();
		send("EHLO " + _socket.getLocalAddress().getHostName());
		do {
			receive();
		} while(_in.ready());
		send("AUTH LOGIN");
		receive();
		send(Base64.encodeBytes((_username).getBytes()));
		receive();
		send(Base64.encodeBytes((_password).getBytes()));
		receive();
	}
	
	public void sendMail(String sender, String recipient, String subject, String message, String... filePaths) throws IOException {
		send("MAIL FROM: <" + sender + ">");
		receive();
		send("RCPT TO: <" + recipient + ">");
		receive();
		send("DATA");
		receive();
		sendData(sender, recipient, subject, message, filePaths);
		send("");
		send(".");
		receive();
	}
	
	private void sendData(String sender, String recipient, String subject, String message, String... filePaths) throws IOException {
		boolean hasAnhaenge = filePaths != null && filePaths.length > 0;
		send("From: " + sender);
		send("To: " + recipient);
		send("Subject: " + subject);
		send("MIME-Version: 1.0");
		if(hasAnhaenge) {
			send("Content-Type: multipart/mixed; boundary=" + BOUNDARY);
			send("");
			send("--" + BOUNDARY);
		}
		send("Content-Transfer-Encoding: base64");
		send("Content-Type: text/plain");
		send("");
		send(Base64.encodeBytes(message.getBytes()));
		for(String filePath : filePaths) {
			File file = new File(filePath);
			send("--" + BOUNDARY);
			send("Content-Transfer-Encoding: base64");
			send("Content-Type: "); // TODO: how to ermittel content-type
			send("Content-Disposition: attachment; filename=" + file.getName());
			send("");
			
			try(InputStream fileIn = new FileInputStream(file)) {
				byte[] bytes = new byte[32];
				while(fileIn.available() > bytes.length) {
					fileIn.read(bytes);
					send(Base64.encodeBytesToBytes(bytes));
				}
				fileIn.read(bytes, 0, fileIn.available());
				send(Base64.encodeBytesToBytes(bytes));
			}
			send("");
		}
		if(hasAnhaenge) {
			send("--" + BOUNDARY + "--");
		}
	}
	
	private void send(String string) {
		_out.println(string);
		LOGGER.info("[SEND] " + string);
	}
	
	private void send(byte[] bytes) throws IOException {
		_outS.write(bytes);
		LOGGER.fine("[SENB] " + new String(bytes));
	}
	
	private String receive() throws IOException {
		String string = _in.readLine();
		LOGGER.info("[RECV] " + string);
		return string;
	}
	
	@Override
	public void close() throws Exception {
		send("QUIT");
		receive();
		_in.close();
		_out.close();
		_outS.close();
		_socket.close();
	}
}
