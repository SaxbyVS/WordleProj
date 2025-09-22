package model;

/*
WordleModel
     - contains gameScore (overall), guessCount,
     the secret string, and a list of Guesses made
     - can check and change all game states, including
     resetting the game
     - functions for individual wordle games, as controller
     handles saves and continuity

 */

public class WordleModel {
    private int gameScore;
    private int guessCount;
    private String secretWord;
    private Guess[] guessesMade = new Guess[6];


    public WordleModel() throws Exception { //main constructor
        this.guessCount = 0;
        //generate word
        this.secretWord = RandomWordFetcher.fetchRandomWord().toLowerCase();

    }

    public WordleModel(String secretWord){ //used for debugging purposes
        this.guessCount = 0;
        this.secretWord = secretWord;
    }

    public void makeGuess(String word){ //store new guess object and inc guessCount
        if (this.guessCount < 6) {
            Guess userGuess = new Guess(word.toLowerCase(), this.secretWord);
            this.guessesMade[guessCount] = userGuess;
            guessCount++;
        }
    }

    public void resetGame() throws Exception { //for ui to enable continuous play
        this.guessCount = 0;
        this.secretWord = RandomWordFetcher.fetchRandomWord();
        this.guessesMade = null;
    }


    //GETTERS/SETTERS | WIN/LOSS CHECKERS
    public Boolean isWon(){
        if (this.guessCount==0){
            return false;
        }else {
            return (this.guessesMade[this.guessCount - 1].getGuess().equals(secretWord) && this.guessCount <= 6);
        }
    }
    public Boolean isLost(){
        if (this.guessCount==0){
            return false;
        }else {
            return (this.guessCount == 6 && !(this.secretWord.equals(this.guessesMade[guessCount - 1].getGuess())));
        }
    }
    public String getSecretWord(){
        return this.secretWord;
    }
    public Guess getLastGuess(){
        if (this.guessCount > 0) {
            return this.guessesMade[guessCount - 1];
        }else{
            return null;
        }
    }
    public void setGameScore(int score){
        this.gameScore = score;
    }
    public void incrementGameScore(){
        this.gameScore++;
    }
    public int getGuessCount(){
        return this.guessCount;
    }
}
