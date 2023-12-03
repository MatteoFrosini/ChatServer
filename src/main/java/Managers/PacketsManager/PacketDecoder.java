package Managers.PacketsManager;

import Constants.Constants;
import GUI.ConsoleGUI;
import Managers.ResourceManager;
import Managers.UserManager;
import ServerThreads.ClientThread;
import User.UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

import java.io.IOException;
/**
 * Questa Classe si occupa della ricezione dei pacchetti
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *     <li>
 *        {@link #getCommand(String)}
 *     </li>
 *     <li>
 *        {@link #decodePacket(String[], ClientThread)}
 *     </li>
 *     <li>
 *        {@link #onHelloPacket(String, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #onBye(ClientThread)}
 *     </li>
 *     <li>
 *        {@link #onSwitchToBroadcast(ClientThread)}
 *     </li>
 *     <li>
 *        {@link #onSwitchToUser(String, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #onChatRequest(ClientThread)}
 *     </li>
 *     <li>
 *        {@link #onMessage(String, ClientThread)}
 *     </li>
 * </ul>
 * @author Matteo Frosini
 * */
public class PacketDecoder {
    private static PacketDecoder pd;
    private static LogManager logManager = LogManager.getInstance();
    private static UserManager userManager = UserManager.getInstance();
    private static PacketManager packetManager = PacketManager.getInstance();
    private static ResourceManager resourceManager = ResourceManager.getInstance();
    private PacketDecoder() {}
    public static PacketDecoder getInstance() {
        if (pd == null){
            pd = new PacketDecoder();
        }
        return pd;
    }
    /**
     * Questo metodo decodifica il pacchetto dividendolo nel comando
     * e nei dati.
     * @param packet il pacchetto da dividere
     * @return Una lista di stringe contenete il comando e i dati
     * */
    public String[] getCommand(String packet){
        return packet.split(":");
    }
    /**
     * Questo metodo sceglie quale azione compiere in base
     * al tipo di pacchetto che riceve
     * @param cmd Il comando
     * @param clientThread Il Thread che riceve il pacchetto
     * */
    public void decodePacket(String[] cmd, ClientThread clientThread){
        logManager.logPrint("Prefisso del comando in arrivo da " + clientThread.getName() + ": " + cmd[0]);
        logManager.logPrint("Dati del comando in arrivo da " + clientThread.getName() + ": " + cmd[1]);
        switch (Constants.valueOf(cmd[0].toUpperCase())){
            case HELLO -> onHelloPacket(cmd[1],clientThread);
            case BYE -> onBye(clientThread);
            case SWITCHBROADCAST -> onSwitchToBroadcast(clientThread);
            case SWITCHTOUSER -> onSwitchToUser(cmd[1],clientThread);
            case CHATREQUEST -> onChatRequest(clientThread);
            case MSG -> onMessage(cmd[1],clientThread);
            default -> LogManager.getInstance().logPrint("Nessun pacchetto riconosciuto");
        }
    }
    /**
     * Metodo da eseguire nel caso arrivi un pacchetto di tipo {@code hello}
     * @param nome il nome del client
     * @param clientThread Il client che riceve il pacchetto
     * */
    private void onHelloPacket(String nome, ClientThread clientThread) {
        logManager.logPrint("Ricevuto pacchetto con comando hello");
        clientThread.setUser(new User(nome, new UsersConnection(clientThread.getSocket())));
        logManager.logPrint("Creato User " + clientThread.getUser().getNome());
        logManager.logPrint("Client " + clientThread.getSocket().getRemoteSocketAddress().toString() + " assume il nome " + clientThread.getUser().getNome());
        userManager.newThreadJoin(clientThread);
        packetManager.sendPacketToBroadcast(Constants.USERLIST, userManager.getClientListAsString(), clientThread);
    }
    /**
     * Metodo da eseguire nel caso arrivi un pacchetto di tipo {@code bye}
     * @param clientThread Il client che riceve il pacchetto
     * */
    private void onBye(ClientThread clientThread){
        if (clientThread.getUser() == null){
            logManager.logPrint(clientThread.getName() + " si è disconnesso da solo");
            disconnect(clientThread);
        } else {
            logManager.logPrint("Inizio la disconnessione di " + clientThread.getUser().getNome() + " su richiesta di quest'ultimo");
            disconnect(clientThread);
            userManager.removeFromList(clientThread);
            logManager.logPrint(clientThread.getUser().getNome() + " è stato disconesso con successo");
        }
        packetManager.sendPacketToBroadcast(Constants.USERLIST, userManager.getClientListAsString(), clientThread);
    }
    /**
     * Questo metodo serve per chiudere la connessione con un Client
     * @param clientThread Il client da disconnettere
     * */
    private void disconnect(ClientThread clientThread){
        try {
            clientThread.getSocket().close();
            clientThread.setRunning(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Metodo da eseguire nel caso arrivi un pacchetto di tipo {@code switchBroadcast}
     * @param clientThread Il client che riceve il pacchetto
     * */
    private void onSwitchToBroadcast(ClientThread clientThread) {
        if (clientThread.getConnectedUser() == null || (!(clientThread.getConnectedUser().equals("BROADCAST")))){
            clientThread.setConnectedUser("BROADCAST");
            logManager.logPrint("Client " + clientThread.getUser().getNome() + " connesso al canale di Broadcast");
        } else {
            logManager.logPrint("Client " + clientThread.getUser().getNome() + " già connesso al canale di Broadcast");
        }
        packetManager.sendPacketToUser(Constants.MSGREQUEST,"1",clientThread);
        ConsoleGUI.updateThreadConnessiGUI();
    }
    /**
     * Metodo da eseguire nel caso arrivi un pacchetto di tipo {@code swichToUser}
     * @param user il nome dell'user
     * @param clientThread Il client che riceve il pacchetto
     * */
    private void onSwitchToUser(String user,ClientThread clientThread) {
        if (userManager.doesClientExist(user)){
            if (clientThread.getConnectedUser() == null || (!(clientThread.getConnectedUser().equals(user)))){
                clientThread.setConnectedUser(user);
                logManager.logPrint("Client " + clientThread.getUser().getNome() + " connesso con " + user);
            } else {
                logManager.logPrint("Client " + clientThread.getUser().getNome() + " già connesso con " + user);
            }
            packetManager.sendPacketToUser(Constants.MSGREQUEST,"1",clientThread);
            ConsoleGUI.updateThreadConnessiGUI();
        } else {
            logManager.logPrint("User " + user + " non esiste");
        }
    }
    /**
     * Metodo da eseguire nel caso arrivi un pacchetto di tipo {@code ChatRequest}
     * @param clientThread Il client che riceve il pacchetto
     * */
    private void onChatRequest(ClientThread clientThread){
        packetManager.sendPacketToUser(Constants.CHATDATA, resourceManager.getChat(clientThread,userManager.getSpecificClient(clientThread.getConnectedUser())),clientThread);
    }
    /**
     * Metodo da eseguire nel caso arrivi un pacchetto di tipo {@code msg}
     * @param messaggio Il messaggio da inviare
     * @param clientThread Il client che riceve il pacchetto
     * */
    private void onMessage(String messaggio, ClientThread clientThread) {
        if (userManager.listaThreadConnessi.size() <= 1){
            packetManager.sendPacketToBroadcast(Constants.MSGRECIVEDBROADCAST, "Sei ancora da solo, aspetta che qualcuno si connetta", clientThread);
        } else {
            if (clientThread.getConnectedUser().equals("BROADCAST")) {
                logManager.logPrint("Ricevuto messaggio per il canale di Broadcast");
                packetManager.sendPacketToBroadcast(Constants.MSGRECIVEDBROADCAST, messaggio, clientThread);
            } else {
                logManager.logPrint("Ricevuto messaggio per " + clientThread.getUser().getNome());
                packetManager.sendPacketToUser(Constants.MSG, messaggio, clientThread);
            }
        }
    }
}