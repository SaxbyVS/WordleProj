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

    public WordleController(WordleModel model) {
        this.model = model;
    }

    public void onGuess(String word){ //when guess is made, inc guess count and pass word to model
        if(word.length()==5) {
            model.makeGuess(word);
        }
    }

    public void onKeyPress(String key){
        if(key.equals("ENTER")) {
            if (buffer.length()==5){
                onGuess(buffer);
            }
        }else if (key.equals("BACKSPACE")) {
            buffer = buffer.substring(0, buffer.length()-1);
        }else if (key.length()==1 && Character.isLetter(key.charAt(0))){
            buffer = buffer + key;
        }
    }

    public void refreshGame() throws Exception { // reset game state for continuous play
        if (model.isWon()) {
            model.incrementGameScore();
        }

        model.resetGame();
    }


    //GETTERS; win/loss checkers | (for ui to handle refreshing/game flow)
    public WordleModel getModel() {
        return model;
    }
    public Boolean isWon(){
        return model.isWon();
    }
    public Boolean isLost(){
        return model.isLost();
    }

    public void saveGame() { //save current game state
    }

    public void loadGame(){ //load last saved game if it exists

    }
}
