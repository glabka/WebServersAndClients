package com.glabka.LowerCaseWebClient;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

import org.glassfish.tyrus.client.ClientManager;

public class App {
	
	public static void main(String[] args){
		String serverName = args[0];
		int portNumber = Integer.parseInt(args[1]);
		
		CountDownLatch latch = new CountDownLatch(1);
		WebClientEndpoint clientEndPoint = new WebClientEndpoint(latch);
		ClientManager clientManager = ClientManager.createClient();
				
		try {
			clientManager.connectToServer(clientEndPoint, new URI("ws://" + serverName + ":" + portNumber + "/websockets/uppercasemaker"));
			latch.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
