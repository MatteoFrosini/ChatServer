package GUI;

import javax.swing.*;
import java.awt.*;

public class ConsoleGui extends JFrame {
    JPanel p = new JPanel(null);
    public ConsoleGui(String title) throws HeadlessException {
        super(title);
        setContentPane(p);
        setSize(300,450);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}