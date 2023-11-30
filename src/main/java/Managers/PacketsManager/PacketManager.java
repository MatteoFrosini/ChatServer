package Managers.PacketsManager;

import Constants.Constants;
import Managers.ChatHandlers.BrodcastChatHandler;
import Managers.ChatHandlers.DirectChatHander;
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
    public void packetDecode(String packet, ClientThread clientThread){
        LogManager.getInstance().logPrint("Il pacchetto inviato da " + clientThread.getName() + " sta per essere processato");
        packetDecoder.decodePacket(packetDecoder.getCommand(packet), clientThread);
    }
    public void sendPacketToUser(Constants tipoPacchetto, String messaggio, ClientThread clientThread, boolean toSelf){
        LogManager.getInstance().logPrint("Invio il pacchetto");
        if (toSelf){
            DirectChatHander.getInstance().sendMessageToSelf(tipoPacchetto,messaggio,clientThread);
        } else {
            DirectChatHander.getInstance().sendMessageToUser(tipoPacchetto,messaggio,clientThread.getConnectedUser());
        }
    }
    public void sendPacketToBroadcast(String messaggio, ClientThread clientThread){
        BrodcastChatHandler.getInstance().sendMessageToBrodcast(messaggio,clientThread);
    }

}
