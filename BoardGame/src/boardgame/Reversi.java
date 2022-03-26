/**
 * CSCI1130 Java Assignment 6 BoardGame Reversi
 * Aim: Practise subclassing, method overriding
 *      Learn from other subclass examples
 *
 * I declare that the assignment here submitted is original
 * except for source material explicitly acknowledged,
 * and that the same or closely related material has not been
 * previously submitted for another course.
 * I also acknowledge that I am aware of University policy and
 * regulations on honesty in academic work, and of the disciplinary
 * guidelines and procedures applicable to breaches of such
 * policy and regulations, as contained in the website.
 *
 * University Guideline on Academic Honesty:
 *   http://www.cuhk.edu.hk/policy/academichonesty
 * Faculty of Engineering Guidelines to Academic Honesty:
 *   https://www.erg.cuhk.edu.hk/erg/AcademicHonesty
 *
 * Student Name: Cheng WIng Lam <fill in yourself>
 * Student ID : 1155125313 <fill in yourself>
 * Date : 08//12/2021 <fill in yourself>
 */
package boardgame;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Reversi is a TurnBasedGame
 */
public class Reversi extends TurnBasedGame {

    public static final String BLANK = " ";
    String winner;
    private boolean passed = false;
    private boolean passtwice = false;
    private int black, white;
    private JButton[][] copy = new JButton[8][8];

    /**
     * * TO-DO: STUDENT'S WORK HERE **
     */
    public Reversi() {
        super(8, 8, "BLACK", "WHITE");
        this.setTitle("Reversi");
    }

    @Override
    protected void initGame() {
        for (int y = 0; y < yCount; y++) {
            for (int x = 0; x < xCount; x++) {
                pieces[x][y].setText(" ");
            }
        }
        pieces[3][3].setText("WHITE");
        pieces[3][3].setBackground(Color.WHITE);
        pieces[3][4].setText("BLACK");
        pieces[3][4].setBackground(Color.BLACK);
        pieces[4][3].setText("BLACK");
        pieces[4][3].setBackground(Color.BLACK);
        pieces[4][4].setText("WHITE");
        pieces[4][4].setBackground(Color.WHITE);
    }

    @Override
    protected void gameAction(JButton triggeredButton, int x, int y) {
        //System.out.println();

        //System.out.println("Move" + isValidMove(x, y));
        //System.out.println("temp = " + temp);

        if (mustPass() == false) {
            
            addLineToOutput("Pass!");
            changeTurn();
        } else {
            
            if (isBlank(x,y) && isValidMove(x, y)) {
                pieces[x][y].setText(currentPlayer);
                pieces[x][y].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                addLineToOutput(currentPlayer + " piece at  (" + x + "," + y + ")");
                //pieces[x][y].setOpaque(true);

                //System.out.println("clicked " + count);
                changeTurn();

            } else {
                addLineToOutput("Invalid move!");

            }
        }
        boolean endgame = checkEndGame(x, y);
        if (endgame == true) {
            countPieces();
            addLineToOutput("Game ended!");
            addLineToOutput("BLACK score:" + black);
            addLineToOutput("WHITE score:" + white);
            if (white != black) {
                if (black > white) {
                    winner = "BLACK";
                } else {
                    winner = "WHITE";
                }
                addLineToOutput("Winner is " + winner + "!");
            } else {
                addLineToOutput("Draw Game");
            }
            JOptionPane.showMessageDialog(null, "Game ended!");
            return;
        }

    }

    protected boolean isBlank(int x, int y) {
        return pieces[x][y].getText().equals(BLANK);
    }

    protected boolean isFriend(int x, int y) {
        return pieces[x][y].getText().equals(currentPlayer);
    }

    protected boolean isOpponent(int x, int y) {
        return pieces[x][y].getText().equals(getOpponent());
    }

