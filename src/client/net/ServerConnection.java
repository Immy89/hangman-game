package client.net;

import client.view.CommandLineView;
import client.view.View;
import shared.Constants;
import shared.MessageException;
import shared.MsgType;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Kim Säther on 10-Nov-18.
 */
public class ServerConnection implements Runnable{
    private static final int TIMEOUT_HALF_MINUTE = 30000;
    private final Socket serverSocket;
    private final View view;
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private boolean connected = false;

    public ServerConnection(View view) {
        this.view = view;
        serverSocket = new Socket();
    }

    public static void main (String[] args) {
        View view = new CommandLineView();
        ServerConnection connection = new ServerConnection(view);
        CommandInterpreter cmd = new CommandInterpreter(connection);
        cmd.run();
        connection.disconnect();
    }

    @Override
    public void run() {
        try {
            boolean autoFlush = true;
            fromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            toServer = new PrintWriter(serverSocket.getOutputStream(), autoFlush);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }

        while (connected) {
            try {
                String messageString = fromServer.readLine();

                if (messageString != null) {
                    HandleMessage(messageString);
                } else {
                    disconnect();
                }
            } catch (IOException ioe) {
                disconnect();
            } catch (MessageException me) {
                toServer.println(me.getMessage());
            }
        }
    }

    private void HandleMessage(String messageString) {
        String[] msgParts = messageString.split(Constants.ANSWER_DELIMETER);
        switch (msgParts[0]) {
            case "TEXT":
                for (int i = 1; i < msgParts.length; i++)
                    view.showText(msgParts[i]);
                break;

            case "GAMESTATE":
                view.showGameState(msgParts[1], msgParts[2], msgParts[3], msgParts[4]);
                break;

            default:
                throw new MessageException("Received corrupt message: " +
                        messageString);
        }
    }

    public String connect(String ip, int port) throws
            IOException {
        serverSocket.connect(new InetSocketAddress(ip, port), TIMEOUT_HALF_MINUTE);
        Thread thread = new Thread(this);
        thread.start();
        connected = true;
        return "Du är nu ansluten till " + ip + ":" + port;
    }

    public void sendStartGame() {
        toServer.println(MsgType.STARTGAME.toString());
    }

    public void sendGuess(String s) {
        toServer.println(MsgType.GAMEENTRY.toString() + Constants.MSG_DELIMETER + s);
    }

    public void disconnect() {
        try {
            serverSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        connected = false;
    }
}
