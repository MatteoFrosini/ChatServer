package Managers.PacketsManager;
public class PacketManager {
    public String[] interpretPacket(String packet){
        return packet.split(":");
    }
}