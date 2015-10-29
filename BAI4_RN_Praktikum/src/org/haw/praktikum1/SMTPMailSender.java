package org.haw.praktikum1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.logging.Logger;

import javax.net.ssl.SSLSocketFactory;

public class SMTPMailSender implements AutoCloseable {
	private static final Logger LOGGER = Logger.getLogger(SMTPMailSender.class.getName());
	
	private static final int PORT_SSL = 465;
	
	private static final String BOUNDARY = "98766789";
	
	private Socket _socket;
	private BufferedReader _in;
	private PrintWriter _out;
	
	public SMTPMailSender(String smtpServer, int smtpPort, String username, String password) throws UnknownHostException, IOException {
		_socket = createSocket(smtpServer, smtpPort);
		_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		_out = new PrintWriter(_socket.getOutputStream(), true);
		connect(username, password);
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
	
	private void connect(String username, String password) throws IOException {
		receive();
		send("EHLO " + _socket.getLocalAddress().getHostName());
		do {
			receive();
		} while(_in.ready());
		send("AUTH LOGIN");
		receive();
		send(Base64.encodeBytes((username).getBytes()));
		receive();
		send(Base64.encodeBytes((password).getBytes()));
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
		send("Content-Transfer-Encoding: quoted-printable");
		send("Content-Type: text/plain");
		send("");
		send(message.replaceAll("([\\n\\r])\\.", "$1.."));
		for(String filePath : filePaths) {
			File file = new File(filePath);
			send("--" + BOUNDARY);
			send("Content-Transfer-Encoding: base64");
			send("Content-Type: application/txt");
			send("Content-Disposition: attachment; filename=" + file.getName());
			send("");
			send(Base64.encodeBytes(Files.readAllBytes(file.toPath())));
		}
		if(hasAnhaenge) {
			send("--" + BOUNDARY + "--");
		}
	}
	
	private void send(String string) {
		_out.println(string);
		LOGGER.info("[SEND] " + string);
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
		_socket.close();
	}
}
