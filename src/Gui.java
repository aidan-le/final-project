import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class Gui implements ActionListener {
    private String startWord;
    private String endWord;
    private int turnsTaken;
    private ArrayList<Word> words;

    private JTextArea textArea;
    private JTextField textField;
    private JLabel goalLabel;
    private JLabel turnsLabel;
    private JScrollPane scroll;

    public Gui(String startWord, String endWord) {
        this.startWord = startWord;
        this.endWord = endWord;

        goalLabel = new JLabel();
        turnsLabel = new JLabel();
        textArea = new JTextArea();
        textField = new JTextField();

        turnsTaken = 0;
    }

    public void init() {
        JFrame frame = new JFrame("Word Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JLabel goalLabel = new JLabel("Goal Word: "+endWord);
        turnsLabel = new JLabel("Turns Taken: "+turnsTaken);
        topPanel.add(goalLabel);
        topPanel.add(turnsLabel);

        JPanel actionPanel = new JPanel();
        textField = new JTextField(2);
        JButton submitButton = new JButton("Submit");
        JButton restartButton = new JButton("Restart");
        actionPanel.add(textField);
        actionPanel.add(submitButton);
        actionPanel.add(restartButton);

        JPanel wordPanel = new JPanel();
        textArea = new JTextArea();
        wordPanel.add(textArea);

        // init
        displayRelatedWords(startWord);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(actionPanel, BorderLayout.CENTER);
        frame.add(wordPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(this);
        restartButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void displayRelatedWords(String word) {
        words = Networking.getRelatedWords(word, endWord);
        if (words == null) {
            displayRelatedWords(word);
        }

        String display = "";
        for (int i = 0; i < words.size(); i++) {
            display += (i + 1) + ". "+words.get(i).toString()+"\n";
        }
        textArea.setText(display);
        turnsTaken++;
        turnsLabel.setText("Turns Taken: "+turnsTaken);
    }

    private void win() {
        textArea.setText("You win! You solved the puzzle in "+turnsTaken+" turns!");
    }

    public void actionPerformed(ActionEvent e) {
        AbstractButton button = (AbstractButton) (e.getSource());
        String text = button.getText();

        if (text.equals("Restart")) {
            displayRelatedWords(startWord);
        } else if (text.equals("Submit")) {
            String word = words.get(Integer.parseInt(textField.getText()) - 1).getWord();
            if (word.equals(endWord)) {
                win();
            } else {
                displayRelatedWords(word);
            }
        }
    }
}