package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import model.*;
import control.WordleController;

import static org.junit.jupiter.api.Assertions.*;

public class WordleTest {

    @Test
    void basicWin(){
        WordleModel model = new WordleModel("mouth");
        WordleController controller = new WordleController(model);
        controller.onGuess("mouth");
        assertEquals(true, controller.isWon());
    }

    @Test
    void basicNoWin(){
        WordleModel model = new WordleModel("sound");
        WordleController controller = new WordleController(model);
        controller.onGuess("snipe");
//        System.out.println(model.getLastGuess());
        assertEquals(false, controller.isWon());
    }

    @Test
    void basicNoLoss(){
        WordleModel model = new WordleModel("sound");
        WordleController controller = new WordleController(model);
        controller.onGuess("snipe");
        assertEquals(false, controller.isLost());
    }

    @Test
    void sixGuessLoss(){
        WordleModel model = new WordleModel("candy");
        WordleController controller = new WordleController(model);
        controller.onGuess("plane");
        controller.onGuess("pline");
        controller.onGuess("sandy");
        controller.onGuess("rendy");
        controller.onGuess("testy");
        controller.onGuess("tiffy");
        assertEquals(true, controller.isLost());
    }

    @Test
    void sixGuessWin(){
        WordleModel model = new WordleModel("candy");
        WordleController controller = new WordleController(model);
        controller.onGuess("plane");
        controller.onGuess("pline");
        controller.onGuess("sandy");
        controller.onGuess("rendy");
        controller.onGuess("testy");
        controller.onGuess("candy");
        assertEquals(true, controller.isWon());
    }

    @Test
    void noGuessMade(){
        WordleModel model = new WordleModel("sound");
        WordleController controller = new WordleController(model);
        assertNull(model.getLastGuess());
    }

    @Test
    void guessCounter(){
        WordleModel model = new WordleModel("sound");
        WordleController controller = new WordleController(model);
        controller.onGuess("snipe");
        controller.onGuess("pline");
        assertEquals(2, model.getGuessCount());
    }

    @Test
    void addTooManyWords(){
        WordleModel model = new WordleModel("sound");
        WordleController controller = new WordleController(model);
        controller.onGuess("snipe");
        controller.onGuess("pline");
        controller.onGuess("sandy");
        controller.onGuess("rendy");
        controller.onGuess("testy");
        controller.onGuess("candy");
        controller.onGuess("tiffy");
        assertEquals(6, controller.getGuessCount());
    }

    @Test
    void correctEvaluation(){
        WordleModel model = new WordleModel("slept");
        WordleController controller = new WordleController(model);
        controller.onGuess("sleep");
        Guess guess = controller.getLastGuess();
//        System.out.println(guess.getGuess());

        LetterFeedback[] correctEval = {LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.CORRECT, LetterFeedback.ABSENT, LetterFeedback.PRESENT};

        for (int i=0; i<5; i++){
//            System.out.println("GuessPos number: " + i + ": " +guess.getLetterEval(i));
            assertEquals(guess.getLetterEval(i), correctEval[i]);
        }
    }
}
