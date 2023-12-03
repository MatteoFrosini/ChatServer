package Managers;
import java.io.File;
import java.util.List;
/**
 * Questa chat serve per controllare la struttura delle cartelle
 * nel server.
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *    <li>
 *       {@link #createDir(String)}
 *    </li>
 *    <li>
 *       {@link #doesDirExist(String)}
 *    </li>
 *    <li>
 *       {@link #dirState(String)}
 *    </li>
 *    <li>
 *       {@link #checkServerDataStructure(List)}
 *    </li>
 * </ul>
 * @author Matteo Frosini
 * */
public class ServerStructureManager {
    private static ServerStructureManager ssm;
    private ServerStructureManager() {}
    public static ServerStructureManager getInstance(){
        if(ssm == null) {
            ssm = new ServerStructureManager();
        }
        return ssm;
    }
    /**
     * Questo metodo serve per creare le cartelle
     * @param nomeCartella il nome della cartella da creare
     * */
    private void createDir(String nomeCartella) {
        new File(nomeCartella).mkdir();
    }
    /**
     * Questo metodo controlla se la cartella
     * passata per input esiste
     * @param nomeCartella la cartella di cui controllare
     * l'esistenza
     * */
    private boolean doesDirExist(String nomeCartella) {
        return new File(nomeCartella).exists();
    }
    /**
     * Questo metodo controlla se la cartella
     * passata per input esiste con il metodo {@link #doesDirExist(String)}
     * e aggiunge informazioni di debug
     * @param nomeCartella la cartella di cui controllare
     * l'esistenza
     * */
    public void dirState(String nomeCartella){
        if (doesDirExist(nomeCartella)){
            System.out.println("Cartella " + nomeCartella + " non creata o già esistente");
        } else {
            createDir(nomeCartella);
            System.out.println("Cartella " + nomeCartella + " creata con successo");
        }
    }
    /**
     * Questo metodo controlla l'esisteneza e se necessario crea tutte le cartelle che gli vengono passate in input
     * @param listaCartelle la lista delle cartelle da creare
     * */
    public void checkServerDataStructure(List<String> listaCartelle){
        for(String nomeCartella : listaCartelle){
            dirState(nomeCartella);
        }
    }
}