package edu.ssu.Websockets;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.*;

public class CubeClient extends WebSocketClient {

    public CubeClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
        System.out.println(draft.toString());
    }

    public CubeClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("new connection opened");
        this.send("Hello");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

    public static void main(String[] args) throws URISyntaxException { 
        WebSocketClient client = new CubeClient(new URI("ws://localhost:8887"), new Draft_10());
        client.connect();
        
        client.send("I am the server!");
    }
}