package Managers;

import ServerThreads.ClientThread;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Questa classe si occupa di gestire tutte le operazioni
 * di scrittura e lettura riguardanti i file del server.
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *     <li>
 *        {@link #initData()}
 *     </li>
 *     <li>
 *        {@link #buildLog()}
 *     </li>
 *     <li>
 *        {@link #getChat(ClientThread, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #writeChat(ClientThread, ClientThread, String)}
 *     </li>
 *     <li>
 *        {@link #writeLogToFile(String)}
 *     </li>
 *     <li>
 *        {@link #getFile(ClientThread, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #getPathName(ClientThread, ClientThread)}
 *     </li>
 *     <li>
 *        {@link #printTime()}
 *     </li>
 * </ul>
 * @author Matteo Frosini
 */
public class ResourceManager {
    private static ResourceManager rm;
    private static LogManager logManager = LogManager.getInstance();
    private static FileWriter logWriter;
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("HH-mm-ss");
    private ResourceManager() {}
    public static synchronized ResourceManager getInstance(){
        if (rm == null){
            rm = new ResourceManager();
        }return rm;
    }
    /**
     * Questo metodo compie tutte le funzioni di inizializzazione delle risorse presenti
     * sul server.
     * */
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
        logManager.logPrint("Log creato");
    }
    /**
     * Questo metodo serve per scrivere la stringa passata in input
     * sul file di log.
     * @param stringToLog la stringa da scrivere sul file di log
     */
    public void writeLogToFile(String stringToLog){
        try {
            logWriter.write(stringToLog);
            logWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Questo metodo permette al server di salvare la chat.
     * Questo metodo salva il messaggio passato come parametro
     * sul file di testo della chat tra i due utenti passati come parametri.
     * @param receiver Il thread che riceve il messaggio
     * @param sender Il thread che manda il messaggio
     */
    public void writeChat(ClientThread sender, ClientThread receiver, String messaggio) {
        try {
            FileWriter chatWriter = new FileWriter(getFile(sender, receiver), true);
            logManager.logPrint("Scrivo [" + messaggio + "] nel file di chat " + getPathName(sender, receiver));
            chatWriter.write(printTime() + "[" + sender.getUser().getNome() + "] " + messaggio + ";\n");
            chatWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Questo metodo ritorna la chat salvata sul file sottoforma stringa  <br>
     * Qui c'è un ipotetico esempio di questo formato: <br>
     * chatData:(2);[12-34-56]-Samuele: Ciao come stai?;[12-34-56]-Matteo : Bene!
     * <ul>
     * <li>
     *    chatData: <- Segnala al client che quello che segue è tutta la chat che ha richesto <br>
     * </li>
     * <li>
     *    (2) <- il numero di messaggi salvati dal server nella chat (informazione utile dal
     *    lato client)<br>
     * </li>
     * <li>
     *    ; <- Il simbolo ";" in questo caso serve per segnalare al client che dopo questo
     *    simbolo ci saranno i messaggi della chat <br>
     * </li>
     * <li>
     *    [12-34-56]-Samuele: Ciao come stai? <- Il messaggio <br>
     * </li>
     * <li>
     *    ; <- Il simbolo ";" in questo caso serve per segnalare al client che dopo questo
     *    simbolo ci sara un altro messaggio <br>
     * </li>
     * <li>
     *    [12-34-56]-Matteo : Bene! <- Il messaggio <br>
     * </li>
     * </ul>
     * @param receiver Il thread che riceve il messaggio
     * @param sender Il thread che manda il messaggio
     * @return la chat tra {@code sender} e {@code receiver}
     * */
    public String getChat(ClientThread sender, ClientThread receiver){
        StringBuilder chatData = new StringBuilder();
        int index = 0;
        logManager.logPrint("Inizio a recuperare la chat");
        try {
            Scanner scanner = new Scanner(getFile(sender, receiver));
            while (scanner.hasNext()){
                chatData.append(scanner.nextLine());
                index++;
                logManager.logPrint("Recuperato messaggio numero " + index + " dalla chat");
            }
            logManager.logPrint("Chat recuperata: preparo la stringa per l'invio");
            chatData.insert(0,index + ";");
            chatData.deleteCharAt(chatData.lastIndexOf(";"));
            logManager.logPrint("Preparata ed inviata la stringa");
            return chatData.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Questo metodo controlla l'esistenza del file dove il server si salva la chat.
     * Questo metodo inizia la sua esecuzione generando la path della chat:
     * Se la path esiste, allora vuol dire che il file esiste e quindi il metodo
     * ritorna un oggetto File che fa riferimento al file dove viene salvata la chat;
     * Se la path non esiste, allora vuol dire che il file non esiste e quindi il metodo
     * crea un nuovo file dove iniziare a salvare la chat e ritorna un oggetto
     * File che fa riferimento al file precedentemente creato. <br>
     * @param receiver Il thread che riceve il messaggio
     * @param sender Il thread che manda il messaggio
     * @return Un oggetto File che fa riferimento al file dove viene salvata
     * la chat
     */
    private static File getFile(ClientThread sender, ClientThread receiver){
        String pathName = getPathName(sender,receiver);
        if (!(new File(pathName).exists())){
            File chat = new File(pathName);
            try {
                logManager.logPrint("La chat tra " + sender.getUser().getNome() + " e " + receiver.getUser().getNome() + " non esiste");
                chat.createNewFile();
                logManager.logPrint("La chat tra " + sender.getUser().getNome() + " e " + receiver.getUser().getNome() + " è stata creata");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } return new File(pathName);
    }
    /**
     * Questo metodo serve per ottenere la path del file della chat.
     * Questo metodo genera il nome del file prendendo i due nomi e
     * ordinandogli in modo alfabetico, cosi facendo ogni file viene
     * chiamato in un modo univoco ed è facilmente ritrovabile.
     * @param receiver Il thread che riceve il messaggio
     * @param sender Il thread che manda il messaggio
     * @return Una stringa che fa riferimento alla path del file dove
     * viene salvata la chat
     */
    private static String getPathName(ClientThread sender, ClientThread receiver){
        return ".\\data\\" +
                ((sender.getUser().getNome().compareTo(receiver.getUser().getNome()) < 0) ?
                        sender.getUser().getNome() + receiver.getUser().getNome() :
                        receiver.getUser().getNome() + sender.getUser().getNome());
    }
    /**
     * Ritorna una stringa contenente il tempo attuale alla chiamata del metodo.
     * @return Time - La stringa contenente il tempo attuale, formattato e racchiuso tra parentesi quadre.
     */
    private static String printTime(){
        LocalDateTime time = LocalDateTime.now();
        return "[" + time.format(format) + "]";
    }
}