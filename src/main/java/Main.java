import GUI.ConsoleGUI;
import Managers.LogManager;

import Managers.ResourceManager;
import Managers.ServerStructureManager;
import ServerThreads.ClientThread;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //Compie le operazioni necessarie per la creazione dei file del server
        ServerStructureManager.getInstance().checkServerDataStructure(new ArrayList<>(Arrays.asList("data", "serverLogs")));
        ResourceManager.getInstance().initData();
        ConsoleGUI consoleGUI = new ConsoleGUI("Console");
        try (ServerSocket server = new ServerSocket(2750)) {
            int numeroClientThread = 0;
            while (true) {
                //il server aspetta di ricevere una connessione da un client
                Socket s = server.accept();
                LogManager.getInstance().logPrint("Connessione accetta col client " + s.getRemoteSocketAddress().toString());
                ClientThread thread = new ClientThread(s, numeroClientThread);
                LogManager.getInstance().logPrint("Spostato client " + s.getRemoteSocketAddress().toString() + " su " + thread.getName());
                numeroClientThread++;
                thread.start();
                LogManager.getInstance().logPrint(thread.getName() + " avviato");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}