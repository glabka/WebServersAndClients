package com.glabka.LowerCaseWebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class WebClientEndpoint {

	private CountDownLatch latch;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public WebClientEndpoint(CountDownLatch latch) {
		this.latch = latch;
	}

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected:");

		try {
			session.getBasicRemote().sendText("start");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println(message);
			String userInput = bufferRead.readLine();
			return userInput;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info("Session " + session.getId() + " closed.");
		latch.countDown();
	}
}
