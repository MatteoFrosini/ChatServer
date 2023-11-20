package Managers.PacketsManager;

public class PacketEncoder {
    private static PacketEncoder pd;
    private PacketEncoder() {};
    public static PacketEncoder getInstance() {
        if (pd == null){
            pd = new PacketEncoder();
        }
        return pd;
    }
    public String encodeUserList(String messaggio){
        return "userList" + messaggio;
    }
    public String encodeMsgRequest(String messaggio){
        return "msgRequest" + messaggio;
    }
    public String encodeMsg(String messaggio){
        return "msg" + messaggio;
    }
    public String encodeMsgRecivedBroadcast(String messaggio){
        return "msgRecivedBroadcast" + messaggio;
    }
}