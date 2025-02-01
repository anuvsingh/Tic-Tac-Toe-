import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JPanel bottom_panel = new JPanel(); // For Restart Button
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    JButton restartButton = new JButton("Restart Game"); // Restart button
    boolean player1_turn;

    TicTacToe() {
        ImageIcon icon = new ImageIcon("C:/Users/rudra/eclipse-workspace/TicTacToe/logo.png");
        frame.setIconImage(icon.getImage());

        // Frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 800);
        frame.getContentPane().setBackground(new Color(30, 30, 47));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Textfield styling
        textfield.setBackground(new Color(30, 30, 47));
        textfield.setForeground(new Color(255, 140, 0)); // Orange for title
        textfield.setFont(new Font("Poppins", Font.BOLD, 60));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        // Title panel setup
        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        // Button panel setup
        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(44, 44, 52));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("Poppins", Font.BOLD, 100));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(44, 44, 52));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(255, 255, 0), 5));
            buttons[i].addActionListener(this);

            // Hover effect
            final int index = i;
            buttons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (buttons[index].getText().equals("")) {
                        buttons[index].setBackground(new Color(50, 205, 50));
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (buttons[index].getText().equals("")) {
                        buttons[index].setBackground(new Color(44, 44, 52));
                    }
                }
            });
        }

        // Restart button styling
        restartButton.setFont(new Font("Poppins", Font.BOLD, 30));
        restartButton.setFocusable(false);
        restartButton.setBackground(new Color(255, 140, 0)); // Orange button
        restartButton.setForeground(Color.WHITE);
        restartButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        restartButton.addActionListener(e -> restartGame()); // Action listener for restart
        bottom_panel.setLayout(new FlowLayout());
        bottom_panel.setBackground(new Color(30, 30, 47));
        bottom_panel.add(restartButton);

        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel, BorderLayout.CENTER);
        frame.add(bottom_panel, BorderLayout.SOUTH);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255, 0, 0)); // Bright red for X
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O Turn");
                        check();
                    }
                } else {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255, 215, 0)); // Bright yellow for O
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X Turn");
                        check();
                    }
                }
            }
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X Turn");
        } else {
            player1_turn = false;
            textfield.setText("O Turn");
        }
    }

    public void check() {
        // Check winning conditions for X and O
        String[] patterns = {
            "012", "345", "678", // Horizontal
            "036", "147", "258", // Vertical
            "048", "246"         // Diagonal
        };

        for (String pattern : patterns) {
            int a = pattern.charAt(0) - '0';
            int b = pattern.charAt(1) - '0';
            int c = pattern.charAt(2) - '0';

            if (buttons[a].getText().equals("X") && buttons[b].getText().equals("X") && buttons[c].getText().equals("X")) {
                xWins(a, b, c);
                return;
            } else if (buttons[a].getText().equals("O") && buttons[b].getText().equals("O") && buttons[c].getText().equals("O")) {
                oWins(a, b, c);
                return;
            }
        }

        // Check for tie
        boolean tie = true;
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                tie = false;
                break;
            }
        }

        if (tie) {
            tieGame();
        }
    }

    public void tieGame() {
        for (JButton button : buttons) {
            button.setEnabled(false);
            button.setBackground(new Color(128, 128, 128)); // Gray for tie
        }
        textfield.setText("It's a Tie!");
    }

    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(new Color(0, 191, 255)); // Neon green for X win
        buttons[b].setBackground(new Color(0, 191, 255));
        buttons[c].setBackground(new Color(0, 191, 255));
        textfield.setText("X Wins!");
    }

    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(new Color(0, 191, 255)); // Neon green for O win
        buttons[b].setBackground(new Color(0, 191, 255));
        buttons[c].setBackground(new Color(0, 191, 255));
        textfield.setText("O Wins!");
    }

    private void restartGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(new Color(44, 44, 52));
        }
        firstTurn();
        textfield.setText("Tic-Tac-Toe");
    }
}
