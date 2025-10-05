# Wordle

### Shane Uriarte

## How to Run
1. Clone repository: 'git clone https://github.com/SaxbyVS/WordleProj.git'
2. Open in Intellij
3. Run 'SwingWordle.java' under /view

## Features
 - Secret Word Selection: randomly generated 5 letter word from api
 - Guess Input: player can input guesses using on-screen keyboard or by typing
 - Feedback System: Will receive visual colored feedback on guesses/represented on on-screen keyboard
     - includes status of all used letters
 - Dictionary Validation: uses api to ensure only valid English words accepted
 - Win/Lose Conditions: Guess the word within 6 guesses to win; lose if 6 incorrect guesses made

## Known Issues
 - Dictionary validation using api for every guess requires internet connection to play. Making guesses in game is also not as responsive for that same reason. It is still very playable though, so no worries. 

## External Libraries
 - Gson 2.13.2 (save/load using JSON)
 - JUnit 5.13.4 (Testing)
