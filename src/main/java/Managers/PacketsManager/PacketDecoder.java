package Managers.PacketsManager;

import Constants.Constants;
import GUI.ConsoleGUI;
import Managers.UserManager;
import ServerThreads.ClientThread;
import User.UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

import java.io.IOException;

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
    public String[] getData(String packet){
        return packet.split(";");
    }
    public void decodePacket(String[] cmd, ClientThread clientThread){
        LogManager.getInstance().logPrint("Prefisso del comando in arrivo da " + clientThread.getName() + ": " + cmd[0]);
        LogManager.getInstance().logPrint("Dati del comando in arrivo da " + clientThread.getName() + ": " + cmd[1]);
        switch (Constants.valueOf(cmd[0])){
            // @Deprecated case "hello" -> {onHelloPacket(cmd[1],clientThread);}
            case CREATEUSER -> {onCreateUser(getData(cmd[1]),clientThread);}
            case LOGININFO -> {onLoginInfo(getData(cmd[1]),clientThread);}
            case BYE -> {onBye(clientThread);}
            case USERLISTREQUEST -> {onUserListRequest(clientThread);}
            case SWITCHBROADCAST -> {onSwitchToBroadcast(clientThread);}
            case SWITCHTOUSER -> {onSwitchToUser(cmd[1],clientThread);}
            case CHATREQUEST -> {onChatRequest();}
            case MSG -> {onMessage(cmd[1],clientThread);}
            default -> {LogManager.getInstance().logPrint("Nessun pacchetto riconosciuto");}
        }
    }
    private void onCreateUser(String[] data, ClientThread clientThread) {
        LogManager.getInstance().logPrint("Ricevute informazioni riguardo alla creazione di un nuovo utente da parte di " + clientThread.getName());
        UserManager.getInstance().newThreadJoin(clientThread);
    }
    private void onLoginInfo(String[] data, ClientThread clientThread) {
        LogManager.getInstance().logPrint("Ricevute informazioni riguardo al login da parte di " + clientThread.getName());
        if (UserManager.getInstance().verifyLoginInfo(data[0],data[1])){
            LogManager.getInstance().logPrint("User" + clientThread.getName() + " verificato");
            UserManager.getInstance().newThreadJoin(clientThread);
            PacketManager.getInstance().sendPacketToUser(Constants.LOGININFOSTATUS,"1", clientThread,true);
        }else {
            LogManager.getInstance().logPrint("User" + clientThread.getName() + " non verificato: l'username o la password sono sbagliati");
            PacketManager.getInstance().sendPacketToUser(Constants.LOGININFOSTATUS,"0", clientThread,true);
        }
    }
    private void onChatRequest() {
    }
    private void onHelloPacket(String nome, ClientThread clientThread) {
        LogManager.getInstance().logPrint("Ricevuto pacchetto con comando hello");
        clientThread.setUser(new User(nome, new UsersConnection(clientThread.getSocket())));
        LogManager.getInstance().logPrint("Creato User " + clientThread.getUser().getNome());
        LogManager.getInstance().logPrint("Client " + clientThread.getSocket().getRemoteSocketAddress().toString() + " assume il nome " + clientThread.getUser().getNome());
        UserManager.getInstance().newThreadJoin(clientThread);
    }

    private void onBye(ClientThread clientThread){
        LogManager.getInstance().logPrint("Inizio la disconnessione di " + clientThread.getUser().getNome() + " su richiesta di quest'ultimo");
        clientThread.setRunning(false);
        try {
            clientThread.getSocket().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UserManager.removeFromList(clientThread);
        LogManager.getInstance().logPrint(clientThread.getUser().getNome() + " è stato disconesso con successo");
    }

    private void onUserListRequest(ClientThread clientThread){
        LogManager.getInstance().logPrint("Ricevuta richiesta della User List da " + clientThread.getUser().getNome());
        PacketManager.getInstance().sendPacketToUser(Constants.USERLIST, UserManager.getInstance().getClientListAsString(),clientThread,true);
    }
    private void onSwitchToBroadcast(ClientThread clientThread) {
        if (!(clientThread.getConnectedUser().equals("BROADCAST"))){
            clientThread.setConnectedUser("BROADCAST");
            LogManager.getInstance().logPrint("Client " + clientThread.getUser().getNome() + " connesso al canale di Broadcast");
        } else {
            LogManager.getInstance().logPrint("Client " + clientThread.getUser().getNome() + " già connesso al canale di Broadcast");
        }
        PacketManager.getInstance().sendPacketToUser(Constants.MSGREQUEST,"1",clientThread,true);
        ConsoleGUI.updateThreadConnessiGUI();
    }
    private void onSwitchToUser(String user,ClientThread clientThread) {
        if (UserManager.getInstance().doesClientExist(user)){
            if (!(clientThread.getConnectedUser().equals(user))){
                clientThread.setConnectedUser(user);
                LogManager.getInstance().logPrint("Client " + clientThread.getUser().getNome() + " connesso con " + user);
            } else {
                LogManager.getInstance().logPrint("Client " + clientThread.getUser().getNome() + " già connesso con " + user);
            }
            PacketManager.getInstance().sendPacketToUser(Constants.MSGREQUEST,"1",clientThread,true);
            ConsoleGUI.updateThreadConnessiGUI();
        } else {
            LogManager.getInstance().logPrint("User " + user + " non esiste");
        }
    }
    private void onMessage(String messaggio, ClientThread clientThread) {
        if (clientThread.getConnectedUser().equals("BROADCAST")){
            LogManager.getInstance().logPrint("Ricevuto messaggio per il canale di Broadcast");
            PacketManager.getInstance().sendPacketToBroadcast(messaggio,clientThread);
        } else {
            LogManager.getInstance().logPrint("Ricevuto messaggio per " + clientThread.getUser().getNome());
            PacketManager.getInstance().sendPacketToUser(Constants.MSG,messaggio,clientThread,false);
        }
    }
}