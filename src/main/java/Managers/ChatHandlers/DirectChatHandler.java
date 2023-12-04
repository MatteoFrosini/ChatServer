package Managers.ChatHandlers;

import Constants.Constants;
import Managers.LogManager;
import Managers.ResourceManager;
import Managers.UserManager;
import ServerThreads.ClientThread;

/**
 * Questa classe gestisce l'invio dei pacchetti ad un singolo User
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *     <li>
 *        {@link #sendMessageToUser(Constants, String, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #sendMessageToSelf(Constants, String, ClientThread)}
 *     </li>
 * </ul>
 * @author Matteo Frosini
 * */
public class DirectChatHandler {
    private static DirectChatHandler dch;
    private LogManager logManager = LogManager.getInstance();
    private UserManager userManager = UserManager.getInstance();
    private ResourceManager resourceManager = ResourceManager.getInstance();
    private DirectChatHandler(){};
    public static synchronized DirectChatHandler getInstance(){
        if (dch == null){
            dch = new DirectChatHandler();
        }
        return dch;
    }
    /**
     * Questo metodo invia il {@code messaggio} all'user che lo deve ricevere
     * @param tipoDiPacchetto il tipo di pacchetto
     * @param messaggio il messaggio da inviare
     * @param clientThread il thread vuole inviare un pacchetto
     * */
    public void sendMessageToUser(Constants tipoDiPacchetto, String messaggio, ClientThread clientThread){
        if (!(userManager.getSpecificClient(clientThread.getConnectedUser()).getConnectedUser().equals(clientThread.getUser().getNome()))){
            userManager.getSpecificClient(clientThread.getConnectedUser()).getUser().getConnesione().send(tipoDiPacchetto + ":" + "[SERVER] " + clientThread.getUser().getNome() + " ti ha scritto un messaggio");
            logManager.logPrint("Il pacchetto è stato inviato con successo");
        } else {
            userManager.getSpecificClient(clientThread.getConnectedUser()).getUser().getConnesione().send(tipoDiPacchetto + ":" + "[" + clientThread.getUser().getNome() + "] " + messaggio);
            logManager.logPrint("Il pacchetto è stato inviato con successo");
        }
        if (tipoDiPacchetto.equals(Constants.MSG)){
            resourceManager.writeChat(clientThread, userManager.getSpecificClient(clientThread.getConnectedUser()),messaggio);
        }
    }
    /**
     * Questo metodo invia il {@code messaggio} allo stesso user
     * che ne ha fatto richiesta
     * @param tipoDiPacchetto il tipo di pacchetto
     * @param messaggio il messaggio da inviare
     * @param clientThread il thread vuole inviare un pacchetto
     * */
    public void sendMessageToSelf(Constants tipoDiPacchetto, String messaggio, ClientThread clientThread){
        clientThread.getUser().getConnesione().send(tipoDiPacchetto + ":" + messaggio);
        logManager.logPrint("Il pacchetto è stato inviato con successo");
    }
}