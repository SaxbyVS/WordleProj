package model;


public class WordleModel {
    private int gameScore;
    private int guessCount;
    private String secretWord;
    private Guess[] guessesMade = new Guess[6];


    public WordleModel() throws Exception {
        this.guessCount = 0;
        //generate word
        this.secretWord = RandomWordFetcher.fetchRandomWord();

    }

    public void makeGuess(String word){
        if (this.guessCount < 6) {
            Guess userGuess = new Guess(word, this.secretWord);
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
        return (this.guessesMade[this.guessCount - 1].getGuess().equals(secretWord) && this.guessCount<=6);
    }
    public Boolean isLost(){
        return (this.guessCount==6 && !(this.secretWord.equals(this.guessesMade[guessCount-1].getGuess())));
    }
    public String getSecretWord(){
        return this.secretWord;
    }
    public Guess getLastGuess(){
        return this.guessesMade[guessCount - 1];
    }
    public void incrementGameScore(){
        this.gameScore++;
    }
}
