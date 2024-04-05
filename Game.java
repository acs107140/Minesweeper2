import javax.swing.*;
import java.awt.*;

public class Game {
    private JFrame mainFrame;
    private JPanel displayPanel;
    public Game(){
        initialize();
    };

    private void initialize(){
        mainFrame = new JFrame("Minesweeper Game");
        mainFrame.setSize(900, 450);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayPanel = new MainPage();
        mainFrame.add(displayPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    };
}
