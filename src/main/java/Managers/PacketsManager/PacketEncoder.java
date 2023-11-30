package Managers.PacketsManager;

import Constants.Constants;

public class PacketEncoder {
    private static PacketEncoder pd;
    private PacketEncoder() {};
    public static PacketEncoder getInstance() {
        if (pd == null){
            pd = new PacketEncoder();
        }
        return pd;
    }
    public String encode(Constants tipoDiPacchetto, String messaggio) {
        return tipoDiPacchetto + ":" + messaggio;
    }
}