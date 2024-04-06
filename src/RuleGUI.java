import javax.swing.*;
import java.awt.*;

public class RuleGUI extends JPanel {
    private String statement = "玩法規則：\n" +
            "1. 目標是找出所有地雷的位置，不觸發地雷。\n" +
            "2. 每個非地雷的格子會顯示周圍的地雷數量。\n" +
            "3. 右鍵標記地雷，左鍵揭開格子。\n" +
            "4. 當所有非地雷的格子都被揭開，遊戲勝利。";
    private ImageIcon backgroundIcon;
    private Controller controller;

    public RuleGUI(Controller controller) {
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        // 設置布局
        this.setLayout(new GridBagLayout());
        backgroundIcon = new ImageIcon(getClass().getResource("./img/background.png"));
        // 添加描述文字
        JTextArea ruleText = new JTextArea(statement);
        ruleText.setEditable(false);
        ruleText.setFont(new Font("Microsoft JhengHei", Font.BOLD, 16)); // 使用支持中文的字體
        ruleText.setForeground(Color.BLACK);
        ruleText.setBackground(new Color(240, 240, 240)); // 設置背景色
        ruleText.setMargin(new Insets(20, 20, 20, 20)); // 設置邊緣空間
        ruleText.setAlignmentX(Component.CENTER_ALIGNMENT); // 設置為置中

        // 使用 GridBagConstraints 設定 JTextArea 在布局中的位置
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(ruleText, gbc);

        // 添加返回主選單的按鈕
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Microsoft JhengHei", Font.BOLD, 14)); // 設定字體和大小
        backButton.setForeground(new Color(200, 200, 200)); // 設定文字顏色
        backButton.setBackground(Color.darkGray); // 設定按鈕背景顏色
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // 設定邊框
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setForeground(new Color(30, 30, 30));
                backButton.setBackground(Color.lightGray);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setForeground(new Color(200, 200, 200));
                backButton.setBackground(Color.darkGray);
            }
        });
        backButton.addActionListener(e -> {
            controller.switchPanel("MAIN");
        });

        // 使用 GridBagConstraints 設定 JButton 在布局中的位置
        gbc.gridy = 1;
        gbc.weighty = 0.2;
        this.add(backButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
