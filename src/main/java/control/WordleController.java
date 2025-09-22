package control;

import model.*;

public class WordleController {
    private final WordleModel model;

    public WordleController(WordleModel model) {
        this.model = model;
    }

    public void onGuess(String word){ //when guess is made, inc guess count and pass word to model
        model.makeGuess(word);
    }

    public void refreshGame() throws Exception { // reset game state for continuous play
        if (model.isWon()) {
            model.incrementGameScore();
        }
        //save prev game info to json?
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
}
