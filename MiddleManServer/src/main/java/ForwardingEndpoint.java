

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

// TODO
@ServerEndpoint(value = "/uppercasemaker")
public class ForwardingEndpoint {
	
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@OnOpen
	public void onOpen(Session session) throws IOException {
		if(clients.size() < 2) {
			clients.add(session);
		} else {
			session.close();
		}
	}
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		synchronized(clients) {
			
			System.out.println("message from " + session.getId()+ " received: " + message);
			for(Session client : clients) {
				if(!client.equals(session)) {
					System.out.println("message to" + client.getId() + " sent: " + message);
					client.getBasicRemote().sendText(message);
				}
			}
		}
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		clients.remove(session);
		logger.info("Session " + session.getId() + " closed");
	}
	
}
