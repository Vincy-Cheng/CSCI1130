package boardgame;

import static boardgame.Reversi.BLANK;
import java.awt.Color;

/**
 * Connect4Advanced is a subclass of Connect4
 * demonstrating an advanced checkEndGame() technique.
 * @author Michael FUNG
 */
public class Connect4Advanced extends Connect4 {

    // helper methods for student's reference
    protected boolean isBlank(int x, int y)
    {
        return pieces[x][y].getText().equals(BLANK);
    }
    
    protected boolean isFriend(int x, int y)
    {
        return pieces[x][y].getText().equals(currentPlayer);
    }
    
    protected boolean isOpponent(int x, int y)
    {
        return pieces[x][y].getText().equals(getOpponent());
    }

    /**
     * checkEndGame considers the latest move at (moveX, moveY)
     * and determines if gameEnded is true or false.
     * Connect4 winning move may be in the middle of 4 consecutive friends.
     * There may be more than one winning-4 too.
     * Needs to check 4 direction vectors systematically.
     * @param moveX is the x-coordinate of the latest move
     * @param moveY is the y-coordinate of the latest move
     * @return end game status: true or false
     */
    @Override
    protected boolean checkEndGame(int moveX, int moveY)
    {
        System.out.println("checkEndGame(" + moveX + ", " + moveY + "):");

        // DEMONSTRATION PURPOSE
        // check ALL locations on the board, SLOW
        // winning move may be in the middle of 4 consecutive friends
        // there may be more than one winning-4, so do NOT stop early
        for (int x = 0; x < xCount; x++)
            for (int y = 0; y < yCount; y++)
            {
                // skip opponent and blank pieces
                if (!isFriend(x, y)) continue;

                // there are 9 direction vectors, including unused <0, 0>
                // check only 4 direction vectors:
                // <0, 1>   <1, -1>   <1, 0>   <1, 1>
                for (int deltaX = -1; deltaX <= 1; deltaX++)
                    for (int deltaY = -1; deltaY <= 1; deltaY++)
                    {
                        // skip trivial and illegal direction vector <0, 0>
                        if (deltaX == 0 && deltaY == 0) continue;
                        // skip four more symmetric direction vectors
                        if (deltaX == 0 && deltaY == -1) continue;
                        if (deltaX == -1 /* && any deltaY */ ) continue;

                        
                        // along direction vector <deltaX, deltaY>
                        // count number of friends
                        int count = 1;
                        try {
                            // locate friends in one direction
                            while (isFriend(x+count*deltaX, y+count*deltaY))
                                count++;
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {} // use try-catch to simplify code in boundary checking

                        
                        // got 4 consecutive friends in a direction?
                        if (count >= 4)
                        {
                            // print starting point and winning direction vector
                            System.out.println("  (" + x + ", " + y + ") direction vector = <" + deltaX + ", " + deltaY + ">, count = " + count);
                            gameEnded = true;
                            // color the connected winning pieces in YELLOW
                            do {
                                count--;
                                pieces[x+count*deltaX][y+count*deltaY].setBackground(Color.YELLOW);
                                pieces[x+count*deltaX][y+count*deltaY].setOpaque(true);
                            } while (count >= 1);
                        }

                    } // end of nested-loops checking 8 directions

            } // end of nested-loops checking ALL locations
        
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
        Connect4Advanced c4a;
        c4a = new Connect4Advanced();
        c4a.setTitle("Connect 4 Advanced");
        c4a.setLocation(400, 100);
        c4a.verbose = false;
        // the game has started and GUI thread will take over here
    }
    
}
