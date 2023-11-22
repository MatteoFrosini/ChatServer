package Managers.PacketsManager;

import Managers.ChatHandlers.BrodcastChatHandler;
import Managers.ChatHandlers.DirectChatHander;
import Managers.UserManager;
import ServerThreads.ClientThread;
import User.UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

public class PacketDecoder {
    private static PacketDecoder pd;
    private PacketDecoder() {}
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
        LogManager.getInstance().logPrint("Prefisso del comando in arrivo da " + clientThread.getName() + ": " + cmd[0]);
        LogManager.getInstance().logPrint("Dati del comando in arrivo da " + clientThread.getName() + ": " + cmd[1]);
        switch (cmd[0]){
            case "hello" -> {onHelloPacket(cmd[1],clientThread);}
            case "bye" -> {}
            case "userListRequest" -> {}
            case "switchBroadcast" -> {onSwitchToBroadcast(clientThread);}
            case "switchToUser" -> {onSwitchToUser(clientThread);}
            case "msg" -> {onMessage(cmd[1],clientThread);}
            default -> {}
        }
    }
    private void onHelloPacket(String nome, ClientThread clientThread) {
        LogManager.getInstance().logPrint("Ricevuto pacchetto con comando hello");
        clientThread.setUser(new User(nome, new UsersConnection(clientThread.getSocket())));
        LogManager.getInstance().logPrint("Creato User " + clientThread.getUser().getNome());
        LogManager.getInstance().logPrint("Client " + clientThread.getSocket().getRemoteSocketAddress().toString() + " assume il nome " + clientThread.getUser().getNome());
        UserManager.getInstance().newThreadJoin(clientThread);
    }
    private void onSwitchToBroadcast(ClientThread clientThread) {
        if (!(clientThread.getConnectedUser().equals("BROADCAST"))){
            clientThread.setConnectedUser("BROADCAST");
            LogManager.getInstance().logPrint("Client " + clientThread.getUser().getNome() + " connesso al canale di Broadcast");
        } else {
            LogManager.getInstance().logPrint("Client " + clientThread.getUser().getNome() + " già connesso al canale di Broadcast");
        }
        PacketManager.getInstance().sendConfirmationPacket(clientThread);
    }
    private void onSwitchToUser(ClientThread clientThread) {
        PacketManager.getInstance().sendConfirmationPacket(clientThread);
    }
    private void onMessage(String messaggio, ClientThread clientThread) {
        if (clientThread.getConnectedUser().equals("BROADCAST")){
            LogManager.getInstance().logPrint("Ricevuto messaggio per il canale di Broadcast");
            PacketManager.getInstance().sendPacketToBroadcast(messaggio);
        } else {
            LogManager.getInstance().logPrint("Ricevuto messaggio per " + clientThread.getUser().getNome());
            PacketManager.getInstance().sendPacketToUser(messaggio,clientThread.getConnectedUser());
        }
    }
}