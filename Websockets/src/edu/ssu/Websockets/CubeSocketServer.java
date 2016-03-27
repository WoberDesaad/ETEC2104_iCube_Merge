/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ssu.Websockets;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.java_websocket.WebSocket;
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
        super();
        this.clients = new ArrayList<>();
    }
    
    @Override
    public void onOpen(WebSocket ws, ClientHandshake ch) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onClose(WebSocket ws, int i, String string, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onMessage(WebSocket ws, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onError(WebSocket ws, Exception excptn) {
        System.out.println("Exception with " + ws.toString() + ".\n" + excptn.getMessage());
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
