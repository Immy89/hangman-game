package server.controller;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import server.helpers.ArrayWordList;
import server.model.Game;
import server.model.GameState;
import shared.Constants;
import shared.MsgType;

/**
 * Created by Kim Säther on 09-Nov-18.
 */
public class GameController {

    private final Game game = new Game(new ArrayWordList());

    public String startGame() {
        if (game.getState() == GameState.RUNNING){
            return Text("Du kan inte starta ett nytt spel innan du har spelat färdigt det du håller på med.");
        } else {
            game.startNewGame();
            return returnGameVariables();
        }
    }

    public String guessLetter(char letter) {
        game.guessLetter(letter);
        return checkGameState();
    }

    public String guessWord(String word) {
        game.guessWord(word);
        return checkGameState();
    }

    private String checkGameState() {
        if (game.getState() == GameState.WON) {
            return Text("Grattis du vann denna ronden.##Spela gärna en till rond med kommandot 'spela'.");
        } else if (game.getState() == GameState.LOST) {
            return Text("Denna ronden är tyvärr slut nu.##Det rätta ordet var '" + game.getWord() + "'.##Försök gärna igen med kommandot 'spela'.");
        } else {
            return returnGameVariables();
        }
    }

    private String Text(String message) {
        return MsgType.TEXT.toString() + Constants.ANSWER_DELIMETER + message;
    }

    private String returnGameVariables() {
        StringBuilder returnGameVariables = new StringBuilder();

        returnGameVariables.append(MsgType.GAMESTATE.toString());
        returnGameVariables.append(Constants.ANSWER_DELIMETER);

        returnGameVariables.append(game.getGuessedLetters());
        returnGameVariables.append(Constants.ANSWER_DELIMETER);

        returnGameVariables.append(game.getMaskedWord());
        returnGameVariables.append(Constants.ANSWER_DELIMETER);

        returnGameVariables.append(game.getRemainingFailedGuesses());
        returnGameVariables.append(Constants.ANSWER_DELIMETER);

        returnGameVariables.append(game.getScore());
        return returnGameVariables.toString();
    }
}
