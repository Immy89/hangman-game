package client.view;

/**
 * Created by Kim Säther on 10-Nov-18.
 */
public interface View {
    void showGameState(String guessedLetter, String word, String remainingFailedGuesses, String score);
    void showText(String message);
}
