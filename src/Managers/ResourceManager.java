package Managers;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResourceManager {
    public static ResourceManager rm;
    private static FileWriter writer;
    private ResourceManager() {
    }
    public static FileWriter getWriter() {
        return writer;
    }
    public static synchronized ResourceManager getInstance(){
        if (rm == null){
            rm = new ResourceManager();
        }return rm;
    }
    public void initData (){
        buildLog();
        buildData();
    }
    private void buildData(){
        if (new File(".\\data\\loginInfo\\loginInfo.txt").exists()){
            LogManager.getInstance().logPrint("File loginInfo.txt già esistente o non creato");
        }else {
            try {
                File loginInfo = new File(".\\data\\loginInfo\\loginInfo.txt");
                loginInfo.createNewFile();
                //non riesco a capire perchè l'istruzione loginInfo.createNewFile(); sia necessaria visto che non viene
                //usata nella creazione degli altri file ma se non la metto il programma si rifiuta categoricamente
                //di creare il file.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            LogManager.getInstance().logPrint("Creato file loginInfo.txt");
        }
    }
    /**
     * Questo metodo costruisce il file di log, viene infatti chiamato una volta
     * per "vita" del server.
     * Quando il metodo viene eseguito creiamo un log con il seguente format per il nome:
     * <ul>
     * <li>{@code "ServerLog-"} : Questa stringa è la prima parte del nome è indica il file come log del Server
     * </li>
     * <li>{@code "[date]"} : Questa parte rappresenta la data di creazione del log ed usa la seguente formattazione : {@code "dd-MM-yyyy HH-mm-ss"} </li>
     * </ul>
     * Il log viene creato nella cartella {@code "serverLogs"} precedentemente creata dal {@link ServerStructureManager}<br><br>
     * //<a href="https://docs.oracle.com/en/java/">note275</a> <br><b>*</b> : Per colpa delle limitazioni su i nomi dei file di Windows non posso usare i {@code ":"} per rappresentare in modo più bellino la data.
     */
    private static void buildLog(){
        DateTimeFormatter formatNomeLog = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        LocalDateTime time = LocalDateTime.now();
        File log = new File(".\\serverLogs\\ServerLog-" + "[" + time.format(formatNomeLog) + "]" + ".txt");
        try {
            writer = new FileWriter(log, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogManager.getInstance().logPrint("Log creato");
    }
}