import UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

import java.net.Socket;

public class ServerThread extends Thread{
    User user;
    Socket s;
    LogManager logManager;
    public ServerThread(Socket s, LogManager logManager){
        this.s = s;
        this.logManager = logManager;
    }
    @Override
    public void run(){
        UsersConnection connessione = new UsersConnection(s);
        logManager.logPrint(this.getName() + " in attesa di nome da Client " + s.getRemoteSocketAddress().toString());
        String nome = connessione.getLine();
        user = new User(nome, s, connessione);
        logManager.logPrint("Creato User " + user.getNome());
        logManager.logPrint("Client " + s.getRemoteSocketAddress().toString() + " assume il nome " + user.getNome());
    }
}