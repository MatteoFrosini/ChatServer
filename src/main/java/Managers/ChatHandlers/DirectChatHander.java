package Managers.ChatHandlers;

import Managers.LogManager;
import Managers.PacketsManager.PacketEncoder;
import ServerThreads.ClientThread;

import java.util.Set;

public class DirectChatHander {
    private static DirectChatHander dch;
    private LogManager logger;
    private DirectChatHander(){};
    public static synchronized DirectChatHander getInstance(){
        if (dch == null){
            dch = new DirectChatHander();
        }
        return dch;
    }
    public void sendMessageToUser(String messaggio, String onUser){
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread t : threads) {
            if (t instanceof ClientThread toSend){
                if (!(toSend.getUser() == null)){
                    if (toSend.getUser().getNome().equals(onUser)){
                        toSend.getUser().getConnesione().send(PacketEncoder.getInstance().encodeMsg(messaggio));
                        logger.logPrint("Messaggio inviato a " + toSend.getUser().getNome());
                        break;
                    }
                }
                logger.logPrint("Messaggio non inviato a " + t.getName() + " perchè non ha ancora completato il login");
            }
        }
    }
}