import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;

public class GameGUI extends JPanel {
    private Controller controller;
    private Board board;
    private JButton[][] buttons;
    private ImageIcon bombIcon;
    private ImageIcon flagIcon;
    private JLabel timerLabel;
    private Timer gameTimer;
    private int secondsPassed;
    private JPanel gamePanel;

    public GameGUI(Controller controller) {
        this.controller = controller;
        this.board = new Board(8, 8);
        this.buttons = new JButton[board.getRows()][board.getCols()];
        bombIcon = resizeImageIcon(new ImageIcon(getClass().getResource("./img/bomb.png")), 40, 40);
        flagIcon = resizeImageIcon(new ImageIcon(getClass().getResource("./img/flag.png")), 40, 40);
        this.secondsPassed = 0;
        initialize();
        startTimer();
    }

    private void initialize() {
        this.setLayout(new GridBagLayout()); // 使用GridBagLayout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        timerLabel = new JLabel("000");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setForeground(Color.RED); // 設置文字顏色為紅色
        timerLabel.setOpaque(true); // 設置為不透明以顯示背景色
        timerLabel.setBackground(Color.BLACK); // 設置背景色為黑色
        timerLabel.setPreferredSize(new Dimension(70, 40));
        // 設置特殊的數位顯示器字體
        try {
            Font digitalFont = Font.createFont(Font.TRUETYPE_FONT, new File("./font/timerFont.ttf")).deriveFont(48f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(digitalFont);
    
            // 使用三個單獨的 JLabel 來顯示時間
            timerLabel = new JLabel();
            timerLabel.setLayout(new GridLayout(1, 3));
            timerLabel.setBackground(Color.BLACK);
            timerLabel.setOpaque(true);
    
            JLabel digit1 = new JLabel("0");
            digit1.setForeground(Color.RED);
            digit1.setHorizontalAlignment(SwingConstants.RIGHT);
            digit1.setFont(digitalFont);
            timerLabel.add(digit1);
    
            JLabel digit2 = new JLabel("0");
            digit2.setForeground(Color.RED);
            digit2.setHorizontalAlignment(SwingConstants.RIGHT);
            digit2.setFont(digitalFont);
            timerLabel.add(digit2);
    
            JLabel digit3 = new JLabel("0");
            digit3.setForeground(Color.RED);
            digit3.setHorizontalAlignment(SwingConstants.RIGHT);
            digit3.setFont(digitalFont);
            timerLabel.add(digit3);
    
            // 設置特殊的數位顯示器字體
            FontMetrics fm = digit1.getFontMetrics(digitalFont);
            int width = fm.charWidth('0');
            int height = fm.getHeight();
            timerLabel.setPreferredSize(new Dimension(width * 3, height));
    
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        this.add(timerLabel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gamePanel = new JPanel(); // 初始化GamePanel
        gamePanel.setLayout(new GridLayout(board.getRows(), board.getCols()));
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40));
                button.setFocusPainted(false);
                int row = i;
                int col = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(row, col);
                    }
                });

                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        if (SwingUtilities.isRightMouseButton(evt)) {
                            handleRightClick(row, col);
                        }
                    }
                });

                buttons[i][j] = button;
                gamePanel.add(button); // 將button添加到GamePanel
            }
        }
        this.add(gamePanel, gbc); // 將GamePanel添加到主面板

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
        gbc.gridy = 2;
        gbc.weighty = 0.2;
        this.add(backButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 載入背景圖片
        ImageIcon backgroundImage = new ImageIcon("./img/background.png");
        // 繪製背景圖片
        g.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private void startTimer() {
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
                String timeString = String.format("%03d", secondsPassed);
                updateTimerLabel(timeString); // 更新 timerLabel 的文字
            }
        });
        gameTimer.start();
    }
    
    private void updateTimerLabel(String timeString) {
        String digit1 = timeString.substring(0, 1);
        String digit2 = timeString.substring(1, 2);
        String digit3 = timeString.substring(2, 3);
    
        ((JLabel) timerLabel.getComponent(0)).setText(digit1);
        ((JLabel) timerLabel.getComponent(1)).setText(digit2);
        ((JLabel) timerLabel.getComponent(2)).setText(digit3);
    }

    private void handleButtonClick(int row, int col) {
        if (!board.isRevealed(row, col)) {
            board.revealCell(row, col);
            updateButtons();
            if (board.isGameOver()) {
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Game Over!");
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.dispose();
            }
        }
    }

    private void handleRightClick(int row, int col) {
        if (!board.isRevealed(row, col)) {
            board.toggleFlag(row, col);
            updateButtons();
        }
    }

    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private void updateButtons() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                JButton button = buttons[i][j];
                if (board.isRevealed(i, j)) {
                    if (board.isMine(i, j)) {
                        button.setIcon(bombIcon);
                    } else {
                        int surroundingMines = board.getSurroundingMinesCount(i, j);
                        if (surroundingMines > 0) {
                            button.setText(String.valueOf(surroundingMines));
                        } else {
                            button.setText("");
                        }
                    }
                    button.setEnabled(false);
                } else {
                    if (board.isFlagged(i, j)) {
                        button.setIcon(flagIcon);
                    } else {
                        button.setIcon(null);
                    }
                    button.setEnabled(true);
                }
            }
        }
    }
}