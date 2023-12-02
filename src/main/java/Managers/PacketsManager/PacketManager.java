package Managers.PacketsManager;

import Constants.Constants;
import Managers.ChatHandlers.BrodcastChatHandler;
import Managers.ChatHandlers.DirectChatHandler;
import Managers.LogManager;
import ServerThreads.ClientThread;

public class PacketManager {
    private static PacketManager pm;
    PacketDecoder packetDecoder;
    private PacketManager(){
        packetDecoder = packetDecoder.getInstance();
    }
    public static PacketManager getInstance() {
        if (pm == null){
            pm = new PacketManager();
        }
        return pm;
    }
    public void packetDecode(String packet, ClientThread receiver){
        LogManager.getInstance().logPrint("Il pacchetto inviato da " + receiver.getName() + " sta per essere processato");
        packetDecoder.decodePacket(packetDecoder.getCommand(packet), receiver);
    }
    public void sendPacketToUser(Constants tipoPacchetto, String messaggio, ClientThread clientThread){
        if (isRichiedente(tipoPacchetto)){
            LogManager.getInstance().logPrint("Invio il pacchetto di tipo " + tipoPacchetto + " a user " + clientThread.getUser().getNome());
            DirectChatHandler.getInstance().sendMessageToSelf(tipoPacchetto,messaggio,clientThread);
        } else {
            LogManager.getInstance().logPrint("Invio il pacchetto di tipo " + tipoPacchetto + " a user " + clientThread.getConnectedUser());
            DirectChatHandler.getInstance().sendMessageToUser(tipoPacchetto,messaggio,clientThread);
        }
    }
    private boolean isRichiedente(Constants tipoPacchetto) {
        switch (tipoPacchetto){
            case CHATDATA, MSGREQUEST, USERLIST -> {return true;}
            default -> {return false;}
        }
    }
    public void sendPacketToBroadcast(String messaggio, ClientThread clientThread){
        LogManager.getInstance().logPrint("Invio il pacchetto da parte di " + clientThread.getUser().getNome() + " nel canale di broadcast");
        BrodcastChatHandler.getInstance().sendMessageToBrodcast(messaggio,clientThread);
    }
}
