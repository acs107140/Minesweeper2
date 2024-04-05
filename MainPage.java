import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPage extends JPanel{
    
    private JPanel mainPanel;
    private GameGUI gameGUI;
    private RuleGUI ruleGUI;

    public MainPage() {
        initialize();
    }

    private void initialize() {
        this.setLayout(new CardLayout());

        mainPanel = createMainPanel();
        this.add(mainPanel, "MAIN");

        gameGUI = new GameGUI();
        this.add(gameGUI, "GAME");

        ruleGUI = new RuleGUI();
        this.add(ruleGUI, "RULE");
        
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 載入背景圖片
                ImageIcon backgroundImage = new ImageIcon("./img/background.png");
                // 繪製背景圖片
                g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
    
        JLabel titleLabel = new JLabel("MineSweeper");
        titleLabel.setPreferredSize(new Dimension(90, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.darkGray);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(titleLabel, gbc);
    
        JButton startButton = createButton("StartGame", e -> {
            showGameGUI();
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(startButton, gbc);
    
        JButton rulesButton = createButton("Rules", e -> {
            showRules();
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(rulesButton, gbc);
    
        JButton editMapButton = createButton("EditMap", e -> {
            editMap();
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(editMapButton, gbc);
    
        JButton leaderboardButton = createButton("LeaderBoard", e -> {
            showLeaderboard();
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(leaderboardButton, gbc);
    
        return panel;
    }
    

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        setCustomButtonStyle(button);
        button.addActionListener(actionListener);
        return button;
    }

    private void setCustomButtonStyle(JButton button) {
        button.setForeground(new Color(200, 200, 200));
        button.setBackground(Color.darkGray);
        Font font = new Font("Serif", Font.PLAIN, 14);
        button.setFont(font);
        Dimension buttonSize = new Dimension(200, 60);
        button.setPreferredSize(buttonSize);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(30, 30, 30));
                button.setBackground(Color.lightGray);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(200, 200, 200));
                button.setBackground(Color.darkGray);
            }
        });
    }

    private void showGameGUI() {
        CardLayout cardLayout = (CardLayout) this.getLayout();
        cardLayout.show(this, "GAME");
    }

    private void showRules() {
        CardLayout cardLayout = (CardLayout) this.getLayout();
        cardLayout.show(this, "RULE");
    }

    private void showLeaderboard() {
        JOptionPane.showMessageDialog(this, "排行榜尚未實現，敬請期待！", "排行榜", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editMap() {
        JOptionPane.showMessageDialog(this, "編輯地圖尚未實現，敬請期待！", "排行榜", JOptionPane.INFORMATION_MESSAGE);
    }
}