package GUI;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import GUI.CostumJComponents.*;
import Managers.UserManager;
import ServerThreads.ClientThread;

public class ConsoleGUI extends JFrame {
    static JPanel p = new JPanel(null);
    static JTextArea consoleTextArea = new JTextArea();
    static JTextArea threadConnessiTextArea = new JTextArea();
    static costumJLabel consoleLabel = new costumJLabel("Console:",p,100,100,25,200);
    static costumJLabel threadConnessiLabel = new costumJLabel("Lista thread attualmente connessi:",p,250,50,25,-5);
    JLabel label = new JLabel("Console:");
    public ConsoleGUI(String title) {
        super(title);
        consoleTextArea.setEditable(false);
        threadConnessiTextArea.setEditable(false);

        JScrollPane consoleScrollPane = new JScrollPane(consoleTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        DefaultCaret caret = (DefaultCaret)consoleTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consoleScrollPane.setLocation(new Point(25,265));
        consoleScrollPane.setSize(1200,225);

        JScrollPane threadConnessiScrollPane = new JScrollPane(threadConnessiTextArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        caret = (DefaultCaret)threadConnessiTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
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
                                      + " - Nome: " + clientThread.getUser().getNome()
                                      + " - ");
        if (clientThread.getConnectedUser() == null){
            threadConnessiTextArea.append("Questo client ancora non sta scrivendo a nessuno");
        } else if (clientThread.getConnectedUser().equals("BROADCAST")){
            threadConnessiTextArea.append("Questo client sta scrivendo in broadcast");
        } else {
            threadConnessiTextArea.append("Questo client sta scrivendo a " + clientThread.getConnectedUser());
        }
        threadConnessiTextArea.append("\n");
        threadConnessiTextArea.update(threadConnessiTextArea.getGraphics());
    }
    public static synchronized void removeUserFromGUI(ClientThread clientThread){
        String[] tc = threadConnessiTextArea.getText().split("\n");
        ArrayList<String> threadConnessi = new ArrayList<>(Arrays.asList(tc));
        for (Iterator<String> it = threadConnessi.iterator(); it.hasNext(); ) {
            String userString = it.next();
            if (userString.contains(clientThread.getName())){
                it.remove();
            }
        }
        updateThreadConnessiGUI();
    }
    public static synchronized void updateThreadConnessiGUI(){
        threadConnessiTextArea.setText("");
        for (ClientThread i : UserManager.getInstance().getBroadcast()){
            addUserToLogGUI(i);
        }
    }
}
