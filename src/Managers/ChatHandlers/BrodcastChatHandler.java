package Managers.ChatHandlers;

import Managers.LogManager;
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
    public void sendMessageToBrodcast(String messaggio){
        logger.logPrint("il Server si prepara per l'invio di un messaggio nel canale di broadcast");
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread t : threads) {
            if (t instanceof ClientThread toSend){//da cambiare in caso non vengano aggiunti al programma altri Thread
                if (!(toSend.getUser() == null)){
                    toSend.getUser().getConnesione().send(messaggio);
                    logger.logPrint("Il messaggio è stato mandato a " + t.getName());
                }
                logger.logPrint("Messaggio non inviato a " + t.getName() + " perchè non ha ancora completato il login");
            }
        }
    }
}
