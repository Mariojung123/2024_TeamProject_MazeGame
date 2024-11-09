package maze;

import maze.level3.level3_1;

import javax.swing.*;

public class MazeForm extends JFrame {
    public MazeForm(String difficulty) {
        setTitle("Maze Game - Difficulty: " + difficulty);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MazePanel mazePanel;
        switch (difficulty) {
            case "Level 3_1":
                mazePanel = new level3_1();
                break;
            // Add cases for other levels as needed
            default:
                mazePanel = new level3_1(); // Default to level 3_1 for now
                break;
        }

        add(mazePanel);
        setVisible(true);
    }
}