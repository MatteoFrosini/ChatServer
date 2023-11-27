package GUI;
import javax.swing.*;
import java.awt.*;

public class ConsoleGUI extends JFrame {
    JPanel p = new JPanel(null);
    static JTextArea textArea = new JTextArea();
    JLabel label = new JLabel("Console:");
    public ConsoleGUI(String title) {
        super(title);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setLocation(new Point(25,265));
        scrollPane.setSize(1200,245);
        label.setLocation(25,200);
        label.setSize(100,100);
        p.add(scrollPane);
        p.add(label);
        setContentPane(p);
        setSize(1250,550);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static synchronized void logGUI(String log) {
        textArea.append(log);
        textArea.updateUI();
    }
}
