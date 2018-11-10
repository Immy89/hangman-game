package server.controller;

import server.helpers.ArrayWordList;
import server.model.Game;
import server.model.GameState;
import shared.Constants;

/**
 * Created by Kim Säther on 09-Nov-18.
 */
public class Controller {

    private final Game game = new Game(new ArrayWordList());

    public String returnGameVariables() {
        StringBuilder returnGameVariables = new StringBuilder();
        returnGameVariables.append(game.getGuessedLetters());
        returnGameVariables.append(Constants.ANSWER_DELIMETER);
        returnGameVariables.append(game.getRemainingFailedGuesses());
        returnGameVariables.append(Constants.ANSWER_DELIMETER);
        returnGameVariables.append(game.getScore());
        returnGameVariables.append(Constants.ANSWER_DELIMETER);
        returnGameVariables.append(game.getMaskedWord());
        return returnGameVariables.toString();
    }

    public String startGame() {
        if (game.getState() == GameState.RUNNING){
            return "Du kan inte starta ett nytt spel innan du har avslutat det du håller på med.";
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
            return "Grattis du vann denna ronden.\r\nSpela gärna en till rond med kommandot 'börja'.";
        } else if (game.getState() == GameState.LOST) {
            return "Denna ronden är tyvärr slut nu.\r\nDet rätta ordet var '" + game.getWord() + "'.\r\nFörsök gärna igen med kommandot 'börja'.";
        } else {
            return returnGameVariables();
        }
    }
}