    protected boolean isValidMove(int x, int y) {
        boolean flip = false;
        int i, j;
        //System.out.println("Move" + x + "," + y);
        // each tile has 8 directions
        // check up
        i = y - 1;
        if (i > 0) {
            if (isOpponent(x, i) ) {
                for (; i >= 0; i--) {
                    if (isBlank(x, i)) {
                        break;
                    }
                    if (isFriend(x, i)) {
                        flip = true;
                        for (j = y - 1; j > i; j--) {
                            pieces[x][j].setText(currentPlayer);
                            pieces[x][j].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                        }
                        //System.out.println("1");
                        break;
                    }
                }
            }
        }

        // check down
        i = y + 1;

        if (i < 8) {
            if (isOpponent(x, i)) {
                for (; i < 8; i++) {
                    if (isBlank(x, i)) {
                        break;
                    }
                    if (isFriend(x, i)) {
                        flip = true;
                        for (j = y + 1; j < i; j++) {
                            pieces[x][j].setText(currentPlayer);
                            pieces[x][j].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                        }
                        //System.out.println("2");
                        break;
                    }
                }
            }
        }

        // check left
        i = x - 1;
        if (i > 0) {
            if (isOpponent(i, y)) {
                for (; i >= 0; i--) {
                    if (isBlank(i, y)) {
                        break;
                    }
                    if (isFriend(i, y)) {
                        flip = true;
                        for (j = x - 1; j > i; j--) {
                            pieces[j][y].setText(currentPlayer);
                            pieces[j][y].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                        }
                        //System.out.println("3");
                        break;
                    }
                }
            }

        }

        // check right
        i = x + 1;
        if (i < 8) {
            if (isOpponent(i, y)) {
                for (; i < 8; i++) {
                    if (isBlank(i, y)) {
                        break;
                    }
                    if (isFriend(i, y)) {
                        flip = true;
                        for (j = x + 1; j < i; j++) {
                            pieces[j][y].setText(currentPlayer);
                            pieces[j][y].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                        }
                        //System.out.println("4");
                        break;
                    }
                }
            }

        }

        // check top left
        int m, n;

        i = y - 1;
        j = x - 1;
        if (i >= 0 && j >= 0) {
            if (isOpponent(j, i)) {
                for (; i >= 0 && j >= 0; i--, j--) {
                    if (isBlank(j, i)) {
                        break;
                    }
                    if (isFriend(j, i)) {
                        flip = true;
                        for (m = y - 1, n = x - 1; m > i && n > j; m--, n--) {
                            pieces[n][m].setText(currentPlayer);
                            pieces[n][m].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                        }
                        //System.out.println("5");
                        break;
                    }
                }
            }
        }

        // check top right
        i = y - 1;
        j = x + 1;
        if (i > 0 && j < 8) {
            if (isOpponent(j, i)) {
                for (; i >= 0 && j < 8; i--, j++) {
                    if (isBlank(j, i)) {
                        break;
                    }
                    if (isFriend(j, i)) {
                        flip = true;
                        for (m = y - 1, n = x + 1; m > i && n < j; m--, n++) {
                            pieces[n][m].setText(currentPlayer);
                            pieces[n][m].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                        }
                        //System.out.println("6");
                        break;
                    }
                }
            }
        }

        // check buttom left
        i = y + 1;
        j = x - 1;
        if (i < 8 && j > 0) {
            if (isOpponent(j, i)) {
                for (; i < 8 && j >= 0; i++, j--) {
                    if (isBlank(j, i)) {
                        break;
                    }
                    if (isFriend(j, i)) {
                        flip = true;
                        for (m = y + 1, n = x - 1; m < i && n > j; m++, n--) {
                            pieces[n][m].setText(currentPlayer);
                            pieces[n][m].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                        }
                        //System.out.println("7");
                        break;
                    }
                }
            }
        }

        // check buttom right
        i = y + 1;
        j = x + 1;
        if (i < 8 && j < 8) {
            if (isOpponent(j, i)) {
                for (; i < 8 && j < 8; i++, j++) {
                    if (isBlank(j, i)) {
                        break;
                    }
                    if (isFriend(j, i)) {
                        flip = true;
                        for (m = y + 1, n = x + 1; m < i && n < j; m++, n++) {
                            pieces[n][m].setText(currentPlayer);
                            pieces[n][m].setBackground(currentPlayer.equals("BLACK") ? Color.BLACK : Color.WHITE);
                        }
                        //System.out.println("8");
                        break;
                    }
                }
            }
        }
        return flip;
    }

