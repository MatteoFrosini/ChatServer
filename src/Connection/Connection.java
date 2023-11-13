package Connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * Questa classe serve alla classe {@link User} per interfacciarsi nella comunicazione client-server. <br>
 * Questa classe è dotata di 3 attributi
 * <ul>
 * <li>
 *     {@link #s} - La {@link Socket}
 * </li>
 * <li>
 *     {@link #in} - Il {@link BufferedReader}
 * </li>
 * <li>
 *     {@link #out} - La {@link DataOutputStream}
 * </li>
 * </ul>
 */
public class Connection {
    private Socket s;
    private BufferedReader in;
    private DataOutputStream out;

    public Connection(Socket s) {
        this.s = s;
        try {
            this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.out = new DataOutputStream(s.getOutputStream());
        } catch (Exception e) {
            System.out.println("Errore istanza Connessione: " + e.getMessage());
        }
    }
    public BufferedReader getIn() {
        return this.in;
    }
    public String getLine(){
        try {
            return this.in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public DataOutputStream getOut() {
        return this.out;
    }
    public void send(String data){
        try {
            this.out.writeBytes(data + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void close() {
        try {
            s.close();
        } catch (Exception e) {}
    }

    public String compose(String cmd, String data){
        return cmd + ":" + data;
    }
}
