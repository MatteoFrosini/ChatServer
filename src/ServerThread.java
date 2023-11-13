import Connection.Connection;
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
        Connection connessione = new Connection(s);
        String nome = connessione.getLine();
        user = new User(nome, s, connessione);
        logManager.logPrint("Creato User " + user.getNome());
        logManager.logPrint("Client " + s.getRemoteSocketAddress().toString() + " assume il nome " + user.getNome());
    }


}