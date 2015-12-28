package org.haw.rn.praktikum3.client.ui;

import java.util.List;

public interface ChatClientUI {
	
	String getEingabe();
	
	
	void showStatusmeldung(String meldung);
	
	
	void showUsers(List<String> users);
	
	
	void showMessage(String username, String message);
}
