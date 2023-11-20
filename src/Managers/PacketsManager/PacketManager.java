package Managers.PacketsManager;

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
    public void sendPacketToUser(String messaggio, String onUser, ClientThread clientThread){
        LogManager.getInstance().logPrint("Invio il pacchetto");
        DirectChatHander.getInstance().sendMessageToUser(messaggio,onUser,clientThread);
    }
    public void packetSendBroadcast(String messaggio, ClientThread clientThread){
        BrodcastChatHandler.getInstance().sendMessageToBrodcast(messaggio);
    }
    public void packetDecode(String packet, ClientThread clientThread){
        LogManager.getInstance().logPrint("Il pacchetto inviato a " + clientThread.getName() + " sta per essere processato");
        packetDecoder.decodePacket(packetDecoder.getCommand(packet), clientThread);
    }
}
