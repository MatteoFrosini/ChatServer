package GUI;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Constants.Constants;
import Managers.ChatHandlers.BrodcastChatHandler;
import Managers.UserManager;
import ServerThreads.ClientThread;
/**
 * Questa classe gestisce la chat della gui
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *     <li>
 *        {@link #ConsoleGUI(String)}
 *     </li>
 *     <li>
 *        {@link #updateThreadConnessiGUI()}
 *     </li>
 *     <li>
 *        {@link #removeUserFromGUI(ClientThread)}
 *     </li>
 *     <li>
 *        {@link #addUserToLogGUI(ClientThread)}
 *     </li>
 *     <li>
 *        {@link #logGUI(String)}
 *     </li>
 * </ul>
 * @author Matteo Frosini */
public class ConsoleGUI extends JFrame {
    private static UserManager userManager = UserManager.getInstance();
    private static BrodcastChatHandler brodcastChatHandler = BrodcastChatHandler.getInstance();
    private static JTextArea consoleTextArea = new JTextArea();
    private static JTextArea threadConnessiTextArea = new JTextArea();
    private static JLabel consoleLabel = new JLabel("Console:");
    private static JLabel threadConnessiLabel = new JLabel("Lista thread attualmente connessi:");
    /**
     * Questo metodo costruisce e stampa il frame
     * */
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    brodcastChatHandler.sendPacketToBrodcast(Constants.BYE,"1");
                } catch (Exception ignored) {}
                e.getWindow().dispose();
                System.exit(0);
            }
        });
    }
    /**
     * Questo metodo scrive sulla GUI (creata con {@link #ConsoleGUI(String)})
     * le informazioni passate come parametro.
     * @param log la stringa da scrivere nel file di log
     * */
    public static synchronized void logGUI(String log) {
        consoleTextArea.setText(consoleTextArea.getText() + log + "\n");
        consoleTextArea.update(consoleTextArea.getGraphics());
    }
    /**
     * Questo metodo aggiunge un user nella {@link #threadConnessiTextArea}<br>
     * Questo metodo aggiunge queste informazioni alla textArea:
     * <ul>
     *     <li>
     *         Nome Thread: Il nome del Thread
     *     </li>
     *     <li>
     *         Indirizzo ip e porta: L'indirizzo ip e la porta dell Thread
     *     </li>
     *     <li>
     *         Nome: Nome dell'user del Thread
     *     </li>
     *     <li>
     *         Connected User: l'user con quale il client sta scrivendo
     *     </li>
     * </ul>
     * */
    public static synchronized void addUserToLogGUI(ClientThread clientThread) {
        // si usa setText(getText + nuovoTesto) perchè senno non funziona l'auto scroll della gui
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
    /**
     * Questo metodo rimuove un user dalla {@link #threadConnessiTextArea}
     * @param clientThread Il Thread da rimuovere
     * */
    public static synchronized void removeUserFromGUI(ClientThread clientThread) {
        // si usa setText(getText + nuovoTesto) perchè senno non funziona 'auto scroll della gui
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
    /**
     * Questo metodo aggiorna la {@link #threadConnessiTextArea}
     * */
    public static synchronized void updateThreadConnessiGUI() {
        threadConnessiTextArea.setText("");
        for (ClientThread i : userManager.listaThreadConnessi) {
            addUserToLogGUI(i);
        }
    }
}