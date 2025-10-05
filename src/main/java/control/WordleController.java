package control;

import model.*;


/*
WordleController
    - controller which interacts with UI
    - can pass guesses to model, save/load/refresh game
    - processes keys pressed in buffer

 */

public class WordleController {
    private final WordleModel model;
    private String buffer = "";
    private Boolean guessState = false;

    //CTOR
    public WordleController(WordleModel model) {
        if (model==null){
            throw new IllegalStateException("Model not initialized");
        }
        this.model = model;
    }


    public void onGuess(String word){ //when guess is made, inc guess count and pass word to model
        if(word.length()==5) {
            model.makeGuess(word);
        }
    }

    public void onKeyPress(String key) throws Exception { //process user's input buffer and make guesses
        if (buffer.length()<=5) {
            if (key.equals("ENTER")&& buffer.length()==5 && GuessValidator.isValid(buffer.toLowerCase())) {
                onGuess(buffer);
                guessState = true;
                buffer = "";
            } else if (key.equals("BACKSPACE") && !buffer.isEmpty()) {
                buffer = buffer.substring(0, buffer.length() - 1);
            } else if (key.length() == 1 && Character.isLetter(key.charAt(0)) && buffer.length()<5) {
                buffer = buffer + key.toUpperCase();
            }
        }
    }

    public void refreshGame() throws Exception { // reset game state for continuous play
        if (model.isWon()) {
            model.incrementGameScore();
        }

        model.resetGame();
    }

    //MODEL routers
    //GETTERS; win/loss checkers | (for ui to handle refreshing/game flow)
    public Boolean isWon(){
        return model.isWon();
    }
    public Boolean isLost(){
        return model.isLost();
    }
    public int getGameScore(){
        return model.getGameScore();
    }
    public int getGuessCount(){
        return model.getGuessCount();
    }
    public String getBuffer(){
        return buffer;
    }
    public Boolean getGuessState(){
        return guessState;
    }
    public void resetGuessState(){
        guessState = false;
    }

    public Guess[] getGuesses(){
        return model.getGuesses();
    }
    public Guess getLastGuess(){
        return model.getLastGuess();
    }
    public String getSecretWord(){
        return model.getSecretWord();
    }

    //SAVE/LOAD
    public void saveGame() { //save current game state
        model.saveGame();
    }
    public void loadGame(){ //load last saved game if it exists
        model.loadGame();
    }
}
