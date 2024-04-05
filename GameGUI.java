import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JPanel {
    private Board board;
    private JButton[][] buttons;
    private ImageIcon bombIcon;
    private ImageIcon flagIcon;
    private JLabel timerLabel;
    private Timer gameTimer;
    private int minutesPassed;
    private int secondsPassed;

    public GameGUI() {
        this.board = new Board(8, 8);
        this.buttons = new JButton[board.getRows()][board.getCols()];
        bombIcon = resizeImageIcon(new ImageIcon(getClass().getResource("./img/bomb.png")), 40, 40);
        flagIcon = resizeImageIcon(new ImageIcon(getClass().getResource("./img/flag.png")), 40, 40);
        this.minutesPassed = 0;
        this.secondsPassed = 0;
        initialize();
        startTimer();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(board.getRows(), board.getCols()));

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
                boardPanel.add(button);
            }
        }

        timerLabel = new JLabel("00:00");
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(timerLabel, BorderLayout.NORTH);
        this.add(boardPanel, BorderLayout.CENTER);
    }

    private void startTimer() {
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
                if (secondsPassed == 60) {
                    secondsPassed = 0;
                    minutesPassed++;
                }
                String timeString = String.format("%02d:%02d", minutesPassed, secondsPassed);
                timerLabel.setText(timeString);
            }
        });
        gameTimer.start();
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
