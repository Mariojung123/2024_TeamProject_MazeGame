package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class MazePanel extends JPanel {
    protected int[][] maze;
    protected int playerX, playerY;

    public MazePanel(int[][] maze) {
        this.maze = maze;
        this.playerX = 1; // Starting position
        this.playerY = 1;
        setPreferredSize(new Dimension(600, 400));
        setFocusable(true);
        addKeyListener(new MazeKeyListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {
                if (maze[y][x] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * 20, y * 20, 20, 20);
                } else if (x == playerX && y == playerY) {
                    g.setColor(Color.RED);
                    g.fillRect(x * 20, y * 20, 20, 20);
                }
            }
        }
    }

    private class MazeKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP && maze[playerY - 1][playerX] == 0) {
                playerY--;
            } else if (key == KeyEvent.VK_DOWN && maze[playerY + 1][playerX] == 0) {
                playerY++;
            } else if (key == KeyEvent.VK_LEFT && maze[playerY][playerX - 1] == 0) {
                playerX--;
            } else if (key == KeyEvent.VK_RIGHT && maze[playerY][playerX + 1] == 0) {
                playerX++;
            }
            repaint();
        }
    }
}