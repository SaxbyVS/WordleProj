package model;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    //CTORS
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
        for (int i = 0; i < 6; i++) {
            this.guessesMade[i] = null;
        }
    }


    //SAVE/LOAD
    public void saveGame(){ //save current state of game to JSON
        if (guessCount>0) {
            Gson gson = new Gson();
            List<String> guesses = new ArrayList<>(); //convert guessesMade into list of strings
            for (int i=0; i<guessCount; i++){
                guesses.add(guessesMade[i].getGuess());
            }

            SaveState state = new SaveState(gameScore, secretWord, guesses); //create save state

            try (FileWriter fw = new FileWriter("wordle_save.json")) {
                gson.toJson(state, fw);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            //save score and secretWord but empty Guess list
            Gson gson = new Gson();
            List<String> guesses = new ArrayList<>();

            SaveState state = new SaveState(gameScore, secretWord, guesses); //create save state

            try (FileWriter fw = new FileWriter("wordle_save.json")) {
                gson.toJson(state, fw);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void loadGame(){ //load last saved state from JSON
        Gson gson = new Gson();
        File file = new File("wordle_save.json");
        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                SaveState state = gson.fromJson(fr, SaveState.class);

                this.secretWord = state.secretWord;
                this.gameScore = state.gameScore;
                if (state.guessesMade!=null) {
                    for (String s : state.guessesMade) { //add guesses back to game; reinstates guessCount
                        makeGuess(s);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
    public Guess[] getGuesses(){
        return this.guessesMade;
    }
    public int getGameScore(){
        return this.gameScore;
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
