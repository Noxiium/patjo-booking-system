package controller;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocketEndpoint class represents the WebSocket server endpoint. Clients can
 * connect to this endpoint to establish WebSocket communication.
 */
@ServerEndpoint("/endpoint")
public class WebSocketEndpoint {

    // Set to store all active WebSocket sessions
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * Sends a message to all connected WebSocket clients.
     * Iterates through all active sessions and sends the message to each client.
     *
     * @param message The message to be sent
     */
    public static void sendMessageToAll(String message) {
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
