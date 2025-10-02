package view;

import model.*;
import control.WordleController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SwingWordle extends JFrame{
    private WordleModel model;
    private WordleController controller;
    private int refreshCount = 0; //just for checking first refresh/load past guesses

    private JLabel[][] boardCells = new JLabel[6][5];
    private Map<String, JButton> keyboardButtons = new HashMap<>();
    private Map<String, JButton> usedButtons = new HashMap<>();

    JPanel wordGrid = new JPanel(new GridLayout(6, 5, 5, 5));
    JLabel status = new JLabel("Guess the Word!");
    JPanel keyboardPanel = new JPanel();

    public SwingWordle() {
        setTitle("Wordle");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() { //save game state on close
            @Override
            public void windowClosing(WindowEvent e) {
                controller.saveGame();
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());

        //loading past states for model/controller is there are any
        try {
            model = new WordleModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        controller = new WordleController(model);
        controller.loadGame();
        //end of load for model/controller

        //UI Setup
        JPanel top = new JPanel(new BorderLayout());
        status.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        status.setFont(new Font("Arial", Font.BOLD, 20));
        top.add(status, BorderLayout.WEST);
        add(top, BorderLayout.NORTH);
        add(wordGrid, BorderLayout.CENTER);
        buildGrid();
        buildKeyBoard();
        add(keyboardPanel, BorderLayout.SOUTH);
        setupKeyBindings();

        refresh();
        pack();
        setVisible(true);
    }

    private void onKeyPress(String key){
        controller.onKeyPress(key);
        refresh();
    }

    private void refresh(){
        if (refreshCount == 0){ //if guesses is populated from previous load state, update screen
            loadScreenGuesses();
        }
        //update if new guess made
        updateScreenGuesses();
        //update current buffer
        updateScreenBuffer();
        //update status - gamescore
        status.setText("Score: "+controller.getGameScore() + " Guess Count: "+controller.getGuessCount());
        //win/loss check
        if (controller.isWon()){
            showPopup(true);
        }else if (controller.isLost()){
            showPopup(false);
        }
        refreshCount++;

    }

    private void showPopup(Boolean won){
        String userMessage = won ? "You won! The word was "+controller.getSecretWord() :
                "You lost! The word was "+controller.getSecretWord();
        int choice = JOptionPane.showOptionDialog(
                this,
                userMessage,
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Reset"},
                "Reset"

        );
        if (choice == 0){
            resetGame();
        }
    }

    private void resetGame(){ //reset all on-screen elements and refresh controller
        //reset grid cells
        for (int i=0; i<controller.getGuessCount(); i++){
            for (int j=0; j<5; j++){
                boardCells[i][j].setText("");
                boardCells[i][j].setBackground(null);
            }
        }

        //refresh controller
        try {
            controller.refreshGame();
        }catch (Exception e){
            e.printStackTrace();
        }

        //reset on screen keys
        for (Map.Entry<String, JButton> entry: usedButtons.entrySet()){
            JButton button = entry.getValue();
            button.setBackground(null);
        }
        usedButtons.clear();
        refresh();
    }

    private void loadScreenGuesses(){ //update screen for guesses from prior save state if there are any
        if (controller.getGuessCount()>0){
            Guess[] guessesMade = Arrays.copyOf(controller.getGuesses(), controller.getGuessCount());
            for (int i=0; i<guessesMade.length; i++){
                Guess guessMade = guessesMade[i];
                String guess = guessMade.getGuess();
                for (int j=0; j<5; j++){
                    char ch = guess.charAt(j);
                    LetterFeedback letterEval = guessMade.getLetterEval(j);
                    boardCells[i][j].setText(String.valueOf(ch).toUpperCase());
                    switch (letterEval){
                        case LetterFeedback.CORRECT:
                            boardCells[i][j].setBackground(Color.GREEN);
                            updateKeyColor(String.valueOf(ch).toUpperCase(), Color.GREEN);
                            break;
                        case LetterFeedback.PRESENT:
                            boardCells[i][j].setBackground(Color.YELLOW);
                            break;
                        case LetterFeedback.ABSENT:
                            boardCells[i][j].setBackground(Color.RED);
                            updateKeyColor(String.valueOf(ch).toUpperCase(), Color.GRAY);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private void updateScreenGuesses(){ //draws guesses on screen
        if (controller.getGuessState()){ //only draws Guesses when new guess made
            int currentRow = controller.getGuessCount()-1;
            Guess guessMade = controller.getLastGuess();
            String guess = guessMade.getGuess();
            for (int i=0; i<5; i++){
                char ch = guess.charAt(i);
                LetterFeedback letterEval = guessMade.getLetterEval(i);
                boardCells[currentRow][i].setText(String.valueOf(ch).toUpperCase());
                switch (letterEval){
                    case LetterFeedback.CORRECT:
                        boardCells[currentRow][i].setBackground(Color.GREEN);
                        updateKeyColor(String.valueOf(ch).toUpperCase(), Color.GREEN);
                        break;
                    case LetterFeedback.PRESENT:
                        boardCells[currentRow][i].setBackground(Color.YELLOW);
                        break;
                    case LetterFeedback.ABSENT:
                        boardCells[currentRow][i].setBackground(Color.RED);
                        updateKeyColor(String.valueOf(ch).toUpperCase(), Color.GRAY);
                        break;
                    default:
                        break;
                }
            }

            controller.resetGuessState();
        }
    }

    private void updateKeyColor(String key, Color color){
        JButton button = keyboardButtons.get(key.toUpperCase());
        usedButtons.put(key.toUpperCase(), button);
        if (button != null) {
            button.setBackground(color);
            button.setOpaque(true);

        }
    }

    private void updateScreenBuffer(){ //updates full row of current guess buffer
        int currentRow = controller.getGuessCount();
        if (currentRow<6) {
            String buffer = controller.getBuffer();
            for (int i = 0; i < 5; i++) {
                if (i < buffer.length()) {
                    char ch = buffer.charAt(i);
                    boardCells[currentRow][i].setText(String.valueOf(ch));
                } else {
                    boardCells[currentRow][i].setText("");
                }

            }
        }
    }

    private void buildKeyBoard() { //on screen keyboard
        keyboardPanel.setLayout(new BoxLayout(keyboardPanel, BoxLayout.Y_AXIS));
        addKeyboardRow(new String[] {"Q","W","E","R","T","Y","U","I","O","P"}, keyboardPanel);
        addKeyboardRow(new String[] {"A","S","D","F","G","H","J","K","L"}, keyboardPanel);
        addKeyboardRow(new String[] {"ENTER","Z","X","C","V","B","N","M","BACKSPACE"}, keyboardPanel);
    }

    private void addKeyboardRow(String[] keys, JPanel panel){
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        for (String key : keys) {
            JButton btn = new JButton(key);
            btn.setFocusable(false);
            btn.addActionListener(e -> onKeyPress(key)); // pass the whole label
            keyboardButtons.put(key, btn);
            // optionally style ENTER/BACKSPACE bigger:
            if (key.equals("ENTER") || key.equals("BACKSPACE")) btn.setPreferredSize(new Dimension(80, 40));
            row.add(btn);
        }
        panel.add(row);
    }

    private void buildGrid() {
        wordGrid.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 5; c++) {
                JLabel cell = new JLabel(" ", SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cell.setFont(new Font("Arial", Font.BOLD, 24));
                boardCells[r][c] = cell;
                wordGrid.add(cell);
            }
        }

    }

    private void setupKeyBindings() {
        // Input/Action maps come from the root pane
        InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getRootPane().getActionMap();

        // Bind letters A–Z a-z
        for (char c = 'A'; c <= 'z'; c++) {
            String letter = String.valueOf(c);
            inputMap.put(KeyStroke.getKeyStroke(c), letter);
            actionMap.put(letter, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onKeyPress(letter);
                }
            });
        }

        // Bind ENTER
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        actionMap.put("ENTER", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onKeyPress("ENTER");
            }
        });

        // Bind BACKSPACE
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "BACKSPACE");
        actionMap.put("BACKSPACE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onKeyPress("BACKSPACE");
            }
        });
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(SwingWordle::new);
    }
}
