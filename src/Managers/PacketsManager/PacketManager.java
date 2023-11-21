package Managers.PacketsManager;

import Managers.ChatHandlers.BrodcastChatHandler;
import Managers.ChatHandlers.DirectChatHander;
import Managers.LogManager;
import ServerThreads.ClientThread;

import java.util.Arrays;

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
    public void sendPacketToUser(String messaggio, String onUser){
        LogManager.getInstance().logPrint("Invio il pacchetto");
        DirectChatHander.getInstance().sendMessageToUser(messaggio,onUser);
    }
    public void sendPacketToBroadcast(String messaggio){
        BrodcastChatHandler.getInstance().sendMessageToBrodcast(messaggio);
    }
    public void packetDecode(String packet, ClientThread clientThread){
        LogManager.getInstance().logPrint("Il pacchetto inviato da " + clientThread.getName() + " sta per essere processato");
        packetDecoder.decodePacket(packetDecoder.getCommand(packet), clientThread);
    }
    public void sendConfirmationPacket(ClientThread clientThread){
        LogManager.getInstance().logPrint("Invio la conferma dello switch della chat a " + clientThread.getName());
        clientThread.getUser().getConnesione().send(PacketEncoder.getInstance().encodeMsgRequest("1"));
    }
}
