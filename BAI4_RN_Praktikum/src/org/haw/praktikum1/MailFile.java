package org.haw.praktikum1;

import java.util.Properties;

public class MailFile {
	private static final String PROPERTIES_DEFAULT = "praktikum1/mail.properties";
	
	private static final String PROPERTY_SOURCE_ADDRESS = "mail.source.address";
	private static final String PROPERTY_USERNAME       = "mail.user.name";
	private static final String PROPERTY_PASSWORD       = "mail.user.password";
	private static final String PROPERTY_SMTP_SERVER    = "mail.smtp.server";
	private static final String PROPERTY_SMTP_PORT      = "mail.smtp.port";
	
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
			
			MailSender mailer = new MailSender(smtpServer, smtpPort, username, password);
			mailer.sendMail(sender, recipient, filePaths);
		} else {
			printUsage();
		}
	}
}
