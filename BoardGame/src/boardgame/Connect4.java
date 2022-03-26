package boardgame;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 * Connect4 is a TurnBasedGame
 * @author Michael FUNG
 */
public class Connect4 extends TurnBasedGame {
    
    // DEMO using default access modifier
    String winner;
    
    // this 7-slot array keeps track of "top" y-coordinate of every column
    protected int[] columnVacancyLeft;
    
    // Connect4 default and only constructor
    public Connect4()
    {
        super(7, 6, "RED", "BLUE");
        this.setTitle("Connect 4");
        columnVacancyLeft = new int[xCount];
        for (int col = 0; col < xCount; col++)
            columnVacancyLeft[col] = yCount;
    }

    
    @Override
    protected void initGame()
    {
        for (int y = 0; y < yCount; y++)
            for (int x = 0; x < xCount; x++)
                pieces[x][y].setText(" ");
    }
    
    
    /**
     * This method runs in a separate Thread, started by BoardGame.actionPerformed().
     * gameAction() of Connect4 lets user drop a piece on column x.
     * This method discards y, instead, it determines top_y location
     * using array columnVacancyLeft[at column x].
     * @param triggeredButton is the button clicked by the user, to be discarded
     * @param x is the x-coordinate of the clicked button
     * @param y is the y-coordinate of the clicked button, to be discarded
     */
    @Override
    protected void gameAction(JButton triggeredButton, int x, int y)
    {
        // defensive programming, destroy unused variables to avoid accidental use
        triggeredButton = null;
        y = -1;

        if (columnVacancyLeft[x] == 0)
        {
            addLineToOutput("Invalid move!");
            return;
        }
        
        // substitute y (of triggeredButton) with top y-coordinate of the column x
        // adjust also top-position using prefix decrement operator on array slot
        int top_y = --columnVacancyLeft[x];
        Color animatedColor = currentPlayer.equals("RED") ? Color.RED : Color.BLUE;

        for (int animatedDropPiece = 0; animatedDropPiece < top_y; animatedDropPiece++)
        {
            pieces[x][animatedDropPiece].setBackground(animatedColor);
            delayInMSwhileRunningOtherTasks(50);
            pieces[x][animatedDropPiece].setBackground(null);
        }

        // settle the new piece
        pieces[x][top_y].setText(currentPlayer);
        pieces[x][top_y].setBackground(currentPlayer.equals("RED") ? Color.RED : Color.BLUE);
        pieces[x][top_y].setOpaque(true);

        addLineToOutput(currentPlayer + " piece at column " + x);

        checkEndGame(x, top_y);

        if (gameEnded)
        {
            addLineToOutput("Game ended!");
            JOptionPane.showMessageDialog(null, "Game ended!");
        }
        else    
            changeTurn();
    }

    
    /**
     * checkEndGame considers the latest move at (moveX, moveY)
     * and determines if gameEnded is true or false.
     * Connect4 winning move may be in the middle of 4 consecutive friends.
     * There may be more than one winning-4 too.
     * Needs to check 4 directions.
     * @param moveX is the x-coordinate of the latest move
     * @param moveY is the y-coordinate of the latest move
     * @return end game status: true or false
     */
    @Override
    protected boolean checkEndGame(int moveX, int moveY)
    {
        int count;

        // winning move may be in the middle of 4 consecutive friends
        // there may be more than one winning-4, so do NOT stop early
        
        // horizontal check
        count = 0;
        for (int x = 0; x < xCount; x++)
            if (pieces[x][moveY].getText().equals(currentPlayer))
            {
                count++;
                if (count == 4)
                {
                    gameEnded = true;
                    // color the connected winning pieces in YELLOW
                    while (count-- > 0)
                    {
                        pieces[x][moveY].setBackground(Color.YELLOW);
                        pieces[x][moveY].setOpaque(true);
                        x--;
                    }
                    break;
                }
            }
            else
                count = 0;

        // vertical check
        count = 0;
        for (int y = 0; y < yCount; y++)
            if (pieces[moveX][y].getText().equals(currentPlayer))
            {
                count++;
                if (count == 4)
                {
                    gameEnded = true;
                    // color the connected winning pieces in YELLOW
                    while (count-- > 0)
                    {
                        pieces[moveX][y].setBackground(Color.YELLOW);
                        pieces[moveX][y].setOpaque(true);
                        y--;
                    }                    
                    break;
                }
            }
            else
                count = 0;
        
        // diagonal \ check
        count = 0;
        for (int i = -6; i <= 6; i++)
            try 
            {
                if (pieces[moveX + i][moveY + i].getText().equals(currentPlayer))
                {
                    count++;
                    if (count == 4)
                    {
                        gameEnded = true;
                        // color the connected winning pieces in YELLOW
                        while (count-- > 0)
                        {
                            pieces[moveX + i][moveY + i].setBackground(Color.YELLOW);
                            pieces[moveX + i][moveY + i].setOpaque(true);
                            i--;
                        }
                        break;
                    }
                }
                else
                    count = 0;
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                count = 0;
            }
        
        // diagonal / check
        count = 0;
        for (int i = -6; i <= 6; i++)
            try 
            {
                if (pieces[moveX + i][moveY - i].getText().equals(currentPlayer))
                {
                    count++;
                    if (count == 4)
                    {
                        gameEnded = true;
                        // color the connected winning pieces in YELLOW
                        while (count-- > 0)
                        {
                            pieces[moveX + i][moveY - i].setBackground(Color.YELLOW);
                            pieces[moveX + i][moveY - i].setOpaque(true);
                            i--;
                        }
                        break;
                    }
                }
                else
                    count = 0;
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                count = 0;
            }

        
        if (gameEnded)
        {
            winner = currentPlayer;
            addLineToOutput("Winner is " + winner + "!");
        }
        else
            if (turn == xCount * yCount)
            {
                gameEnded = true;
                addLineToOutput("Draw game!");
            }
        return gameEnded;
    }
        
    public static void main(String[] args)
    {
        Connect4 c4;
        c4 = new Connect4();
        c4.setLocation(400, 100);
        c4.verbose = false;
        // the game has started and GUI thread will take over here
    }
    
}
