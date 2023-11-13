package User;

import Connection.Connection;

import java.net.Socket;

public class User {

    private String nome;
    private Socket socket;
    private Connection connesione;

    public User(String nome, Socket socket, Connection connesione) {
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

    public Connection getConnesione() {
        return connesione;
    }
}
