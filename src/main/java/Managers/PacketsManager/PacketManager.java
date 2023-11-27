package Managers.PacketsManager;

import Managers.ChatHandlers.BrodcastChatHandler;
import Managers.ChatHandlers.DirectChatHander;
import Managers.LogManager;
import ServerThreads.ClientThread;
import com.fasterxml.jackson.core.JsonEncoding;

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
    public void sendPacketToUser(String messaggio, ClientThread clientThread){
        LogManager.getInstance().logPrint("Invio il pacchetto");
        DirectChatHander.getInstance().sendMessageToUser(messaggio,clientThread);
    }
    public void sendPacketToSelf(String messaggio, ClientThread clientThread){
        LogManager.getInstance().logPrint("Invio il pacchetto");
        DirectChatHander.getInstance().sendMessageToSelf(messaggio,clientThread);
    }
    public void sendPacketToBroadcast(String messaggio, ClientThread clientThread){
        BrodcastChatHandler.getInstance().sendMessageToBrodcast(messaggio,clientThread);
    }
    public void packetDecode(String packet, ClientThread clientThread){
        LogManager.getInstance().logPrint("Il pacchetto inviato da " + clientThread.getName() + " sta per essere processato");
        packetDecoder.decodePacket(packetDecoder.getCommand(packet), clientThread);
    }
    public void sendConfirmationPacket(ClientThread clientThread){
        clientThread.getUser().getConnesione().send(PacketEncoder.getInstance().encodeMsgRequest("1"));
        LogManager.getInstance().logPrint("Invio la conferma dello switch della chat a " + clientThread.getName());
    }
}
