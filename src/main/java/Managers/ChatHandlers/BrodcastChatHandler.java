package Managers.ChatHandlers;

import Constants.Constants;
import Managers.LogManager;
import Managers.UserManager;
import ServerThreads.ClientThread;
/**
 * Questa classe gestisce l'invio dei pacchetti in Broadcast
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *     <li>
 *        {@link #sendMessageToBrodcast(String, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #sendPacketToBrodcast(Constants, String)}
 *     </li>
 * </ul>
 * @author Matteo Frosini
 * */
public class BrodcastChatHandler {
    public static BrodcastChatHandler BrodcastChatHandler;
    private UserManager userManager = UserManager.getInstance();
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
    /**
     * Questo metodo gestisce l'invio dei messaggi in Broadcast
     * @param messaggio il messaggio da inviare
     * @param clientThread il thread che vuole inviare un messaggio in broadcast
     * */
    public void sendMessageToBrodcast(String messaggio, ClientThread clientThread) {
        logger.logPrint("il Server si prepara per l'invio di un messaggio nel canale di broadcast");
        for (ClientThread t : userManager.listaThreadConnessi) {
            t.getUser().getConnesione().send(Constants.MSGRECIVEDBROADCAST + ":" + "[" + clientThread.getUser().getNome() + "] " + messaggio);
            logger.logPrint("Il messaggio è stato mandato a " + t.getName());
        }
    }
    /**
     * Questo metodo gestisce l'invio dei messaggi in Broadcast
     * @param tipoDiPacchetto il tipo di paccheto che si vuole inviare un messaggio in broadcast
     * @param messaggio il messaggio da inviare
     * */
    public void sendPacketToBrodcast(Constants tipoDiPacchetto, String messaggio) {
        logger.logPrint("il Server si prepara per l'invio di un messaggio nel canale di broadcast");
        for (ClientThread t : userManager.listaThreadConnessi) {
            t.getUser().getConnesione().send(tipoDiPacchetto + ":" + messaggio);
            logger.logPrint("Il pacchetto di tipo " + tipoDiPacchetto + " è stato mandato a " + t.getName());
        }
    }
}
