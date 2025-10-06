# Wordle Project Report

## Design Decisions

### Architecture
 - The model in this instance is WordleModel. It holds the core game states, including score,
guess count, the secret word string, and a list of guesses made. The RandomWordFetcher class is called
to generate the random String. The list of guesses is composed of Guess objects which when instantiated are populated with LetterFeedback enum information.
WordleController acts as the middle man between the model and the view. The view never directly interacts with the model, instead being routed to
relevant model functions through the controller. The controller handles user input from the view and validates the guesses (GuessValidator) that are made
before sending them to the model where the game state is updated. Finally, the view handles the playable game. It reads input from the user, allowing them to make guesses
and see the visual feedback on screen. With regards to abstractions and interfaces, the rules and structure of Wordle 
are simplistic enough to not need incredible flexibility, so I didn't need them in this instance.
I opted instead for simple classes to make objects like SaveState and hold static functions like SaveLoad.
If there were many versions of the game that one could implement or you wanted to design your own version of Wordle,
then interfaces/abstractions could prove very useful. Finally, I chose Swing over JavaFX because I am more familiar with Swing,
and Wordle did not in my mind require anything complex from a UI perspective.

### Data Structures
 - Game states within the model are represented by ints, Strings, and an array of Guesses. The secret word is always a 5-letter word string,
and guessCount and gameScore require only simple integers, so nothing special needed here. The guessesMade array is always going to filled with at max
6 guesses, and it is composed of Guess objects so that the ingrained LetterFeedback information can be accessed later. Guess objects themselves are just
a string for the word and a LetterFeedback array of length 5, one element per letter. The input buffer held in the controller
is a simple String as well. In the view, the board cells are stored in a 2d array of JLabels of size 5x6. This made them straightforward to track and update.
The onscreen keyboard buttons are held in a HashMap mapping the String representation to the linked JButton. That way,
any input received can be passed to the controller and also quickly linked to the relevant button, so its visuals can be updated accordingly.

### Algorithms
 - A key algorithm implemented would be the word evaluation carried out in checkWord within Guess.java. Instead of passing through the guess string
once and comparing each letter to the secret word to create LetterFeedback, I first count the occurrences of each letter in the secret word. Then, two separate passes
over the guess string allow the evaluation of only CORRECT letters first while appropriately subtracting from the occurrence counts. The second pass deals with ABSENT and PRESENT,
only populating PRESENT when there is still an occurrence left within the count array for that given character.

## Challenges Faced
**1.** One problem I faced included evaluating the letters of each guess word (inside the Guess class). At first, I was simply comparing each letter individually to the secretWord which resulted in incorrect evaluations when a letter was guessed more than once.
 - To solve this problem, I switched to a two pass evaluation algorithm which I mentioned earlier. After counting the occurrences of each letter in the secretWord, I pass over the guess twice, 
first counting the CORRECT letters (and decrementing the corresponding/available before then handling PRESENT and ABSENT letters. This prevents the appearance of PRESENT second letters when the secretWord
does not have more than one occurrence of the letter.

**2.** Another challenge I faced was trying to store the game state in a JSON file, as I was not super familiar with working with JSON files.
 - After researching best practices, I discovered the gson library which allows you to convert classes into JSON saveable objects and vice versa very easily. So, I created a SaveState class which includes the 
gameScore, secretWord, and guesses list to then pair with the toJson/fromJson functions.

**3.** A third challenge I faced encompassed loading past save states onto the screen. My original updateScreenGuesses function only updated the grid on screen when a guess is made. When loading in a past save state that had some guesses made, they were not printed to the screen.
 - I ended up making a loadScreenGuesses function that goes through all guesses loaded into guessesMade. It only triggers in refresh on the first refresh of the program,
so as not to overlap with any subsequent screen updates.

## What We Learned
 - This project certainly ingrained the MVC structure in my understanding. This was not something I was familiar with, but its usage combined with oop encapsulation
definitely made sense and helped streamline the process of making this. Similarly, the concepts of polymorphism and inheritance were reinforced through
controller/model routing and my specific implementation of the swing JFrame. I also benefited from more exposure
to the JUnit testing framework which aided my debugging/testing process.
## If We Had More Time
 - I would certainly want to implement a local cache of dictionary words for validating inputs to speed up general play and performance. In that case,
the dictionary api could be a fallback mechanism for odd but valid English guesses. 
 - The view could probably be refactored to help organization, and I would maybe want to experiment with JavaFX to 
deliver a more visually appealing experience. 