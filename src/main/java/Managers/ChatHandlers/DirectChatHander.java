package Managers.ChatHandlers;

import Constants.Constants;
import Managers.LogManager;
import Managers.PacketsManager.PacketEncoder;
import Managers.UserManager;
import ServerThreads.ClientThread;

public class DirectChatHander {
    private static DirectChatHander dch;
    private LogManager logger = LogManager.getInstance();
    private DirectChatHander(){};
    public static synchronized DirectChatHander getInstance(){
        if (dch == null){
            dch = new DirectChatHander();
        }
        return dch;
    }
    public void sendMessageToUser(Constants tipoDiPacchetto, String messaggio, String connectedUser){
        UserManager.getInstance().getSpecificClient(connectedUser).getUser().getConnesione().send(PacketEncoder.getInstance().encode(tipoDiPacchetto,messaggio));
        logger.logPrint("Il pacchetto è stato inviato con successo");
    }
    public void sendMessageToSelf(Constants tipoDiPacchetto, String messaggio, ClientThread clientThread){
        clientThread.getUser().getConnesione().send(PacketEncoder.getInstance().encode(tipoDiPacchetto,messaggio));
        logger.logPrint("Il pacchetto è stato inviato con successo");
    }
}