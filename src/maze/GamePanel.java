package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    private Maze maze;
    private Player player;
    private boolean[][] visited; // 지나간 자리를 기록하는 배열
    private BufferedImage blockImage;  // 벽 이미지
    private BufferedImage pathImage;   // 통로 이미지
    private BufferedImage playerImage; // 플레이어 이미지
    private BufferedImage flagImage;   // 목적지 이미지
    private int flagX; // flag의 x좌표
    private int flagY; // flag의 y좌표

    public GamePanel(Maze maze, Player player, int flagX, int flagY) {
        this.maze = maze;
        this.player = player;
        this.flagX = flagX;
        this.flagY = flagY;
        this.visited = new boolean[maze.getSize()][maze.getSize()]; // 미로 크기만큼 방문 배열 초기화
    }

    public void setBlockImage(BufferedImage blockImage) {
        this.blockImage = blockImage;
    }

    public void setPathImage(BufferedImage pathImage) {
        this.pathImage = pathImage;
    }

    public void setPlayerImage(BufferedImage playerImage) {
        this.playerImage = playerImage;
    }

    public void setFlagImage(BufferedImage flagImage) {
        this.flagImage = flagImage;
    }

    public void updatePlayerPosition(int x, int y) {
        player.setPosition(x, y);
        visited[player.getX()][player.getY()] = true; // 현재 위치 방문 기록
        repaint(); // 패널 업데이트
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] mazeArray = maze.getMaze();
        int cellSize = Math.min(getWidth(), getHeight()) / maze.getSize();

        // 중앙에 위치하도록 여백 설정
        int offsetX = (getWidth() - (cellSize * maze.getSize())) / 2;
        int offsetY = (getHeight() - (cellSize * maze.getSize())) / 2;

        // 지나간 자리를 계속 볼 수 있도록 하기
        for (int i = 0; i < maze.getSize(); i++) {
            for (int j = 0; j < maze.getSize(); j++) {
                if (visited[i][j]) {
                    g.setColor(Color.LIGHT_GRAY); // 지나간 자리 색상
                    g.fillRect(offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize);
                }
            }
        }

        // 현재 플레이어 주변 1칸만 보이도록 하기
        for (int i = player.getX() - 1; i <= player.getX() + 1; i++) {
            for (int j = player.getY() - 1; j <= player.getY() + 1; j++) {
                // 범위 체크
                if (i >= 0 && i < maze.getSize() && j >= 0 && j < maze.getSize()) {
                    if (mazeArray[i][j] == 1) {
                        g.drawImage(blockImage, offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize, null); // 벽 이미지 그리기
                    } else {
                        g.drawImage(pathImage, offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize, null); // 통로 이미지 그리기
                    }
                }
            }
        }

        // 플레이어 표시
        g.drawImage(playerImage, offsetX + player.getY() * cellSize, offsetY + player.getX() * cellSize, cellSize, cellSize, null);

        // 목적지 표시 (플레이어가 도착할 때까지 보이지 않도록)
        if (Math.abs(player.getX() - flagX) <= 1 && Math.abs(player.getY() - flagY) <= 1) {
            // 플레이어가 flag 근처에 있을 때만 flag 표시
            g.drawImage(flagImage, offsetX + flagY * cellSize, offsetY + flagX * cellSize, cellSize, cellSize, null);
        }
    }
}
