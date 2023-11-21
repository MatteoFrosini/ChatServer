package GUI;

import javax.swing.*;
import java.awt.*;

public class ConsoleGui extends JFrame {
    public static JLabel log = new JLabel("log");
    public ConsoleGui(String title) throws HeadlessException {
        super(title);
        log.setLocation(0,0);
        add(log);
        setSize(1050,500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}