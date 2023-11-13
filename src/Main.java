import Managers.LogManager;
import Managers.ResourceManager;

import java.net.ServerSocket;
import java.net.Socket;
public class Main {
    public static void main(String[] args) {
        LogManager logger = new LogManager();
        ResourceManager resourceManager = new ResourceManager(logger);
        logger.initLogsFolder();
        resourceManager.initDataFolders();

        try {
            ServerSocket server = new ServerSocket(2750);
            while(true){
                Socket s = server.accept();
                logger.logPrint("Connessione accetta col client " + s.getRemoteSocketAddress().toString());
                ServerThread thread = new ServerThread(s, logger);
                logger.logPrint("Sposto client " + s.getRemoteSocketAddress().toString() + " su " + thread.getName());
                thread.start();
                logger.logPrint(thread.getName() + " avviato");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}