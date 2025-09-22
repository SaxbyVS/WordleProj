package view;

import model.*;
import control.WordleController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class SwingWordle extends JFrame{
    private WordleModel model;
    private WordleController controller;


    private JLabel[][] boardCells = new JLabel[6][5];
    private Map<String, JButton> keyboardButtons = new HashMap<String, JButton>();

    JPanel wordGrid = new JPanel(new GridLayout(6, 5, 5, 5));
    JLabel status = new JLabel("Guess the Word!");
    JPanel keyboardPanel = new JPanel();

    public SwingWordle() {
        setTitle("Wordle");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() { //save game state on close
            @Override
            public void windowClosing(WindowEvent e) {
                //controller.saveGame();
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());

        //loading past states for model/controller is there are any

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

        pack();
        setVisible(true);
    }

    private void onKeyPress(String key){
        controller.onKeyPress(key);
        refresh();
    }

    private void refresh(){
        //update if guess made

        //update current buffer

        //update status - gamescore, guesscount

        //win/loss check
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

        // Bind letters A–Z
        for (char c = 'A'; c <= 'Z'; c++) {
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
