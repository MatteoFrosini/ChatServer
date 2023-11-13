import Connection.Connection;
import Logger.Logger;
import User.User;

import java.net.Socket;

public class ServerThread extends Thread{
    User user;
    Socket s;
    Logger logger;
    public ServerThread(Socket s, Logger logger){
        this.s = s;
        this.logger = logger;
    }
    @Override
    public void run(){
        Connection connessione = new Connection(s);
        String nome = connessione.getLine();
        user = new User(nome, s, connessione);
        logger.logPrint("Creato User " + user.getNome());
        logger.logPrint("Client " + s.getRemoteSocketAddress().toString() + " assume il nome " + user.getNome());
    }


}
