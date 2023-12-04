package ServerThreads;

import Managers.PacketsManager.PacketManager;
import User.UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

import java.net.Socket;
/**
 * Questa classe contiene il Thread che si interfaccia con
 * il client. <br>
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *     <li>
 *          {@link #run()}
 *     </li>
 * </ul>
 * @author Matteo Frosini
 * */
public class ClientThread extends Thread{
    private User user;
    private Socket socket;
    private LogManager logger = LogManager.getInstance();
    private PacketManager packetManager = PacketManager.getInstance();
    private String connectedUser;
    boolean isRunning = true;
    public ClientThread(Socket s, int i){
        super("ClientThread-" + i);
        this.socket = s;
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
    /**
     * Questo metodo è un {@code @Override} del metodo
     * {@link #run()} della classe Thread e contiene le azioni
     * che il Thread esegue quando viene avviato
     * */
    @Override
    public void run(){
        UsersConnection connessione = new UsersConnection(socket);
        logger.logPrint(this.getName() + " in attesa di nome da Client " + socket.getRemoteSocketAddress().toString());
        do {
            packetManager.packetDecode(connessione.getLine(), this);
        } while (isRunning());
    }
}