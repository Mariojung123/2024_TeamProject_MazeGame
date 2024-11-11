package maze;

import login.InformationForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectMap extends JDialog {
    private String[] difficulties = {"Level 3_1", "Level 3_2", "Level 3_3", "Level 2_1", "Level 2_2", "Level 2_3", "Level 1_1", "Level 1_2", "Level 1_3"};
    private JButton btnBack;

    public SelectMap(InformationForm parent) {
        super(parent, "Select Difficulty", true);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        for (String difficulty : difficulties) {
            JButton button = new JButton(difficulty);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new MazeForm(difficulty); // Open MazeForm with selected difficulty
                    dispose(); // Close SelectMap
                }
            });
            gridPanel.add(button);
        }

        btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close SelectMap and return to InformationForm
            }
        });

        JPanel southPanel = new JPanel();
        southPanel.add(btnBack);

        add(gridPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setSize(300, 200);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}