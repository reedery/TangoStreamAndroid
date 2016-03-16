package edu.bc.reedelunt;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import java.io.IOException;

public class JSSocket extends WebSocketAdapter {
    
	private Session session;

    String zooKeeper = "localhost:2181";
    String groupId = "test-consumer-group";
    String topic = "data";
    int threads = 1;

    SimpleConsumer example = new SimpleConsumer(zooKeeper, groupId, topic);

    @Override
    public void onWebSocketConnect(Session connectedSession){
    	this.session = connectedSession;
        super.onWebSocketConnect(connectedSession);
        System.out.println("Socket Connected: " + connectedSession);
    }
    
    @Override
    public void onWebSocketText(String message){
        super.onWebSocketText(message);
        System.out.println("Received TEXT message: " + message);

        switch (message) {
        case "start":
            send("STARTED");

            example.run(threads, session);

            break;
        case "stop":
        	 send("STOPPED!");
        	 try {
				session.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
            break;
        }
    }


    private void send(String message) {
        try {
            if (session.isOpen()) {
                session.getRemote().sendString(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public void onWebSocketClose(int statusCode, String reason){
        super.onWebSocketClose(statusCode,reason);
        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
    }
    
    @Override
    public void onWebSocketError(Throwable cause){
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }
}