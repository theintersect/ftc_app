package org.firstinspires.ftc.teamcode.Utils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;
import org.firstinspires.ftc.teamcode.Models.TelemetryObject
/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class BasicServer extends WebSocketServer {
    Logger l = new Logger("BASIC_SERVER");
    public BasicServer(int port ) {
        super( new InetSocketAddress( port ) );
    }


    public void broadcastMessage(JSONObject json){
        broadcastMessage(json.toString());
    }

    private void broadcastMessage(String text){
        broadcast(text);
//        l.logData("Broadcasted", text);
    }
    public void broadcastData(String eventType, JSONObject jsonData) throws JSONException {
        JSONObject message = new JSONObject()
                .put("event",eventType)
                .put("ts",System.currentTimeMillis())
                .put("body", jsonData);
        broadcastMessage(message);
        l.log("Broadcasted data: \n" + message.toString(4))
    }

    public void broadcastTelemetry(telemetryObject:TelemetryObject) throws JSONException{
        broadcastData("telemetry", telemetryObject);
    }

    private String getIP(WebSocket conn){
        return conn.getRemoteSocketAddress().getAddress().getHostAddress();
    }

    @Override
    public void onOpen( WebSocket conn, ClientHandshake handshake ) {
        l.logData( "New connection", getIP(conn));
    }

    @Override
    public void onClose( WebSocket conn, int code, String reason, boolean remote ) {
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
        l.logData( "recieved",getIP(conn) +  ": " + message );
    }

    @Override
    public void onError( WebSocket conn, Exception ex ) {
        ex.printStackTrace();
        if( conn != null ) {
            l.logData("Error",getIP(conn) + ": " + ex.toString());
        }
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
    }



}