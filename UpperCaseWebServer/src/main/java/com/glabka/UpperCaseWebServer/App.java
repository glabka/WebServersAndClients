package com.glabka.UpperCaseWebServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.glassfish.tyrus.server.Server;

public class App {
	
    public static void main(String[] args) {
    	String serverName =  args[0]; // an ip adress or localhost
    	int portNumber = Integer.parseInt(args[1]);
    	
    	Server server = new Server(serverName, portNumber, "/websockets", UpperCaseMakerEndpoint.class);
    	
    	try {
    		server.start();
    		System.out.print("Please press a key to stop the server.");
    		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            reader.readLine();
    	} catch (Exception e) {
    		throw new RuntimeException(e);
    	} finally {
    		server.stop();
    	}
    }
}
