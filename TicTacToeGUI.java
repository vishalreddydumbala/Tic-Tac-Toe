import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame implements ActionListener {

    private JPanel menuPanel;
    private JButton oneVsOneButton;
    private JButton oneVsCompButton;
    private JPanel gamePanel;
    private JButton[][] buttons;
    private String currentPlayer;
    private JLabel statusLabel;
    private boolean flag;

    public TicTacToeGUI() {

        menuPanel = new JPanel();
        oneVsOneButton = new JButton("1 vs 1");
        oneVsCompButton = new JButton("1 vs Computer");
        menuPanel.add(oneVsOneButton);
        menuPanel.add(oneVsCompButton);
        flag = false;

        // Add action listeners to buttons
        oneVsOneButton.addActionListener(this);
        oneVsCompButton.addActionListener(this);

        // Add menu panel to frame
        add(menuPanel);

        setTitle("Tic Tac Toe");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // flag=true if 1 Vs 1
        if(e.getSource()==oneVsOneButton){
            flag =true;
        }

        // Remove menu panel from frame
        remove(menuPanel);

        // Create new game panel
        gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        currentPlayer = "X";
        statusLabel = new JLabel("Player " + currentPlayer + "'s turn");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].addActionListener(new ButtonListener(i, j,flag));
                gamePanel.add(buttons[i][j]);
            }
        }

        // Add status label and game panel to frame
        add(statusLabel, BorderLayout.NORTH);
        add(gamePanel);

        // Resize frame to fit game panel
        pack();
    }

    private boolean isWinner(String player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                    buttons[i][0].getText().equals(String.valueOf(player))) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                    buttons[1][i].getText().equals(buttons[2][i].getText()) &&
                    buttons[0][i].getText().equals(String.valueOf(player))) {
                return true;
            }
        }

        // Check diagonals
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                buttons[0][0].getText().equals(String.valueOf(player))) {
            return true;
        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                buttons[0][2].getText().equals(String.valueOf(player))) {
            return true;
        }

        return false;
    }

    private boolean isTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonListener implements ActionListener {

        private int row;
        private int col;
        private boolean flag;

        public ButtonListener(int row, int col,boolean flag){
            this.row = row;
            this.col = col;
            this.flag=flag;
        }

        public void actionPerformed(ActionEvent e) {

            //1 Vs 1
            if(flag==true){
                if(buttons[row][col].getText().isEmpty()){
                    buttons[row][col].setText(String.valueOf(currentPlayer));
                    if (isWinner(currentPlayer)) {
                            JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                            // int input = JOptionPane.showConfirmDialog(null, "Do you want to play again");
                            // if(input==JOptionPane.YES_OPTION){
                            //     remove(gamePanel);
                            //     new TicTacToeGUI();
                            // }
                            for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {
                                    buttons[i][j].setEnabled(false);
                                }
                            }
                    }else if(isTie()){
                        statusLabel.setText("Tie game!");
                    }else{
                        currentPlayer = currentPlayer == "X" ? "O" : "X";
                        statusLabel.setText("Player " + currentPlayer + "'s turn");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "invalid move");
                }
                return;
            }

            //Ai player
            if (buttons[row][col].getText().isEmpty()) {
                    buttons[row][col].setText(String.valueOf(currentPlayer));
                    if (isWinner(currentPlayer)) {
                            JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                            for (int i = 0; i < 3; i++) {
                                for (int j = 0; j < 3; j++) {
                                    buttons[i][j].setEnabled(false);
                                }
                            }
                    }else if(isTie()){
                        statusLabel.setText("Tie game!");
                    }else{
                        currentPlayer = currentPlayer == "X" ? "O" : "X";
                        statusLabel.setText("Player " + currentPlayer + "'s turn");
                        if (currentPlayer == "O") {
                                int[] move = getComputerMove();
                                buttons[move[0]][move[1]].doClick();
                        }
                    }
            }else{
                JOptionPane.showMessageDialog(null, "invalid move");
            }
        }

        private int[] getComputerMove() {
            int[] move = new int[2];
            // Try to win
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().isEmpty()) {
                        buttons[i][j].setText("O");
                        if (isWinner("O")) {
                            move[0] = i;
                            move[1] = j;
                            buttons[i][j].setText("");
                            return move;
                        }
                        buttons[i][j].setText("");
                    }
                }
            }
            // Try to block
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().isEmpty()) {
                        buttons[i][j].setText("X");
                        if (isWinner("X")) {
                            move[0] = i;
                            move[1] = j;
                            buttons[i][j].setText("");
                            return move;
                        }
                        buttons[i][j].setText("");
                    }
                }
            }
            // Random move
            do {
                move[0] = (int) (Math.random() * 3);
                move[1] = (int) (Math.random() * 3);
            } while (!buttons[move[0]][move[1]].getText().isEmpty());
            return move;
        }
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}