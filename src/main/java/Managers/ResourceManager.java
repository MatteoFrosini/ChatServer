package Managers;

import ServerThreads.ClientThread;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ResourceManager {
    private static ResourceManager rm;
    private static FileWriter logWriter;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH-mm-ss");
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
    public static void writeChat(ClientThread sender, ClientThread receiver, String messaggio) {
        try {
            FileWriter chatWriter = new FileWriter(getFile(sender, receiver), true);
            chatWriter.write(printTime() + "[" + sender.getUser().getNome() + "] " + messaggio + ";\n");
            chatWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getChat(ClientThread sender, ClientThread receiver){
        StringBuilder chatData = new StringBuilder();
        int index = 0;
        try {
            Scanner scanner = new Scanner(getFile(sender, receiver));
            while (scanner.hasNext()){
                chatData.append(scanner.nextLine());
                index++;
            }
            chatData.insert(0,index + ";");
            return chatData.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static File getFile(ClientThread sender, ClientThread receiver){
        String pathName = getPathName(sender,receiver);
        if (!(new File(pathName).exists())){
            File chat = new File(pathName);
            try {
                LogManager.getInstance().logPrint("La chat tra " + sender.getUser().getNome() + " e " + receiver.getUser().getNome() + " non esiste");
                chat.createNewFile();
                LogManager.getInstance().logPrint("La chat tra " + sender.getUser().getNome() + " e " + receiver.getUser().getNome() + " è stata creata");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } return new File(pathName);
    }
    private static String getPathName(ClientThread sender, ClientThread receiver){
        return ".\\data\\" +
                ((sender.getUser().getNome().compareTo(receiver.getUser().getNome()) < 0) ?
                        sender.getUser().getNome() + receiver.getUser().getNome() :
                        receiver.getUser().getNome() + sender.getUser().getNome());
    }
    private static String printTime(){
        LocalDateTime time = LocalDateTime.now();
        return "[" + time.format(format) + "]";
    }
}