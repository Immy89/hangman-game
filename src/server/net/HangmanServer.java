package server.net;

import server.controller.Controller;
import server.model.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kim Säther on 06-Nov-18.
 */
public class HangmanServer {
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private final List<ClientHandler> clients = new ArrayList<>();
    private int portNo = 1989;
    private final Controller ctrl = new Controller();

    public static void main(String[] args){
        HangmanServer server = new HangmanServer();
        server.serve();
    }

    private void serve() {
        try {
            ServerSocket listeningSocket = new ServerSocket(portNo);
            while(true){
                Socket clientSocket = listeningSocket.accept();
                startHandler(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Server failure.");
        }
    }

    private void startHandler(Socket clientSocket) throws SocketException {
        clientSocket.setSoLinger(true, LINGER_TIME);
        clientSocket.setSoTimeout(TIMEOUT_HALF_HOUR);
        ClientHandler handler = new ClientHandler(this, clientSocket);
        synchronized (clients) {
            clients.add(handler);
        }
        Thread handlerThread = new Thread(handler);
        handlerThread.setPriority(Thread.MAX_PRIORITY);
        handlerThread.start();
    }

    public void removeHandler(ClientHandler handler) {
        synchronized (clients) {
            clients.remove(handler);
        }
    }
}
