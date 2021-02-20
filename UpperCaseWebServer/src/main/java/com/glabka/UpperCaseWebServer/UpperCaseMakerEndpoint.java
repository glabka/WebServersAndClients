package com.glabka.UpperCaseWebServer;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/uppercasemaker")
public class UpperCaseMakerEndpoint {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected to session:" + session.getId());
	}
	
	@OnMessage
	public String onMessage(String message, Session session) {
		if(message.toUpperCase().equals("STOP")) {
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "User ends program"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return message.toUpperCase();
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info("Session " + session.getId() + " closed");
	}
	
}
