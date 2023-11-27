package GUI;
import javax.swing.*;
import java.awt.*;
import GUI.CostumJComponents.*;
import ServerThreads.ClientThread;

public class ConsoleGUI extends JFrame {
    static JPanel p = new JPanel(null);
    static JTextArea consoleTextArea = new JTextArea();
    static JTextArea threadConnessiTextArea = new JTextArea();
    static costumJLabel consoleLabel = new costumJLabel("Console:",p,100,100,25,200);
    static costumJLabel threadConnessi = new costumJLabel("Lista thread attualmente connessi:",p,200,50,25,-5);
    JLabel label = new JLabel("Console:");
    public ConsoleGUI(String title) {
        super(title);
        consoleTextArea.setEditable(false);
        threadConnessiTextArea.setEditable(false);

        JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        consoleScrollPane.setLocation(new Point(25,265));
        consoleScrollPane.setSize(1200,225);

        JScrollPane threadConnessiScrollPane = new JScrollPane(threadConnessiTextArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        threadConnessiScrollPane.setLocation(new Point(25,30));
        threadConnessiScrollPane.setSize(1200,195);

        p.add(threadConnessiScrollPane);
        p.add(consoleScrollPane);

        setContentPane(p);
        setSize(1250,550);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static synchronized void logGUI(String log) {
        consoleTextArea.append(log+"\n");
        consoleTextArea.update(consoleTextArea.getGraphics());
    }
    public static synchronized void addUserToLogGUI(ClientThread clientThread){
        threadConnessiTextArea.append("Nome Thread: " + clientThread.getName()
                                      + " - Indirizzo ip e porta: " + clientThread.getSocket().getRemoteSocketAddress().toString()
                                      + " - Nome:" + clientThread.getUser().getNome()
                                      + " - ");
        if (clientThread.getConnectedUser() == null){
            threadConnessiTextArea.append("Questo client ancora non sta scrivendo a nessuno");
        } else if (clientThread.getConnectedUser().equals("BROADCAST")){
            threadConnessiTextArea.append("Questo client sta scrivendo in broadcast");
        } else {
            threadConnessiTextArea.append("Questo client sta scrivendo a " + clientThread.getConnectedUser());
        }
        threadConnessiTextArea.append("\n");
    }
}
