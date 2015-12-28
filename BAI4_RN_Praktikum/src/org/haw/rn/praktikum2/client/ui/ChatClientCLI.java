package org.haw.rn.praktikum2.client.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ChatClientCLI implements ChatClientUI {
	private Scanner _scanner;
	private PrintStream _out;
	
	public ChatClientCLI(InputStream in, PrintStream out) {
		_scanner = new Scanner(in);
		_out = out;
	}
	
	@Override
	public String getEingabe() {
		return _scanner.nextLine();
	}
	
	@Override
	public void showStatusmeldung(String meldung) {
		_out.println(meldung);
	}
	
	@Override
	public void showUsers(List<String> users) {
		_out.println(Arrays.toString(users.toArray(new String[] {})));
	}
	
	@Override
	public void showMessage(String username, String message) {
		_out.println(username + ": " + message);
	}
}
