package client.net;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Kim Säther on 10-Nov-18.
 */
public class CommandInterpreter {
    private final ServerConnection connection;

    public CommandInterpreter(ServerConnection connection) {

        this.connection = connection;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\r\\n");
        for (;;) {
            String input = scanner.next();
            String[] commandParts = input.split(" ");
            String command = commandParts[0];
            switch (command.toUpperCase()) {
                case "AVSLUTA":
                    return;
                case "ANSLUT":
                    try {
                        String ip = commandParts[1];
                        int port = Integer.parseInt(commandParts[2]);
                        String connectionMessage = connection.connect(ip, port);
                        System.out.println(connectionMessage);
                    } catch (IOException ioe) {
                        System.out.println("Det gick inte att ansluta sig till servern.\r\nKontrollera att du har matat in rätt ipadress och portnummer och försök igen.");
                    }
                    break;
                case "SPELA":
                    connection.sendStartGame();
                    break;
                case "GISSA":
                    connection.sendGuess(commandParts[1]);
                    break;
                default:
                    System.out.println(command + " är inget giltigt kommando!\r\nFörsök igen med något av följande:\r\nanslut\r\nspela\r\ngissa\r\navsluta");
            }
        }
    }
}
