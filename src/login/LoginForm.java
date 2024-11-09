package login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// ID 와 PW 를 입력해서 로그인 하는 화면
// 위 정보 입력 후 로그인 OR 회원가입 
// 중복 ID 거를 수 있어야 함 

public class LoginForm extends JFrame {

    private UsersData users;
    private JLabel idLabel;
    private JLabel pwLabel;
    private JTextField idTxt;
    private JPasswordField pwTxt;
    private JButton logBtn;
    private JButton joinBtn;
    private LayoutManager flowLeft;

    public LoginForm() {
        users = new UsersData();
        init();
        setDisplay();	// UI 화면을 설정하는 메서드
        addListners();	// 리스너를 추가하는 메서드
        showFrame();	// 프레임을 화면에 표시하는 메서드
    }

    public void init() {
        // 사이즈 통일
        Dimension labelSize= new Dimension(80, 30);
        int txtSize = 10;
        Dimension btnSize = new Dimension(100, 25);

        idLabel = new JLabel("아이디");
        idLabel.setPreferredSize(labelSize);
        pwLabel = new JLabel("암호");
        pwLabel.setPreferredSize(labelSize);

        idTxt = new JTextField(txtSize);
        pwTxt = new JPasswordField(txtSize);

        logBtn = new JButton("로그인");
        logBtn.setPreferredSize(btnSize);
        joinBtn = new JButton("회원가입");
        joinBtn.setPreferredSize(btnSize);

        flowLeft = new FlowLayout(FlowLayout.LEFT);
    }

    public UsersData getUsers() {
        return users;
    }

    public String getidTxt() {
        return idTxt.getText();
    }

    public void setDisplay() {
        // 컴포넌트를 왼쪽 정렬로 배치
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);

        JPanel northPanel = new JPanel(new GridLayout(0,1));

        JPanel idPanel = new JPanel(flowLeft);
        idPanel.add(idLabel);
        idPanel.add(idTxt);

        JPanel pwPanel = new JPanel(flowLeft);
        pwPanel.add(pwLabel);
        pwPanel.add(pwTxt);

        northPanel.add(idPanel);
        northPanel.add(pwPanel);

        JPanel southPanel = new JPanel();
        southPanel.add(logBtn);
        southPanel.add(joinBtn);

        northPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
        southPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

    }

    public void addListners() {
        // JOIN button 눌렀을때
        joinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new JoinForm(LoginForm.this);
                idTxt.setText("");
                pwTxt.setText("");
            }
        });
        // LOGIN button 눌렀을때

        logBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ID 입력하지 않았을 경우
                if (idTxt.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginForm.this, "아이디를 입력하세요","JAVA LOGIN",JOptionPane.WARNING_MESSAGE);
                    // 이미 존재하는 ID일 경우
                } else if (users.contains(new User(idTxt.getText()))) {
                    // PW 입력하지 않았을 경우
                    if(String.valueOf(pwTxt.getPassword()).isEmpty()) {
                        JOptionPane.showMessageDialog(LoginForm.this, "암호를 입력하세요","JAVA LOGIN",JOptionPane.WARNING_MESSAGE);
                        // PW 틀렸을 경우
                    } else if (!users.getUser(idTxt.getText()).getPw().equals(String.valueOf(pwTxt.getPassword()))) {
                        JOptionPane.showConfirmDialog(LoginForm.this, "암호가 일치하지 않습니다","RETRY", JOptionPane.WARNING_MESSAGE);
                    } else {
                        // 예시 코드, 실제 호출 부분에서 userId 값을 전달해야 함
                        InformationForm informationForm = new InformationForm(LoginForm.this, "사용자ID값");

                        //InformationForm informationForm = new InformationForm(LoginForm.this, title);
                        informationForm.setcheck(users.getUser(idTxt.getText()).toString());
                        setVisible(false);
                        informationForm.setVisible(true);
                        idTxt.setText("");
                        pwTxt.setText("");
                    }
                    // 존재하지 않는 ID일 경우
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "존재하지 않는 ID 입니다", "JAVA LOGIN", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int choice = JOptionPane.showConfirmDialog(
                        LoginForm.this, "다음에 또 만나요!", "BYE JAVA", JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

    }
    public void showFrame() {
        setTitle("Welcome to JAVA");
        pack(); 										// 프레임의 크기를 컨텐츠에 맞게 조정
//		setLocale(null);								// 시스템의 기본 로케일로 설정된다는 의미, 이 메서드는 주로 다국어 지원을 고려하여 프로그램 개발 될 때 사용 
        setLocationRelativeTo(null);					// 프레임을 화면 중앙에 위치 		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);	// 프레임이 닫힐 때 아무 동작도 하지 않도록 설정
        setResizable(false);							// 프레임의 크기 고정
        setVisible(true);
    }
} 