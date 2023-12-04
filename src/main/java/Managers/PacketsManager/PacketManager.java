package Managers.PacketsManager;

import Constants.Constants;
import Managers.ChatHandlers.BrodcastChatHandler;
import Managers.ChatHandlers.DirectChatHandler;
import Managers.LogManager;
import Managers.ResourceManager;
import Managers.UserManager;
import ServerThreads.ClientThread;
/**
 * Questa classe gestisce parzialmente l'invio e la ricezione dei
 * pacchetti.<br>
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *     <li>
 *        {@link #packetDecode(String, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #sendPacketToUser(Constants, String, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #sendPacketToBroadcast(Constants, String, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #isRichiedente(Constants)}
 *     </li>
 * </ul>
 * @author Matteo Frosini
 * */
public class PacketManager {
    private static PacketManager pm;
    private LogManager logManager = LogManager.getInstance();
    private DirectChatHandler directChatHandler = DirectChatHandler.getInstance();
    private BrodcastChatHandler brodcastChatHandler = BrodcastChatHandler.getInstance();
    PacketDecoder packetDecoder;
    private PacketManager() {
        packetDecoder = packetDecoder.getInstance();
    }
    public static PacketManager getInstance() {
        if (pm == null) {
            pm = new PacketManager();
        }
        return pm;
    }
    /**
     * Questo metodo indirizza il pacchetto al {@link PacketDecoder}
     * @param packet Il pacchetto da inoltrare
     * @param receiver Il Thread che ha ricevuto il pacchetto
     * */
    public void packetDecode(String packet, ClientThread receiver) {
        logManager.logPrint("Il pacchetto inviato da " + receiver.getName() + " sta per essere processato");
        packetDecoder.decodePacket(packetDecoder.getCommand(packet), receiver);
    }
    /**
     * Questo metodo indirizza i messaggi al {@link DirectChatHandler} per poi
     * procedere con l'invio.
     * @param tipoPacchetto Il tipo di pacchetto da inviare
     * @param messaggio Il messaggio da inviare
     * @param clientThread Il Thread che ha ricevuto il pacchetto
     * */
    public void sendPacketToUser(Constants tipoPacchetto, String messaggio, ClientThread clientThread) {
        if (isRichiedente(tipoPacchetto)) {
            logManager.logPrint("Invio il pacchetto di tipo " + tipoPacchetto + " a user " + clientThread.getUser().getNome());
            directChatHandler.sendMessageToSelf(tipoPacchetto, messaggio, clientThread);
        } else {
            logManager.logPrint("Invio il pacchetto di tipo " + tipoPacchetto + " a user " + clientThread.getConnectedUser());
            directChatHandler.sendMessageToUser(tipoPacchetto, messaggio, clientThread);
        }
    }
    /**
     * Questo metodo serve al metodo {@link #sendPacketToUser(Constants, String, ClientThread)}
     * e al metodo {@link #sendPacketToBroadcast(Constants, String, ClientThread)} per decidere
     * se il pacchetto verra inviato al {@code ConnectedUser} del Thread o al Thread stesso.
     * @param tipoPacchetto Il tipo di pacchetto su cui eseguire il controllo
     * */
    private boolean isRichiedente(Constants tipoPacchetto) {
        switch (tipoPacchetto) {
            case CHATDATA, MSGREQUEST, USERLIST, BYE -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }
    /**
     * Questo metodo indirizza i messaggi al {@link BrodcastChatHandler} per poi
     * procedere con l'invio.
     * @param tipoPacchetto Il tipo di pacchetto da inviare
     * @param messaggio Il messaggio da inviare
     * @param clientThread Il Thread che ha ricevuto il pacchetto
     * */
    public void sendPacketToBroadcast(Constants tipoPacchetto, String messaggio, ClientThread clientThread) {
        if (isRichiedente(tipoPacchetto)) {
            logManager.logPrint("Invio il pacchetto da parte di " + clientThread.getUser().getNome() + " nel canale di broadcast");
            brodcastChatHandler.sendPacketToBrodcast(tipoPacchetto, messaggio);
        } else {
            logManager.logPrint("Invio il pacchetto da parte di " + clientThread.getUser().getNome() + " nel canale di broadcast");
            brodcastChatHandler.sendMessageToBrodcast(messaggio, clientThread);
        }
    }
}
