package Managers.ChatHandlers;

import Managers.LogManager;
import Managers.PacketsManager.PacketEncoder;
import Managers.UserManager;
import ServerThreads.ClientThread;

import java.util.Set;

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
    public void sendMessageToUser(String messaggio, ClientThread clientThread){
        logger.logPrint(clientThread.getConnectedUser());
        if (clientThread.getUser() == null){
            logger.logPrint("Messaggio non inviato a " + clientThread.getName() + " perchè non ha ancora completato il login");
        } else {
            UserManager.getInstance().getSpecificClient(clientThread.getConnectedUser()).getUser().getConnesione().send(PacketEncoder.getInstance().encodeMsg(messaggio));
        }
    }

    public void sendMessageToSelf(String messaggio, ClientThread clientThread){
        clientThread.getUser().getConnesione().send(PacketEncoder.getInstance().encodeUserList(messaggio));
    }
}