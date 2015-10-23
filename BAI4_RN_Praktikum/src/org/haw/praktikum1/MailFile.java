package org.haw.praktikum1;

import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MailFile {
	private static final String PROPERTIES_DEFAULT = "praktikum1/mail.properties";
	
	private static final String PROPERTY_SOURCE_ADDRESS = "mail.source.address";
	private static final String PROPERTY_USERNAME       = "mail.user.name";
	private static final String PROPERTY_PASSWORD       = "mail.user.password";
	private static final String PROPERTY_SMTP_SERVER    = "mail.smtp.server";
	private static final String PROPERTY_SMTP_PORT      = "mail.smtp.port";
	
	private static final String BETREFF = "Quatschnasen UNITE!";
	private static final String NACHRICHT =
			"Hallo du Quatschnase!\n" +
			"\n" +
			"Gründe, warum du eine Quatschnase bist:\n" +
			"- Grüner Hut\n" +
			"- Große Füße\n" + 
			"- Lautes Schnarchen\n" +
			"\n" +
			"Mit freundlichen Grüßen,\n" +
			"\n" +
			"Ihr Quatschnasenverein e.V.";

	private static void printUsage() {
		StringBuilder sb = new StringBuilder();
		sb.append("MailFile <recipient> {filepaths}\n");
		sb.append("\n");
		sb.append("recipient\n");
		sb.append("\tDie Mail-Adresse des Empfängers\n");
		sb.append("filepaths\n");
		sb.append("\tEine Liste der anzuhängenden Dateien\n");
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args) throws Exception {
		Handler loggingHandler = new FileHandler("mail.log");
		loggingHandler.setFormatter(new SimpleFormatter());
		loggingHandler.setLevel(Level.ALL);
		Logger.getGlobal().addHandler(loggingHandler);
		
		if(args.length > 0) {
			String recipient = args[0];
			String[] filePaths = new String[args.length-1];
			for(int i=0; i<filePaths.length; i++) {
				filePaths[i] = args[i+1];
			}
			
			Properties properties = new Properties();
			properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_DEFAULT));
			
			String smtpServer = properties.getProperty(PROPERTY_SMTP_SERVER);
			int smtpPort = Integer.parseInt(properties.getProperty(PROPERTY_SMTP_PORT));
			
			String username = properties.getProperty(PROPERTY_USERNAME);
			String password = properties.getProperty(PROPERTY_PASSWORD);
			
			String sender = properties.getProperty(PROPERTY_SOURCE_ADDRESS);
			
			try(SMTPMailSender mailer = new SMTPMailSender(smtpServer, smtpPort, username, password)) {
				mailer.sendMail(sender, recipient, BETREFF, NACHRICHT, filePaths);
			}
		} else {
			printUsage();
		}
	}
}
