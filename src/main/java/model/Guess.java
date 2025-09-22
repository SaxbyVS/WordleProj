package model;
import java.util.Arrays;

//houses individual 5- letter words guessed by user;
//provides access to correctness of each letter


public class Guess {
    private final String guess;
    private final LetterFeedback[] letterEval = new LetterFeedback[5];

    public Guess(String guess, String secret) {
        this.guess = guess;
        checkWord(secret);
    }

    //populates letterEval array with appropriate feedback
    public void checkWord(String secretWord){
        for(int i = 0; i < 5; i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                letterEval[i] = LetterFeedback.CORRECT;
            } else if (secretWord.contains(String.valueOf(guess.charAt(i)))){
                letterEval[i] = LetterFeedback.PRESENT;
            } else{
                letterEval[i] = LetterFeedback.ABSENT;
            }
        }
    }

   //GETTERS

    public String getGuess() {
        return guess;
    }
    //return validity of letter according to given position
    public LetterFeedback getLetterEval(int pos) {
        return letterEval[pos];
    }

    @Override
    public String toString() {
        return "Guess string: \""+this.guess + "\" Feedback: "+ Arrays.toString(letterEval);
    }
}
