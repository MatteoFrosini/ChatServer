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
    private boolean isDirCreated(String nomeCartella) {
        return new File(nomeCartella).mkdir();
    }
    public void dirState(String nomeCartella){
        if (isDirCreated(nomeCartella)){
            LogManager.getInstance().logPrint("Cartella " + fixName(nomeCartella) + " creata con successo");
        }else {
            LogManager.getInstance().logPrint("Cartella " + fixName(nomeCartella) + " non creata o già esistente");
        }
    }
    public void checkServerDataStructure(List<String> listaCartelle){
        for(String nomeCartella : listaCartelle){
            dirState(nomeCartella);
        }
    }
    public String fixName(String stringToFix){
        StringBuilder sb = new StringBuilder(stringToFix);
        String[] lista = sb.reverse().toString().split("\\\\");
        sb = new StringBuilder(lista[0].toString());
        System.out.println(sb);
        return sb.reverse().toString();
    }
}
