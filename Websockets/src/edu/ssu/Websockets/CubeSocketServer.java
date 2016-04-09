package edu.ssu.Websockets;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class CubeSocketServer extends WebSocketServer {
    WebSocket openGLServer;
    ArrayList<WebSocket> clients;
    
    public CubeSocketServer(InetSocketAddress address) {
        super(address);
        clients = new ArrayList<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
        if(openGLServer == null)
        {
            this.openGLServer = conn;
        }
        else
        {
            if(this.openGLServer != conn)
            {
                if(this.clients.size() < 4)
                {
                    if(!this.clients.contains(conn))
                    {
                        this.clients.add(conn);                
                    }
                    else
                    {
                        conn.send("Connection already Established!");
                    }
                }
                else
                {
                    conn.closeConnection(0, "Already too many clients connected!");
                }
            }
            else
            {
                conn.send("Connection already Established!");
            }
            
        }
        System.out.println(this.clients.size());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
        if(this.clients.contains(conn))
        {
            this.clients.remove(conn);
        }
        else if(this.openGLServer == conn)
        {
            this.openGLServer = null;
            for(WebSocket client : this.clients)
            {
                client.send("Server is down...");
            }
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        if(conn == this.openGLServer)
        {
            for(WebSocket client : this.clients)
            {
                client.send("From Sever to clients: " + message);
            }
        }
        else if(this.clients.contains(conn))
        {
            this.openGLServer.send("From Clients to server: " + message);
        }
    }
    
    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        if(conn == this.openGLServer)
        {
            for(WebSocket client : this.clients)
            {
                client.send(message);
            }
        }
        else if(this.clients.contains(conn))
        {
            this.openGLServer.send(message);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occured on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 8887;

        WebSocketServer server = new CubeSocketServer(new InetSocketAddress(host, port));
        server.run();
    }
}