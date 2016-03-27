package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class BallServer extends WebSocketServer {

    int ball_x = 50;
    int ball_y = 50;
    
    int max_x = 750;
    int max_y = 750;
    int min_x = 0;
    int min_y = 0;
    
    int ball_speed = 10;
            
    
    public BallServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public BallServer(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putInt(0, ball_x);
        bb.putInt(4, ball_y);
        this.sendToAll(bb);
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " has left the room!");
    }
    
    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        int command = message.getInt(0);
        
        if(command == 0)
        {
            this.ball_y -= ball_speed;
            if(this.ball_y < min_y)
            {
                this.ball_y = min_y;
            }
        }else if(command == 1)
        {
            this.ball_y += ball_speed;
            if(this.ball_y > max_y)
            {
                this.ball_y = max_y;
            }
        }else if(command == 2)
        {
            this.ball_x -= ball_speed;
            if(this.ball_x < this.min_x)
            {
                this.ball_x = this.min_x;
            }
        }else if(command == 3)
        {
            this.ball_x += ball_speed;
            if(this.ball_x > this.max_x)
            {
                this.ball_x = this.max_x;
            }
        }else
        {
            System.out.println("unknown command");
        }
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putInt(0, ball_x);
        bb.putInt(4, ball_y);
        System.out.println("X: " + bb.asIntBuffer().get(0) + "\nY: " + bb.asIntBuffer().get(1));
        this.sendToAll(bb);
        
    }
    
    @Override
    public void onFragment(WebSocket conn, Framedata fragment) {
        System.out.println("received fragment: " + fragment);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        WebSocketImpl.DEBUG = true;
        int port = 8887; // 843 flash policy port
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception ex) {
        }
        BallServer s = new BallServer(port);
        s.start();
        System.out.println("ChatServer started on port: " + s.getPort());

        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String in = sysin.readLine();
            if (in.equals("exit")) {
                s.stop();
                break;
            } else if (in.equals("restart")) {
                s.stop();
                s.start();
                break;
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    /**
     * Sends <var>text</var> to all currently connected WebSocket clients.
     *
     * @param text The String to send across the network.
     * @throws InterruptedException When socket related I/O errors occur.
     */
    public void sendToAll(ByteBuffer command) {
        System.out.println(command);
        Collection<WebSocket> con = connections();
        synchronized (con) {
            for (WebSocket c : con) {
                c.send(command);
            }
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        return;
    }
    
}
