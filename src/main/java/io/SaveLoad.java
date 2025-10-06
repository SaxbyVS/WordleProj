package io;
import com.google.gson.Gson;
import model.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
SaveLoad
 - provides static functions to save/load the current WordleModel to/from a JSON file

 */


public  class SaveLoad {
    public static void saveState(WordleModel model){ //save current state of game to JSON
        Gson gson = new Gson();
        List<String> guesses = new ArrayList<>(); //convert guessesMade into list of strings
        SaveState state;
        //create save state
        if (model.getGuessCount()>0) {
            for (int i = 0; i< model.getGuessCount(); i++){
                guesses.add(model.getGuesses()[i].getGuess());
            }
        }
        state = new SaveState(model.getGameScore(), model.getSecretWord(), guesses); //create save state

        try (FileWriter fw = new FileWriter("wordle_save.json")) {
                gson.toJson(state, fw);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public static void loadState(WordleModel model){ //load last saved state from JSON
        Gson gson = new Gson();
        File file = new File("wordle_save.json");
        if (file.exists()) {
            try (FileReader fr = new FileReader(file)) {
                SaveState state = gson.fromJson(fr, SaveState.class);

                model.setSecretWord(state.secretWord);
                model.setGameScore(state.gameScore);
                if (state.guessesMade!=null) {
                    for (String s : state.guessesMade) { //add guesses back to game; reinstates guessCount
                        model.makeGuess(s);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
