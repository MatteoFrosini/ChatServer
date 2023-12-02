package Managers.ChatHandlers;

import Constants.Constants;
import Managers.LogManager;
import Managers.PacketsManager.PacketEncoder;
import Managers.UserManager;
import ServerThreads.ClientThread;

public class BrodcastChatHandler {
    public static BrodcastChatHandler BrodcastChatHandler;
    private LogManager logger;

    private BrodcastChatHandler() {
        this.logger = LogManager.getInstance();
    }

    public static synchronized BrodcastChatHandler getInstance() {
        if (BrodcastChatHandler == null) {
            BrodcastChatHandler = new BrodcastChatHandler();
        }
        return BrodcastChatHandler;
    }

    public void sendMessageToBrodcast(String messaggio, ClientThread clientThread) {
        logger.logPrint("il Server si prepara per l'invio di un messaggio nel canale di broadcast");
        for (ClientThread t : UserManager.getInstance().getBroadcast()) {
            t.getUser().getConnesione().send(PacketEncoder.getInstance().encode(Constants.MSGRECIVEDBROADCAST,
                    "[" + clientThread.getUser().getNome() + "] " + messaggio));
            logger.logPrint("Il messaggio è stato mandato a " + t.getName());
        }
    }

    public void BLAsendMessageToBrodcast(Constants tipoDiPacchetto, String messaggio, ClientThread clientThread) {
        logger.logPrint("il Server si prepara per l'invio di un messaggio nel canale di broadcast");
        for (ClientThread t : UserManager.getInstance().getBroadcast()) {
            t.getUser().getConnesione().send(tipoDiPacchetto + messaggio);
            logger.logPrint("Il messaggio è stato mandato a " + t.getName());
        }
    }
}
