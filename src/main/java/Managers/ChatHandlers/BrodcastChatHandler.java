package Managers.ChatHandlers;

import Managers.LogManager;
import Managers.PacketsManager.PacketEncoder;
import Managers.UserManager;
import ServerThreads.ClientThread;
import java.util.Set;

public class BrodcastChatHandler{
    public static BrodcastChatHandler BrodcastChatHandler;
    private LogManager logger;
    private BrodcastChatHandler() {
        this.logger = LogManager.getInstance();
    }
    public static synchronized BrodcastChatHandler getInstance(){
        if (BrodcastChatHandler == null){
            BrodcastChatHandler = new BrodcastChatHandler();
        }return BrodcastChatHandler;
    }
    public void sendMessageToBrodcast(String messaggio, ClientThread clientThread){
        logger.logPrint("il Server si prepara per l'invio di un messaggio nel canale di broadcast");
        for (ClientThread t : UserManager.getInstance().getBroadcast()){
            if (t.getName().equals(clientThread.getName())){
                continue;
            }
            t.getUser().getConnesione().send(PacketEncoder.getInstance().encodeMsgRecivedBroadcast(messaggio));
            logger.logPrint("Il messaggio è stato mandato a " + t.getName());
        }
    }
}