    protected boolean CheckPass(int x, int y) {// same method as isVaildMove
        boolean flip = false;
        int i, j;
        //System.out.println("Move" + x + "," + y);

        // check up
        i = y - 1;
        if (i > 0) {
            if (isOpponent(x, i)) {
                for (; i >= 0; i--) {
                    if (isBlank(x, i)) {
                        break;
                    }
                    if (isFriend(x, i)) {
                        flip = true;

                        break;
                    }
                }
            }
        }

        // check down
        i = y + 1;

        if (i < 8) {
            if (isOpponent(x, i)) {
                for (; i < 8; i++) {
                    if (isBlank(x, i)) {
                        break;
                    }
                    if (isFriend(x, i)) {
                        flip = true;

                        break;
                    }
                }
            }
        }

        // check left
        i = x - 1;
        if (i > 0) {
            if (isOpponent(i, y)) {
                for (; i >= 0; i--) {
                    if (isBlank(i, y)) {
                        break;
                    }
                    if (isFriend(i, y)) {
                        flip = true;

                        break;
                    }
                }
            }

        }

        // check right
        i = x + 1;
        if (i < 8) {
            if (isOpponent(i, y)) {
                for (; i < 8; i++) {
                    if (isBlank(i, y)) {
                        break;
                    }
                    if (isFriend(i, y)) {
                        flip = true;

                        break;
                    }
                }
            }

        }

        // check top left
        int m, n;

        i = y - 1;
        j = x - 1;
        if (i >= 0 && j >= 0) {
            if (isOpponent(j, i)) {
                for (; i >= 0 && j >= 0; i--, j--) {
                    if (isBlank(j, i)) {
                        break;
                    }
                    if (isFriend(j, i)) {
                        flip = true;

                        break;
                    }
                }
            }
        }

        // check top right
        i = y - 1;
        j = x + 1;
        if (i > 0 && j < 8) {
            if (isOpponent(j, i)) {
                for (; i >= 0 && j < 8; i--, j++) {
                    if (isBlank(j, i)) {
                        break;
                    }
                    if (isFriend(j, i)) {
                        flip = true;

                        break;
                    }
                }
            }
        }

        // check buttom left
        i = y + 1;
        j = x - 1;
        if (i < 8 && j > 0) {
            if (isOpponent(j, i)) {
                for (; i < 8 && j >= 0; i++, j--) {
                    if (isBlank(j, i)) {
                        break;
                    }
                    if (isFriend(j, i)) {
                        flip = true;
                        break;
                    }
                }
            }
        }

        // check buttom right
        i = y + 1;
        j = x + 1;
        if (i < 8 && j < 8) {
            if (isOpponent(j, i)) {
                for (; i < 8 && j < 8; i++, j++) {
                    if (isBlank(j, i)) {
                        break;
                    }
                    if (isFriend(j, i)) {
                        flip = true;
                        break;
                    }
                }
            }
        }
        return flip;
    }

    protected boolean mustPass() {
        copy = pieces;
        boolean move = false;
        for (int i = 0; i < 8; i++) {
            if (move) {
                break;
            }
            for (int j = 0; j < 8; j++) {
                if (isBlank(i, j) == true) {
                    if (CheckPass(i, j)) {
                        move = true;
                        break;
                    }
                }
            }
        }

        if (move == false) {
            if (passed = true) {
                if (passtwice != true) {
                    passtwice = true;
                } else {
                    passed = true;
                }
            } else {
                passed = false;
            }
            
        }
        
        return move;
    }


    @Override
    protected boolean checkEndGame(int moveX, int moveY) {
        if (passtwice == true ) {
            gameEnded = true;
            return gameEnded;
        } else {
            gameEnded = false;
            return gameEnded;
        }
    }

    protected void countPieces() { // count total number of tile
        int b = 0, w = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j].getText().equals("BLACK")) {
                    b++;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j].getText().equals("WHITE")) {
                    w++;
                }
            }
        }
        black = b;
        white = w;
    }

    public static void main(String[] args) {
        Reversi reversi;
        reversi = new Reversi();

        // TO-DO: run other classes, one by one
        System.out.println("You are running class Reversi");

        // TO-DO: study comment and code in other given classes
        // TO-DO: uncomment these two lines when your work is ready
        reversi.setLocation(400, 20);
        reversi.verbose = false;

        // the game has started and GUI thread will take over here
    }
}
