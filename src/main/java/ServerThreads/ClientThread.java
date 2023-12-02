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
    private String connectedUser;
    boolean isRunning = true;
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
    public String getConnectedUser() {
        return connectedUser;
    }
    public void setConnectedUser(String connectedUser) {
        this.connectedUser = connectedUser;
    }
    public boolean isRunning() {
        return isRunning;
    }
    public void setRunning(boolean running) {
        isRunning = running;
    }
    @Override
    public void run(){
        UsersConnection connessione = new UsersConnection(socket);
        logger.logPrint(this.getName() + " in attesa di nome da Client " + socket.getRemoteSocketAddress().toString());
        do {
            packetManager.packetDecode(connessione.getLine(), this);
        } while (isRunning());
    }
}