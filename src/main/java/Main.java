import GUI.ConsoleGUI;
import Managers.LogManager;

import Managers.ResourceManager;
import Managers.ServerStructureManager;
import ServerThreads.ClientThread;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        //Inizializzazione dei Manager del server
        LogManager logger = LogManager.getInstance();
        ResourceManager resourceManager = ResourceManager.getInstance();
        ServerStructureManager ssm = ServerStructureManager.getInstance();
        //Controllo e creazione della struttura del server con i log
        ssm.checkServerDataStructure(new ArrayList<>(Arrays.asList("data","serverLogs",".\\data\\loginInfo",".\\data\\chatData")));
        resourceManager.initData();
        //ConsoleGUI cng = new ConsoleGUI("Console");
        try {
            ServerSocket server = new ServerSocket(2750);
            int numeroClientThread = 0;
            while(true){
                Socket s = server.accept();
                logger.logPrint("Connessione accetta col client " + s.getRemoteSocketAddress().toString());
                ClientThread thread = new ClientThread(s,numeroClientThread);
                logger.logPrint("Spostato client " + s.getRemoteSocketAddress().toString() + " su " + thread.getName());
                numeroClientThread++;
                thread.start();
                logger.logPrint(thread.getName() + " avviato");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}