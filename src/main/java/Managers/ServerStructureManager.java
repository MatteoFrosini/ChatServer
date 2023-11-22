package Managers;
import java.io.File;
import java.util.List;

public class ServerStructureManager {
    private static ServerStructureManager ssm;
    private ServerStructureManager() {}
    public static ServerStructureManager getInstance(){
        if(ssm == null) {
            ssm = new ServerStructureManager();
        }
        return ssm;
    }
    private void createDir(String nomeCartella) {
        new File(nomeCartella).mkdir();
    }
    private boolean doesDirExist(String nomeCartella) {
        return new File(nomeCartella).exists();
    }
    public void dirState(String nomeCartella){
        if (doesDirExist(nomeCartella)){
            System.out.println("Cartella " + fixName(nomeCartella) + " non creata o già esistente");
        } else {
            createDir(nomeCartella);
            System.out.println("Cartella " + fixName(nomeCartella) + " creata con successo");
        }
    }
    public void checkServerDataStructure(List<String> listaCartelle){
        for(String nomeCartella : listaCartelle){
            dirState(nomeCartella);
        }
    }
    public String fixName(String stringToFix){
        String[] lista = stringToFix.split("\\\\");
        return lista[lista.length-1];
    }
}