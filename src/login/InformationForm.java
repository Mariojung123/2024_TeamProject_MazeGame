package login;

import maze.MazeGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

// 로그인 성공 시 개인 정보가 출력되는 GUI 화면
// LOGOUT 과 WITHDRAW 기능
// LOGOUT 클릭 시 LOGINFORM 화면으로 이동
// WITHDRAW 클릭 시 탈퇴 및 정보 삭제

public class InformationForm extends JDialog {

    private LoginForm owner;
    private UsersData users;
    private String userId;
    private JTextArea check;
    private JButton btnLogout;
    private JButton btnWithdraw;
    private JButton startGameBtn;
    private JButton checkRankingBtn;
    private JButton gameStoryBtn;

    public InformationForm(JFrame parent, String userId) {
        // Initialize components
        startGameBtn = new JButton("게임 시작");
        checkRankingBtn = new JButton("랭킹 확인");
        gameStoryBtn = new JButton("게임 스토리");

        // Set up the frame
        setTitle("User Information");
        setSize(400, 300);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add components to the panel with BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("User Information for: " + userId));
        panel.add(startGameBtn);
        panel.add(checkRankingBtn);
        panel.add(gameStoryBtn);
        add(panel);

        // Add action listeners for the buttons
        startGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeGame mazeGame = new MazeGame(10); // Create an instance of MazeGame with a size of 10
                mazeGame.startMazeGame(); // Call the startMazeGame method
                dispose(); // Close InformationForm
            }
        });

        checkRankingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement ranking check functionality here
                JOptionPane.showMessageDialog(InformationForm.this, "랭킹 확인 기능은 아직 구현되지 않았습니다.");
            }
        });

        gameStoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement game story functionality here
                JOptionPane.showMessageDialog(InformationForm.this, "게임 스토리 기능은 아직 구현되지 않았습니다.");
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    private void init() {
        Dimension btnsize = new Dimension(100, 25);

        check = new JTextArea(10, 20);
        check.setEditable(false);

        btnLogout = new JButton("로그아웃");
        btnLogout.setPreferredSize(btnsize);

        btnWithdraw = new JButton("탈퇴하기");
        btnWithdraw.setPreferredSize(btnsize);
    }

    private void setDisplay() {
        LineBorder lBorder = new LineBorder(Color.GRAY, 1);
        TitledBorder border = new TitledBorder(lBorder, "안녕하세요! 본인의 정보를 확인 할 수 있습니다");
        check.setBorder(border);

        JPanel southPanel = new JPanel();
        southPanel.add(btnLogout);
        southPanel.add(btnWithdraw);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(check), BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void addListners() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        btnWithdraw.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    // 예외처리 추가
                    String userId = InformationForm.this.userId;
                    if (userId != null && !userId.isEmpty()) {
                        // System.out.println("Withdraw button clicked: " + userId);
                        // User 객체를 생성하여 ID 가져오기
                        User userForWithdraw = new User(userId);
                        // withdraw 메서드 호출
                        users.withdraw(userForWithdraw.getId());

                        JOptionPane.showMessageDialog(InformationForm.this, "회원 정보가 삭제되었습니다" + "\n" + "다음에 또 만나요!", "BYE JAVA", JOptionPane.PLAIN_MESSAGE);
                        dispose();
                        owner.setVisible(true);
                    } else {
                        System.out.println("Withdraw button clicked: userId is null or empty");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnLogout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(InformationForm.this, "로그아웃 되었습니다" + "\n" + "다음에 또 만나요!","BYE JAVA",JOptionPane.PLAIN_MESSAGE);
                dispose();
                owner.setVisible(true);
            }
        });
    }

    public void setcheck(String userInfo) {
        check.setText(userInfo);
    }

    private void showFrame() {
        pack();
        setLocationRelativeTo(owner);								// loginForm이 있는 위치를 기준으로 위치를 조정함
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);	// 다이얼로그를 닫을 때 해당 다이얼로그만 닫히고 프로그램이 종료되지는 않음
        setResizable(false);					 								// 창 크기를 조절할 수 없도록 설정함
    }
}
