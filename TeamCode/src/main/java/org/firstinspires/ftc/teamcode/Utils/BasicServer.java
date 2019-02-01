package org.firstinspires.ftc.teamcode.Utils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class BasicServer extends WebSocketServer {
    Logger l = new Logger("BASIC_SERVER");
    public BasicServer(int port ) {
        super( new InetSocketAddress( port ) );
    }
//
//    public BasicServer(InetSocketAddress address ) {
//        super( address );
//    }

//    public void addTelemetry(String variable, )

    public void broadcastMessage(JSONObject json){
        broadcastMessage(json.toString());
    }

    private void broadcastMessage(String text){
        broadcast(text);
        l.logData("Broadcasted", text);
    }
    public void broadcastData(String eventType, JSONObject jsonData) throws JSONException {
        JSONObject message = new JSONObject()
                .put("event",eventType)
                .put("ts",System.currentTimeMillis())
                .put("body", jsonData);
        broadcastMessage(message);
    }
    private String getIP(WebSocket conn){
        return conn.getRemoteSocketAddress().getAddress().getHostAddress();
    }
    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
//        conn.send("Welcome to the server!"); //This method sends a message to the new client
//        broadcast( "new connection: " + handshake.getResourceDescriptor() ); //This method sends a message to all clients connected
        l.logData( "New connection", getIP(conn));
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
//        broadcast( conn + " has left the room!" );
        System.out.println( conn + " has left the room!" );
        l.logData( "Disconnected",getIP(conn));

    }

    @Override
    public void onMessage( WebSocket conn, String message ) {
        if(message.toLowerCase().equals("ping")){
            broadcastMessage("pong");
        }
        l.logData( "recieved",getIP(conn) + ": " + message );
    }
    @Override
    public void onMessage( WebSocket conn, ByteBuffer message ) {
//        broadcast( message.array() );
        l.logData( "recieved",getIP(conn) +  ": " + message );
    }

    @Override
    public void onError( WebSocket conn, Exception ex ) {
        ex.printStackTrace();
        if( conn != null ) {
            l.logData("Error",getIP(conn) + ": " + ex.toString());
            // some errors like port binding failed may not be assignable to a specific websocket
        }
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }

}