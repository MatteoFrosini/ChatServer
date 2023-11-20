package Managers.PacketsManager;

public class PacketEncoder {
    public String[] getCommand(String packet){
        return packet.split(":");
    }
    public void encodePacket(String[] cmd, String data){
        switch (cmd[0]){
            case "userList" -> {}
            case "msgRequest" -> {}
            case "msg" -> {}
            case "msgRecivedBroadcast" -> {}
            default -> {}
        }
    }
}