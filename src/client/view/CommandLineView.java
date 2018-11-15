package client.view;

/**
 * Created by Kim Säther on 10-Nov-18.
 */
public class CommandLineView implements View {
    @Override
    public void showGameState(String guessedLetter, String word, String remainingFailedGuesses, String score) {

        String headerGissadeBokstäver = "Gissade bokstäver";
        String headerOrdet = "Ordet";
        String headerResterandeGissningar = "Resterande gissningar";
        String headerPoäng = "Poäng";

        StringBuilder header = new StringBuilder();
        header.append("| ");
        header.append(headerGissadeBokstäver);
        header.append(" | ");
        if (word.length() > headerOrdet.length()) {
            int diffWord1 = word.length() - headerOrdet.length();
            StringBuilder subheaderword = new StringBuilder();
            for (int i = 0; i < diffWord1/2; i++) {
                subheaderword.append(" ");
            }
            subheaderword.append(headerOrdet);
            for (int i = 0; i < diffWord1/2; i++) {
                subheaderword.append(" ");
            }
            if (subheaderword.length() < word.length()) {
                subheaderword.append(" ");
            } else if (subheaderword.length() > word.length()) {
                subheaderword.deleteCharAt(subheaderword.length() - 1);
            }
            header.append(subheaderword);
        } else {
            header.append(headerOrdet);
        }
        header.append(" | ");
        header.append(headerResterandeGissningar);
        header.append(" | ");
        header.append(headerPoäng);
        header.append(" |");

        StringBuilder response = new StringBuilder();
        response.append("| ");
        if (guessedLetter.length() < headerGissadeBokstäver.length()) {
            StringBuilder subresponseGissadeBokstäver = new StringBuilder();
            int diffGissadeBokstäver = headerGissadeBokstäver.length() - guessedLetter.length();
            for (int i = 0; i < diffGissadeBokstäver / 2; i++) {
                subresponseGissadeBokstäver.append(" ");
            }
            subresponseGissadeBokstäver.append(guessedLetter);
            for (int i = 0; i < diffGissadeBokstäver / 2; i++) {
                subresponseGissadeBokstäver.append(" ");
            }
            if (subresponseGissadeBokstäver.length() < headerGissadeBokstäver.length()) {
                subresponseGissadeBokstäver.append(" ");
            } else if (subresponseGissadeBokstäver.length() > headerGissadeBokstäver.length()) {
                subresponseGissadeBokstäver.deleteCharAt(subresponseGissadeBokstäver.length() - 1);
            }
            response.append(subresponseGissadeBokstäver);
        } else {
            response.append(guessedLetter);
        }
        response.append(" | ");
        if (headerOrdet.length() > word.length()) {
            StringBuilder subresponseWord = new StringBuilder();
            int diffWord2 = headerOrdet.length() - word.length();
            for (int i = 0; i < diffWord2/2; i++) {
                subresponseWord.append(" ");
            }
            subresponseWord.append(word);
            for (int i = 0; i < diffWord2/2; i++) {
                subresponseWord.append(" ");
            }
            if (subresponseWord.length() < headerOrdet.length()) {
                subresponseWord.append(" ");
            } else if (subresponseWord.length() > headerOrdet.length()) {
                subresponseWord.deleteCharAt(subresponseWord.length() - 1);
            }
            response.append(subresponseWord);
        } else {
            response.append(word);
        }
        response.append(" | ");
        if (headerResterandeGissningar.length() > remainingFailedGuesses.length()) {
            int diffRemainingGuesses = headerResterandeGissningar.length() - remainingFailedGuesses.length();
            StringBuilder subresponseRemainingGuesses = new StringBuilder();
            for (int i = 0; i < diffRemainingGuesses/2; i++) {
                subresponseRemainingGuesses.append(" ");
            }
            subresponseRemainingGuesses.append(remainingFailedGuesses);
            for (int i = 0; i < diffRemainingGuesses/2; i++) {
                subresponseRemainingGuesses.append(" ");
            }
            if (subresponseRemainingGuesses.length() < headerResterandeGissningar.length()) {
                subresponseRemainingGuesses.append(" ");
            } else if (subresponseRemainingGuesses.length() > headerResterandeGissningar.length()) {
                subresponseRemainingGuesses.deleteCharAt(subresponseRemainingGuesses.length() - 1);
            }
            response.append(subresponseRemainingGuesses);
        } else {
            response.append(remainingFailedGuesses);
        }
        response.append(" | ");
        if (score.length() < headerPoäng.length()) {
            int diffscore = headerPoäng.length() - score.length();
            StringBuilder subresponsePoäng = new StringBuilder();
            for (int i = 0; i < diffscore/2; i++) {
                subresponsePoäng.append(" ");
            }
            subresponsePoäng.append(score);
            for (int i = 0; i < diffscore/2; i++) {
                subresponsePoäng.append(" ");
            }
            if (subresponsePoäng.length() < headerPoäng.length()) {
                subresponsePoäng.append(" ");
            } else if (subresponsePoäng.length() > headerPoäng.length()) {
                subresponsePoäng.deleteCharAt(subresponsePoäng.length() - 1);
            }
            response.append(subresponsePoäng);
        } else {
            response.append(score);
        }
        response.append(" |");

        StringBuilder frame = new StringBuilder();

        for (int i = 0; i < response.length(); i++) {
            frame.append("-");
        }

        StringBuilder view = new StringBuilder();
        view.append(frame);
        view.append("\r\n");
        view.append(header);
        view.append("\r\n");
        view.append(frame);
        view.append("\r\n");
        view.append(response);
        view.append("\r\n");
        view.append(frame);
        view.append("\r\n");

        System.out.print(view);
    }

    @Override
    public void showText(String message) {
        System.out.println(message);
    }
}
