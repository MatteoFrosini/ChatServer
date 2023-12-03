package User;
import User.UsersConnection.UsersConnection;
import java.net.Socket;

public class User {
    private String nome;
    private UsersConnection connesione;
    public User(String nome, UsersConnection connesione) {
        this.nome = nome;
        this.connesione = connesione;
    }
    public String getNome() {
        return nome;
    }

    public UsersConnection getConnesione() {
        return connesione;
    }
}
