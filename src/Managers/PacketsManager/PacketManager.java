package Managers.PacketsManager;

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
    public void packetSend(){

    }
    public void packetDecode(String packet, ClientThread clientThread){
        LogManager.getInstance().logPrintAsServer("Il pacchetto inviato a " + clientThread.getName() + " sta per essere processato");
        packetDecoder.decodePacket(packetDecoder.getCommand(packet), clientThread);
    }
}
