package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import model.*;
import control.WordleController;

import static org.junit.jupiter.api.Assertions.*;

public class WordleTest {

    @Test
    void basicWinTest(){
        WordleModel model = new WordleModel("mouth");
        WordleController controller = new WordleController(model);
        controller.onGuess("mouth");
        assertEquals(true, controller.isWon());
    }

    @Test
    void basicNoWinTest(){
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
}
