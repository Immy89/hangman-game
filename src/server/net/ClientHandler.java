package server.net;

import server.controller.GameController;
import shared.Message;
import shared.MessageException;

import java.io.*;
import java.net.Socket;

/**
 * Created by Kim Säther on 06-Nov-18.
 */
public class ClientHandler implements Runnable {

    private final HangmanServer server;
    private final Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private boolean connected;
    private GameController ctrl;

    public ClientHandler(HangmanServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        connected = true;
    }

    @Override
    public void run()  {
        try {
            boolean autoFlush = true;
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream(), autoFlush);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }

        ctrl = new GameController();
        while (connected) {
            try {
                String msgString = fromClient.readLine();

                if (msgString != null) {
                    HandleMessage(msgString);
                } else {
                    disconnectClient();
                }
            } catch (IOException ioe) {
                disconnectClient();
            } catch (MessageException me) {
                toClient.println(me.getMessage());
            }
        }
    }

    private void HandleMessage(String msgString) {
        Message msg = new Message(msgString);
        String answer;
        switch (msg.getMsgType()) {
            case STARTGAME:
                answer = ctrl.startGame();
                toClient.println(answer);
                break;
            case GAMEENTRY:
                if (msg.getMsgBody().length() == 1) {
                    answer = ctrl.guessLetter(msg.getMsgBody().charAt(0));
                } else if (msg.getMsgBody().length() > 1) {
                    answer = ctrl.guessWord(msg.getMsgBody());
                } else {
                    answer = "Du måste gissa på någon bokstav eller ett helt ord när du anger kommandot 'Gissa'.";
                }
                toClient.println(answer);
                break;
            case DISCONNECT:
                disconnectClient();
                break;
            default:
                throw new MessageException("Received corrupt message: " +
                        msg.getReceivedString());
        }
    }

    private void disconnectClient() {
        try {
            clientSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        connected = false;
        server.removeHandler(this);
    }
}
