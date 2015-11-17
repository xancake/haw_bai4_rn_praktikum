package org.haw.praktikum2;

public interface Protokoll {
	String AUTHENTIFICATION = "auth";
	String AUTH_ACCEPT      = "accept";
	String AUTH_DECLINE     = "decline";
	
	String LIST_USERS = "list_users";
	String USERS      = "users";
	
	String SEND_MESSAGE    = "smsg";
	String RECEIVE_MESSAGE = "rmsg";
	
	String QUIT = "quit";
	String BYE  = "bye";
}
