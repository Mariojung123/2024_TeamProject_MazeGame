package maze;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MazeGame extends JFrame {
    private Maze maze;
    private Player player;
    private GamePanel gamePanel;
    private boolean reachedDestination = false;
    private int flagX;
    private int flagY;

    public MazeGame(int size) {
        maze = new Maze(size);
        player = new Player(0, 0);
        Random random = new Random();

        // 랜덤한 flag 위치 생성
        do {
            flagX = random.nextInt(size);
            flagY = random.nextInt(size);
        } while (maze.getMaze()[flagX][flagY] == 1 || (flagX == 0 && flagY == 0)); // 벽이나 시작 위치에 flag가 생성되지 않도록

        setTitle("미로 찾기 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 패널 설정
        gamePanel = new GamePanel(maze, player, flagX, flagY);
        add(gamePanel);

        loadImages(); // 이미지를 로드하는 위치를 패널 초기화 후로 이동

        pack(); // 프레임 크기 조정
        setResizable(false); // 크기 조정 불가

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePlayer(e.getKeyCode());
            }
        });
    }

    private void loadImages() {
        gamePanel.setBlockImage(ImageLoader.loadImage("images/block.png"));
        gamePanel.setPathImage(ImageLoader.loadImage("images/path.png"));
        gamePanel.setPlayerImage(ImageLoader.loadImage("images/player.png"));
        gamePanel.setFlagImage(ImageLoader.loadImage("images/flag.png"));
    }

    private void movePlayer(int keyCode) {
        if (reachedDestination) return; // 도착하면 더 이상 이동 불가

        if (player.move(keyCode, maze)) {
            if (player.getX() == flagX && player.getY() == flagY) {
                reachedDestination = true;
                JOptionPane.showMessageDialog(this, "목적지에 도착했습니다!");
                System.exit(0); // 프로그램 종료
            }
            gamePanel.updatePlayerPosition(player.getX(), player.getY());
        }
    }

    public void startMazeGame() {
        String input = JOptionPane.showInputDialog("미로의 사이즈를 입력하세요:");
        int size = Integer.parseInt(input);
        MazeGame game = new MazeGame(size);
        game.setSize(600, 600); // 프레임 크기 설정
        game.setVisible(true);
    }

}
