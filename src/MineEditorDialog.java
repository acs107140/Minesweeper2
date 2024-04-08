import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MineEditorDialog extends JDialog {
    private Controller controller;
    private JButton[][] buttons;
    private JLabel remainingMinesLabel;
    private int remainingMines;
    private int rows;
    private int cols;

    public MineEditorDialog(Controller controller) {
        this.controller = controller;
        this.rows = 8; // 預設地圖行數
        this.cols = 8; // 預設地圖列數
        this.remainingMines = rows * cols / 6; // 地雷數量為總格子數的1/6
        initialize();
    }

    private void initialize() {
        this.setTitle("Mine Editor");
        this.setLayout(new BorderLayout());

        JPanel minePanel = new JPanel(new GridLayout(rows, cols));
        buttons = new JButton[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(40, 40));
                button.setFocusPainted(false);
                int row = i;
                int col = j;

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toggleMine(button, row, col);
                    }
                });

                buttons[i][j] = button;
                minePanel.add(button);
            }
        }
        updateRemainingMinesLabel();

        JPanel controlPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMapToFile();
            }
        });
        controlPanel.add(saveButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        controlPanel.add(cancelButton);

        this.add(minePanel, BorderLayout.CENTER);
        this.add(remainingMinesLabel, BorderLayout.NORTH);
        this.add(controlPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    // 切換地雷狀態
    private void toggleMine(JButton button, int row, int col) {
        if (button.getText().equals("M")) {
            button.setText("");
            remainingMines++;
        } else {
            button.setText("M");
            remainingMines--;
        }
        updateRemainingMinesLabel(); // 更新剩餘地雷數標籤
    }

    // 更新剩餘地雷數標籤
    private void updateRemainingMinesLabel() {
        if (remainingMinesLabel == null) {
            remainingMinesLabel = new JLabel("剩餘地雷數： " + remainingMines);
        } else {
            remainingMinesLabel.setText("剩餘地雷數： " + remainingMines);
        }
    }

    // 將地圖存儲為文件
    private void saveMapToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("保存地圖文件");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        if (buttons[i][j].getText().equals("M")) {
                            writer.write((i + 1) + " " + (char) ('A' + j) + System.lineSeparator());
                        }
                    }
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "地圖保存成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "保存地圖時出錯： " + e.getMessage(), "錯誤", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
