/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ssu.Websockets;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import sun.rmi.runtime.Log;

/**
 *
 * @author Robert Appelmann
 */
public class CubeSocketServer extends WebSocketServer{

    // Fields
    private static final int MAX_CLIENTS = 4;
    
    // Connections
    private WebSocket gameServer;
    private ArrayList<WebSocket> clients;
    
    public CubeSocketServer() throws UnknownHostException{
        super(new InetSocketAddress(8080));
        this.clients = new ArrayList<>();
    }
    
    @Override
    public void onOpen(WebSocket ws, ClientHandshake ch) {
        // What to do on client connect
    }

    @Override
    public void onClose(WebSocket ws, int i, String string, boolean bln) {
        // what to do on client disconnect
    }

    @Override
    public void onMessage(WebSocket ws, String string) {
        //what to do on client message
    }
    
    @Override
    public void onMessage(WebSocket ws, ByteBuffer string) {
        //what to do on client message
    }

    @Override
    public void onError(WebSocket ws, Exception excptn) {
        // what to do on error
        System.out.println("Exception with " + ws.toString() + ".\n" + excptn.getMessage());
    }
    
    @Override
    public void onFragment(WebSocket conn, Framedata fragment)
    {
        // what to do on fragment receipt
    }
    
    public void sendToAll(String message)
    {
        Collection<WebSocket> con = connections();
        synchronized (con)
        {
            for(WebSocket c : con)
            {
                c.send(message);
            }
        }
    }
    
    public void sendToAll(ByteBuffer buffer)
    {
        Collection<WebSocket> con = connections();
        synchronized (con)
        {
            for(WebSocket c : con)
            {
                c.send(buffer);
            }
        }
    }
    
    public static void main(String[] args)
    {
        try {
            CubeSocketServer sever = new CubeSocketServer();
        } catch (UnknownHostException ex) {
            Logger.getLogger(CubeSocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
