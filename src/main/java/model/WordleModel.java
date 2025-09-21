package model;

import java.util.Objects;

public class WordleModel {

    private int guessCount;
    private String secretWord;

    private final Guess[] guessesMade = new Guess[6];

    public WordleModel() throws Exception {
        this.guessCount = 0;
        //generate word
        this.secretWord = RandomWordFetcher.fetchRandomWord();

    }

    public void makeGuess(String word){
        Guess userGuess = new Guess(word, this.secretWord);
        this.guessesMade[guessCount] = userGuess;
        guessCount++;
    }

    public Boolean isWon(){
        return (this.guessesMade[this.guessCount - 1].getGuess().equals(secretWord));
    }
    public Boolean isLost(){
        return (this.guessCount==6 && this.secretWord.equals(this.guessesMade[guessCount-1].getGuess()));
    }

    public String getSecretWord(){
        return this.secretWord;
    }

    public Guess getLastGuess(){
        return this.guessesMade[guessCount - 1];
    }
}
