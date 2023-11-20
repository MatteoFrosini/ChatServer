package Managers.PacketsManager;

import ServerThreads.ClientThread;
import User.UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

public class PacketDecoder {
    private static PacketDecoder pd;
    private PacketDecoder() {};
    public static PacketDecoder getInstance() {
        if (pd == null){
            pd = new PacketDecoder();
        }
        return pd;
    }
    public String[] getCommand(String packet){
        return packet.split(":");
    }
    public void decodePacket(String[] cmd, ClientThread clientThread){
        switch (cmd[0]){
            case "hello" -> {onHelloPacket(cmd[1],clientThread);}
            case "bye" -> {}
            case "userListRequest" -> {}
            case "switchToBroadcast" -> {onSwitchToBroadcast(clientThread);}
            case "switchToUser" -> {}
            case "msg" -> {onMessage(clientThread);}
            default -> {}
        }
    }
    private void onHelloPacket(String nome, ClientThread clientThread) {
        clientThread.setUser(new User(nome, new UsersConnection(clientThread.getSocket())));
        LogManager.getInstance().logPrintAsClient(clientThread,"Creato User " + clientThread.getUser().getNome());
        LogManager.getInstance().logPrintAsClient(clientThread,"Client " + clientThread.getSocket().getRemoteSocketAddress().toString() + " assume il nome " + clientThread.getUser().getNome());
    }
    private void onSwitchToBroadcast(ClientThread clientThread) {
        if (!(clientThread.isOnBrodcast())){
            clientThread.setOnBrodcast(true);
            LogManager.getInstance().logPrintAsServer("Client " + clientThread.getUser().getNome() + " connesso al canale di Broadcast");
        } else {
            LogManager.getInstance().logPrintAsServer("Client " + clientThread.getUser().getNome() + " già connesso al canale di Broadcast");
        }
    }
    private void onMessage(ClientThread clientThread) {
        if (clientThread.isOnBrodcast()){

        }
    }
}