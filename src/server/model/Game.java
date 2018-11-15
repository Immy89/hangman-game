package server.model;

import server.helpers.Wordlist;

/**
 * Contains the hangman game logic.
 *
 * Created by Kim Säther on 09-Nov-18.
 */
public class Game {
    private final Wordlist wordlist;
    private String word = "";
    private String guessedLetters = "";
    private int remainingFailedGuesses;
    private int score = 0;
    private GameState state = GameState.NOTSTARTED;

    public Game(Wordlist wordlist) {
        this.wordlist = wordlist;
    }

    /**
     * @return The word of the current game.
     */
    public String getWord() {
        return word;
    }

    /**
     * @return A string with all the guessed letter for the current game.
     */
    public String getGuessedLetters() {
        return guessedLetters;
    }

    /**
     * @return The remaining guesses the user can fail before losing the current game.
     */
    public int getRemainingFailedGuesses() {
        return remainingFailedGuesses;
    }

    /**
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return The current game state.
     */
    public GameState getState() {
        return state;
    }

    /**
     * Starts a new game after the last game was finished.
     */
    public void startNewGame() {
        if (state != GameState.RUNNING) {
            word = wordlist.getRandomWord().toLowerCase();
            remainingFailedGuesses = word.length();
            guessedLetters = "";
            state = GameState.RUNNING;
        }
    }

    /**
     * Make a guess for a letter.
     * @param letter The letter to make a guess for.
     */
    public void guessLetter(char letter) {
        if (state != GameState.RUNNING) {
            return;
        }
        String guessedLetter = Character.toString(letter).toLowerCase();
        guessedLetters += guessedLetter;

        if (!word.contains(guessedLetter)) {
            remainingFailedGuesses--;
            checkIfGameFailed();
        } else {
            checkIfGameWasWon();
        }
    }

    /**
     * Make a guess for a word.
     * @param word The word to make a guess for.
     */
    public void guessWord(String word) {
        if (state != GameState.RUNNING) {
            return;
        }
        if (word.toLowerCase().equals(this.word)) {
            score++;
            state = GameState.WON;
        } else {
            remainingFailedGuesses--;
            checkIfGameFailed();
        }
    }
    /**
     * @return The word with only correctly guessed letter.
     */
    public String getMaskedWord() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            String c = Character.toString(word.charAt(i));
            if (guessedLetters.contains(c)) {
                if (i < word.length() - 1) {
                    sb.append(c + " ");
                } else if (i == word.length() - 1) {
                    sb.append(c);
                }
            }
            else {
                if (i < word.length() - 1) {
                    sb.append("_ ");
                } else if (i == word.length() - 1) {
                    sb.append("_");
                }
            }
        }

        return sb.toString();
    }

    private void checkIfGameFailed() {
        if (remainingFailedGuesses == 0) {
            score--;
            state = GameState.LOST;
        }
    }

    private void checkIfGameWasWon() {
        StringBuilder wordFromGuessedLetters = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (guessedLetters.contains(Character.toString(word.charAt(i)))) {
                wordFromGuessedLetters.append(word.charAt(i));
            } else {
                return;
            }
        }
        if (word.equals(wordFromGuessedLetters.toString())) {
            score++;
            state = GameState.WON;
        }
    }
}
