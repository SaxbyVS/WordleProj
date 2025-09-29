package io;

import java.util.List;

/*
SaveState
    - creates writable object to be saved in JSON
    - only needs to store strings of guesses as they can be added back manually

 */


public class SaveState {
    int gameScore;
    String secretWord;
    List<String> guessesMade;

    public SaveState(int gameScore, String secretWord, List<String> guessesMade) {
        this.gameScore = gameScore;
        this.secretWord = secretWord;
        this.guessesMade = guessesMade;
    }
}
