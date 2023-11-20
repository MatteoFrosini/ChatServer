package ServerThreads;

import Managers.PacketsManager.PacketManager;
import User.UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

import java.net.Socket;

public class ClientThread extends Thread{
    private User user;
    private Socket socket;
    private LogManager logger;
    private PacketManager packetManager;
    private boolean onBrodcast = false;
    private String onUser = null;
    public ClientThread(Socket s, int i){
        super("ClientThread-" + i);
        this.socket = s;
        this.logger = LogManager.getInstance();
        this.packetManager = PacketManager.getInstance();
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Socket getSocket() {
        return socket;
    }
    public boolean isOnBrodcast() {
        return onBrodcast;
    }
    public String getOnUser() {
        return onUser;
    }
    public void setOnBrodcast(boolean onBrodcast) {
        this.onBrodcast = onBrodcast;
    }
    public void setOnUser(String onUser) {
        this.onUser = onUser;
    }
    @Override
    public void run(){
        UsersConnection connessione = new UsersConnection(socket);
        logger.logPrint(this.getName() + " in attesa di nome da Client " + socket.getRemoteSocketAddress().toString());
        do {
            logger.logPrint("Client " + this.getName() + " a ricevuto un pacchetto");
            packetManager.packetDecode(connessione.getLine(), this);
        } while (true);
        /*user = new User(nome, s, connessione);
        logger.logPrintAsClient(this,"Creato User " + user.getNome());
        logger.logPrintAsClient(this,"Client " + s.getRemoteSocketAddress().toString() + " assume il nome " + user.getNome());
        do {
            messageToSend = user.getConnesione().getLine();
            BrodcastChatHandler.getInstance().sendMessageToBrodcast(messageToSend);
        } while (true);*/
    }
}