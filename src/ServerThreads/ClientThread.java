package ServerThreads;

import Managers.ChatHandlers.BrodcastChatHandler;
import User.UsersConnection.UsersConnection;
import Managers.LogManager;
import User.User;

import java.net.Socket;

public class ClientThread extends Thread{
    User user;
    Socket s;
    LogManager logger;
    public ClientThread(Socket s, int i){
        super("ClientThread-" + i);
        this.s = s;
        this.logger = LogManager.getInstance();
    }
    public User getUser() {
        return user;
    }
    @Override
    public void run(){
        UsersConnection connessione = new UsersConnection(s);
        logger.logPrintAsClient(this,this.getName() + " in attesa di nome da Client " + s.getRemoteSocketAddress().toString());
        String nome = connessione.getLine();
        user = new User(nome, s, connessione);
        logger.logPrintAsClient(this,"Creato User " + user.getNome());
        logger.logPrintAsClient(this,"Client " + s.getRemoteSocketAddress().toString() + " assume il nome " + user.getNome());
        String messageToSend;
        do {
            messageToSend = user.getConnesione().getLine();
            BrodcastChatHandler.getInstance().sendMessageToBrodcast(messageToSend);
        } while (true);
    }
}