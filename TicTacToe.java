package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***
 * TicTacToe based off the DrawTemplate template
 */
public class TicTacToe extends JPanel {
    private int width = 510; // make multiple of 3 to draw the grid nicely
    private int height = width;
    private int turn = 1; // who goes first? 1 = X, 2 = O

    private Timer timer; // timer used to redraw the screen whenever we click
    private TicTacGrid ticTacGrid; // a simple but versatile grid structure

    private boolean hasWinner = false;
    private String winnerXO = "";
    private int winsX = 0;
    private int winsO = 0;

    private boolean game = false; // used for drawing at the start of the program before we draw the game
    private int opacity = 0; // used to control background colour opacity in drawing at the start of the program

    public TicTacToe() {
        ticTacGrid = new TicTacGrid(width, height, height / 3);
        setPreferredSize(new Dimension(width, height));

        addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) { }
                resetGame();
            }
            public void keyTyped(KeyEvent e) { }
            public void keyReleased(KeyEvent e) { }
        });

        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                timer.start();
                onMouseClick(e);
            }
            public void mouseEntered(MouseEvent arg0) { }
            public void mouseExited(MouseEvent arg0) { }
            public void mousePressed(MouseEvent arg0) { }
            public void mouseReleased(MouseEvent arg0) { }
        });

        ActionListener animation = ae -> repaint();
        timer = new Timer(50, animation);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            TicTacToe game = new TicTacToe();
            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(game);
            frame.addKeyListener(game.getKeyListeners()[0]);
            frame.setResizable(false);
            frame.pack();
            
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        })
        ;
    }

    private void resetGame() {
        ticTacGrid.reInitialize();
        hasWinner = false;
        winnerXO = "";
        turn = 1;
    }

    private void gameWon(String winnerXO) {
        if (winnerXO.equals("X")) {
            winsX += 1;
            System.out.println("----" + winsX + "------------- X wins ---------------------");
        } else if (winnerXO.equals("O")) {
            winsO += 1;
            System.out.println("----" + winsO + "------------- O wins ---------------------");
        }
        this.winnerXO = winnerXO;
        hasWinner = true;
    }

    private void onMouseClick(MouseEvent e) {
        TicTacTile click = ticTacGrid.coordinatesToBox(e.getX(), e.getY()); // box we clicked on

        if (click != null && !hasWinner) {
            click.setXO(turn);
            turn();
            checkWinner(click);
        }
    }

    private void checkWinner(TicTacTile playerxo) {
        // get the current x/o tile
        // check in every direction to see if its connected to another of its same type
        // if so carry on along that line to see if its connected to another
        // if so win, if not return 1
        // use the int returned to calculate matches in all directions in tangent

        int x = playerxo.x;
        int y = playerxo.y;
        String pxo = playerxo.xo;

//        treeSearch(false, x, y, 0, -1, 2, 0); //north
        if (treeSearch(false, x, y, 0, -1, 2, 0) == 2) { // north
            gameWon(pxo);
        }
        if (treeSearch(false, x, y, 0, 1, 2, 0) == 2) { // south
            gameWon(pxo);
        }
        if (treeSearch(true, x, y, -1, 0, 2, 0) == 2) { // east
            gameWon(pxo);
        }
        if (treeSearch(true, x, y, 1, 0, 2, 0) == 2) { // west
            gameWon(pxo);
        }
        if (treeSearch(null, x, y, 1, -1, 2, 0) == 2) { //north west
            gameWon(pxo);
        }
        if (treeSearch(null, x, y, 1, 1, 2, 0) == 2) { // south west
            gameWon(pxo);
        }
        if (treeSearch(null, x, y, -1, -1, 2, 0) == 2) { // north east
            gameWon(pxo);
        }
        if (treeSearch(null, x, y, -1, 1, 2, 0) == 2) { // south east
            gameWon(pxo);
        }

        int matches = 0; // number of matches that we've found in the direction we search

        if (x == 1) { // check the east and western side together
            matches += treeSearch(true, x, y, 1, 0, 2, 0);
            matches += treeSearch(true, x, y, -1, 0, 2, 0);
            if (matches == 2)
                gameWon(pxo);
        }
        matches = 0;

        if (y == 1) { // check the north and southern side together
            matches += treeSearch(false, x, y, 0, -1, 2, 0);
            matches += treeSearch(false, x, y, 0, 1, 2, 0);
            if (matches == 2)
                gameWon(pxo);
        }
        matches = 0;

        if (x == 1 && y == 1) { // check diagonal wins when we click the centre piece
            matches += treeSearch(null, x, y, -1, -1, 2, 0);
            matches += treeSearch(null, x, y, 1, 1, 2, 0);

            if (matches == 2)
                gameWon(pxo);

            matches = 0;
            matches += treeSearch(null, x, y, 1, -1, 2, 0);
            matches += treeSearch(null, x, y, -1, 1, 2, 0);

            if (matches == 2)
                gameWon(pxo);
        }
    }

    private int treeSearch(Boolean searchX, int vx, int vy, int mx, int my, int limitHigh, int limitLow) {
        int matches = 0;

        if (searchX == null) {
            if (vx + mx >= limitLow && vx + mx <= limitHigh && vy + my >= limitLow && vy + my <= limitHigh) {
                if (ticTacGrid.getTicTacTiles()[vx + mx][vy + my].xo == ticTacGrid.getTicTacTiles()[vx][vy].xo) {
                    matches++;

                    // move to the next square in the direction that were checking
                    vy = vy + my;
                    vx = vx + mx;
                    if (vx >= limitLow || vx <= limitHigh || vy >= limitLow || vy <= limitHigh) {
                        matches += treeSearch(null, vx, vy, mx, my, limitHigh, limitLow);
                    }
                    System.out.println("matches " + matches);
                }
            }
        } else if (searchX) {
            if (vx + mx >= limitLow && vx + mx <= limitHigh) {
                if (ticTacGrid.getTicTacTiles()[vx + mx][vy].xo == ticTacGrid.getTicTacTiles()[vx][vy].xo) {
                    matches++;

                    // move to the next square in the direction that were checking
                    vx = vx + mx;
                    if (vx >= limitLow || vx <= limitHigh) {
                        matches += treeSearch(true, vx, vy, mx, 0, limitHigh, limitLow);
                    }
                }
            }
        } else {
            // if (!searchX) - do y
            if (vy + my >= limitLow && vy + my <= limitHigh) {
                if (ticTacGrid.getTicTacTiles()[vx][vy + my].xo == ticTacGrid.getTicTacTiles()[vx][vy].xo) {
                    matches++;

                    // move to the next square in the direction that were checking
                    vy = vy + my;
                    if (vy >= limitLow || vy <= limitHigh) {
                        matches += treeSearch(false, vx, vy, 0, my, limitHigh, limitLow);
                    }
                    System.out.println("matches " + matches);
                }
            }
        }

        return matches;
    }

    private void turn() {
        if (turn == 0) {
            turn = 1;
        } else {
            turn = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (game) {
            // draw everything here
            ticTacGrid.draw(g);

            if (hasWinner) {
                g.drawString("Winner = " + winnerXO, 100, 100);
            }
        } else {
            // the game has a fade-in when you run it and global game = false
//            g.setColor(new Color(121, 42, 201, opacity));
            g.setColor(new Color(11, 11, 11, opacity));

            // todo insert logo fade-in here

            g.fillRect(0, 0, width, height);
            //System.out.println(opacity);
            try {
                Thread.sleep(18);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            opacity();
            //g.setColor(new Color(22,22,22,1));
        }

        repaint();
    }

    private void opacity() {
        if (opacity < 180)
            opacity++;
        else
            game = true;
    }

}
