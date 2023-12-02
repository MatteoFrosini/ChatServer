package Managers;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class ResourceManager {
    private static ResourceManager rm;
    private static FileWriter logWriter;
    private static FileWriter loginInfoWriter;
    private static FileWriter chatWriter;
    private ResourceManager() {}
    public static synchronized ResourceManager getInstance(){
        if (rm == null){
            rm = new ResourceManager();
        }return rm;
    }
    public void initData (){
        buildLog();
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
     * Il log viene creato nella cartella {@code "serverLogs"} precedentemente creata dal {@link ServerStructureManager}
     */
    private static void buildLog(){
        DateTimeFormatter formatNomeLog = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        LocalDateTime time = LocalDateTime.now();
        File log = new File(".\\serverLogs\\ServerLog-" + "[" + time.format(formatNomeLog) + "]" + ".txt");
        try {
            logWriter = new FileWriter(log, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogManager.getInstance().logPrint("Log creato");
    }
    public static void writeLogToFile(String toLog){
        try {
            logWriter.write(toLog);
            logWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}