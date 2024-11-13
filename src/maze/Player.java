package maze;

import java.awt.event.KeyEvent;

public class Player {
    private int x, y;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public boolean move(int keyCode, Maze maze) {
        int newX = x;
        int newY = y;

        if (keyCode == KeyEvent.VK_UP && x > 0 && maze.getMaze()[x - 1][y] == 0) {
            newX--;
        } else if (keyCode == KeyEvent.VK_DOWN && x < maze.getSize() - 1 && maze.getMaze()[x + 1][y] == 0) {
            newX++;
        } else if (keyCode == KeyEvent.VK_LEFT && y > 0 && maze.getMaze()[x][y - 1] == 0) {
            newY--;
        } else if (keyCode == KeyEvent.VK_RIGHT && y < maze.getSize() - 1 && maze.getMaze()[x][y + 1] == 0) {
            newY++;
        }

        if (newX != x || newY != y) {
            x = newX;
            y = newY;
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
