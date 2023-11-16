import Managers.LogManager;
import Managers.ResourceManager;
import Managers.ServerStructureManager;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        LogManager logger = LogManager.getInstance();
        ResourceManager resourceManager = new ResourceManager(logger);
        ServerStructureManager ssm = ServerStructureManager.getInstance();
        ssm.checkServerDataStructure(new ArrayList<>(Arrays.asList("data","serverLogs",".\\data\\loginInfo",".\\data\\chatData")));
        // logger singleton
        // ssm
        try {
            ServerSocket server = new ServerSocket(2750);
            while(true){
                Socket s = server.accept();
                logger.logPrint("Connessione accetta col client " + s.getRemoteSocketAddress().toString());
                ServerThread thread = new ServerThread(s);
                logger.logPrint("Sposto client " + s.getRemoteSocketAddress().toString() + " su " + thread.getName());
                thread.start();
                logger.logPrint(thread.getName() + " avviato");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}