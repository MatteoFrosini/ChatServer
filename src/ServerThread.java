import UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

import java.net.Socket;

public class ServerThread extends Thread{
    User user;
    Socket s;
    LogManager logger;
    public ServerThread(Socket s){
        this.s = s;
        this.logger = LogManager.getInstance();
    }
    @Override
    public void run(){
        UsersConnection connessione = new UsersConnection(s);
        logger.logPrint(this.getName() + " in attesa di nome da Client " + s.getRemoteSocketAddress().toString());
        String nome = connessione.getLine();
        user = new User(nome, s, connessione);
        logger.logPrint("Creato User " + user.getNome());
        logger.logPrint("Client " + s.getRemoteSocketAddress().toString() + " assume il nome " + user.getNome());
    }
}