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

    //populates letterEval array with appropriate feedback (2 pass approach to deal with duplicates)
    public void checkWord(String secretWord){
        int[] letterCounts = new int[26];
        for (int i = 0; i < secretWord.length(); i++) { //count instances of each letter in secret word
            letterCounts[secretWord.charAt(i) - 'a']++;
        }

        //first pass: only deal with Correct
        for (int i=0; i<5; i++){
            if (guess.charAt(i) == secretWord.charAt(i)){
                letterEval[i] = LetterFeedback.CORRECT;
                letterCounts[guess.charAt(i) - 'a']--;
            }
        }
        //second pass: Present and Absent
        for(int i = 0; i < 5; i++) {
            if (letterEval[i] == LetterFeedback.CORRECT){
            }
            else if (secretWord.contains(String.valueOf(guess.charAt(i))) && letterCounts[guess.charAt(i) - 'a'] > 0) {
                letterEval[i] = LetterFeedback.PRESENT;
                letterCounts[guess.charAt(i) - 'a']--;
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
