package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class TicTacTile extends JComponent {
    public int x, y, width;
    public String xo = "";

    /*
        Color variables.
            colorIndex the index of the current color in the LinkedList.
            color is the current color of the box.
            colorList is a list of all the colors of the box. List allows more to be added dynamically.
     */
    int colorIndex = 0;
    Color color;
    // default colors
    Color[] colors = {
            Color.WHITE, Color.BLACK, Color.RED, Color.GREEN, Color.PINK,
            Color.ORANGE, Color.YELLOW, Color.MAGENTA
    };
    LinkedList<Color> colorList = new LinkedList<>();

    int neighbourCount;

    public TicTacTile(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.neighbourCount = 0;

        for (int i = 0; i < colors.length; i++) {
            colorList.add(colors[i]);
        }
        // white to start
        color = colorList.get(colorIndex);

        setBounds(x*width, y*width, width, width);
        setSize(width, width);
        setPreferredSize(new Dimension(width, width));
        setMaximumSize(new Dimension(width, width));
        //addMouseListener(mouseListener);
    }

    @Override
    public boolean contains(int x, int y) {
        super.contains(x,y);
        int minX, maxX, minY, maxY;



        minX = this.x*width;
        maxX = this.x*width+width;
        minY = this.y*width;
        maxY = this.y*width+width;
//        System.out.println("width = " + width);
//
//        System.out.println("minX = " + minX);
//        System.out.println("maxX = " + maxX);
//        System.out.println("minY = " + minY);
//        System.out.println("maxY = " + maxY);

        if((x >= minX && x <= maxX) && (y >= minY && y <= maxY)) {
            return true;
        } else {
            return false;
        }
    }

    public void setXO(int side) {
        // 0 = o, 1 = x
        if (side == 0) {
            this.xo = "X";
        } else {
            this.xo = "O";
        }

        System.out.println("XO = " + xo);
    }

    public String getXO() {
        return xo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (color == new Color(10, 150, 10)) {
            color = Color.RED;
        } else {
            this.color = color;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
