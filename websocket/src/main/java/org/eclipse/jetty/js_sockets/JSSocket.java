package org.eclipse.jetty.js_sockets;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

public class JSSocket extends WebSocketAdapter {
    
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	private Session sessy;
	
    @Override
    public void onWebSocketConnect(Session sess)
    {
    	this.sessy = sess;
        super.onWebSocketConnect(sess);
        System.out.println("Socket Connected: " + sess);
    }
    
    @Override
    public void onWebSocketText(String message)
    {
        super.onWebSocketText(message);
        System.out.println("Received TEXT message: " + message);

        switch (message) {
        case "start":
            send("STARTED");
            executor.scheduleAtFixedRate(() -> send(NumService.getNums()), 30, 5, TimeUnit.MILLISECONDS);
            break;
        case "stop":
        	 send("STOPPED!");
        	 try {
				sessy.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
            break;
        }
    }
    
    
    private void send(String message) {
        try {
            if (sessy.isOpen()) {
                sessy.getRemote().sendString(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        super.onWebSocketClose(statusCode,reason);
        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
    }
    
    @Override
    public void onWebSocketError(Throwable cause)
    {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }
}