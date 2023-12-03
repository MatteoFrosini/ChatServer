package Managers.ChatHandlers;

import Constants.Constants;
import Managers.LogManager;
import Managers.PacketsManager.PacketEncoder;
import Managers.ResourceManager;
import Managers.UserManager;
import ServerThreads.ClientThread;

public class DirectChatHandler {
    private static DirectChatHandler dch;
    private LogManager logger = LogManager.getInstance();
    private DirectChatHandler(){};
    public static synchronized DirectChatHandler getInstance(){
        if (dch == null){
            dch = new DirectChatHandler();
        }
        return dch;
    }
    public void sendMessageToUser(Constants tipoDiPacchetto, String messaggio, ClientThread clientThread){
        if (!(UserManager.getInstance().getSpecificClient(clientThread.getConnectedUser()).equals(clientThread.getUser().getNome()))){
            UserManager.getInstance().getSpecificClient(clientThread.getConnectedUser()).getUser().getConnesione().send(PacketEncoder.getInstance().encode(tipoDiPacchetto,"[" + clientThread.getUser().getNome() + "] " + messaggio));
            logger.logPrint("Il pacchetto è stato inviato con successo");
        } else {
            UserManager.getInstance().getSpecificClient(clientThread.getConnectedUser()).getUser().getConnesione().send(PacketEncoder.getInstance().encode(tipoDiPacchetto,"[" + clientThread.getUser().getNome() + "] ti ha scritto un messaggio"));
            logger.logPrint("Il pacchetto è stato inviato con successo");
        }
        if (tipoDiPacchetto.equals(Constants.MSG)){
            ResourceManager.getInstance().writeChat(clientThread, UserManager.getInstance().getSpecificClient(clientThread.getConnectedUser()),messaggio);
        }
    }
    public void sendMessageToSelf(Constants tipoDiPacchetto, String messaggio, ClientThread clientThread){
        clientThread.getUser().getConnesione().send(PacketEncoder.getInstance().encode(tipoDiPacchetto,messaggio));
        logger.logPrint("Il pacchetto è stato inviato con successo");
    }
}