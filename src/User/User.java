package User;
import User.UsersConnection.UsersConnection;
import java.net.Socket;

public class User {

    private String nome;
    private Socket socket;
    private UsersConnection connesione;

    public User(String nome, Socket socket, UsersConnection connesione) {
        this.nome = nome;
        this.socket = socket;
        this.connesione = connesione;
    }

    public String getNome() {
        return nome;
    }

    public Socket getSocket() {
        return socket;
    }

    public UsersConnection getConnesione() {
        return connesione;
    }
}
