package GUI;
import javax.swing.*;
import java.awt.*;

public class ConsoleGUI extends JFrame {
    JPanel p = new JPanel(null);
    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane();
    JLabel label = new JLabel("mi ammazzo");
    public ConsoleGUI(String title) {
        super(title);
        textArea.setEditable(false);
        textArea.setLocation(0,0);
        textArea.setSize(200,200);
        scrollPane.setLocation(new Point(200,200));
        scrollPane.setSize(200,200);
        fillArea(50);
        scrollPane.createVerticalScrollBar();
        //scrollPane.setViewport(new JViewport().setVie(textArea));
        label.setLocation(100,100);
        label.setSize(100,100);
        p.add(scrollPane);
        p.add(label);
        setContentPane(p);
        setSize(1500,750);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void fillArea(int i) {
        for (int j = 0; j < i; j++) {
            textArea.setText(textArea.getText()+j+"\n");
            textArea.updateUI();
        }
    }
}
