package GUI;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Managers.UserManager;
import ServerThreads.ClientThread;

public class ConsoleGUI extends JFrame {
    static JTextArea consoleTextArea = new JTextArea();
    static JTextArea threadConnessiTextArea = new JTextArea();
    static JLabel consoleLabel = new JLabel("Console:");
    static JLabel threadConnessiLabel = new JLabel("Lista thread attualmente connessi:");

    public ConsoleGUI(String title) {
        super(title);
        setLayout(new GridLayout(2, 1,5,5));

        // CONSOLE
        JPanel JP_console = new JPanel(new BorderLayout(5, 5));
        JP_console.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        DefaultCaret caret_consoleTextArea = (DefaultCaret) consoleTextArea.getCaret();
        caret_consoleTextArea.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consoleTextArea.setEditable(false);
        consoleTextArea.setAutoscrolls(true);
        JP_console.add(consoleLabel, BorderLayout.PAGE_START);
        JP_console.add(new JScrollPane(consoleTextArea), BorderLayout.CENTER);
        // CONSOLE END

        // THREAD CONNESSI
        JPanel JP_threadConnessi = new JPanel(new BorderLayout(5, 5));
        JP_threadConnessi.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        DefaultCaret caret_threadConnessiTextArea = (DefaultCaret) threadConnessiTextArea.getCaret();
        caret_threadConnessiTextArea.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        threadConnessiTextArea.setEditable(false);
        threadConnessiTextArea.setAutoscrolls(true);
        JP_threadConnessi.add(threadConnessiLabel, BorderLayout.PAGE_START);
        JP_threadConnessi.add(new JScrollPane(threadConnessiTextArea), BorderLayout.CENTER);
        // THREAD CONNESSI END

        add(JP_threadConnessi);
        add(JP_console);

        setSize(1250, 550);
        setResizable(true);
        setMinimumSize(new Dimension(300,300));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static synchronized void logGUI(String log) {
        consoleTextArea.setText(consoleTextArea.getText() + log + "\n");
        consoleTextArea.update(consoleTextArea.getGraphics());
    }

    public static synchronized void addUserToLogGUI(ClientThread clientThread) {
        threadConnessiTextArea.setText(threadConnessiTextArea.getText() + "Nome Thread: " + clientThread.getName()
                + " - Indirizzo ip e porta: " + clientThread.getSocket().getRemoteSocketAddress().toString()
                + " - Nome: " + clientThread.getUser().getNome()
                + " - ");
        if (clientThread.getConnectedUser() == null) {
            threadConnessiTextArea.setText(threadConnessiTextArea.getText() + "Questo client ancora non sta scrivendo a nessuno");
        } else if (clientThread.getConnectedUser().equals("BROADCAST")) {
            threadConnessiTextArea.setText(threadConnessiTextArea.getText() + "Questo client sta scrivendo in broadcast");
        } else {
            threadConnessiTextArea.setText(threadConnessiTextArea.getText() + "Questo client sta scrivendo a " + clientThread.getConnectedUser());
        }
        threadConnessiTextArea.setText(threadConnessiTextArea.getText() + "\n");
        threadConnessiTextArea.update(threadConnessiTextArea.getGraphics());
    }

    public static synchronized void removeUserFromGUI(ClientThread clientThread) {
        String[] tc = threadConnessiTextArea.getText().split("\n");
        ArrayList<String> threadConnessi = new ArrayList<>(Arrays.asList(tc));
        for (Iterator<String> it = threadConnessi.iterator(); it.hasNext();) {
            String userString = it.next();
            if (userString.contains(clientThread.getName())) {
                it.remove();
            }
        }
        updateThreadConnessiGUI();
    }

    public static synchronized void updateThreadConnessiGUI() {
        threadConnessiTextArea.setText("");
        for (ClientThread i : UserManager.getInstance().getBroadcast()) {
            addUserToLogGUI(i);
        }
    }
}
