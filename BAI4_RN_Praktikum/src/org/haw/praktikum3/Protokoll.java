package org.haw.praktikum3;

public interface Protokoll {
	String AUTHENTIFICATION = "";
	String AUTH_ACCEPT      = "OK";
	String AUTH_DECLINE     = "";
	
	String LIST_USERS = "whoisin";
	String USERS      = "";
	
	String SEND_MESSAGE    = "smsg";
	String RECEIVE_MESSAGE = "rmsg";
	
	String QUIT = "logout";
	String BYE  = "299";
}
