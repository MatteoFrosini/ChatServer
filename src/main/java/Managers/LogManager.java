package Managers;
import GUI.ConsoleGUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * La classe LogManager ci permette di creare un log per tutte le attività del server.<br>
 * Usando il metodo {@link } siamo capaci di scrivere sul file di log.<br>
 * Tutti i log verranno salvati in una cartella nominata {@code "serverLogs"}.<br>
 * Tutti i log utilizzano il format di denominazione descritto nel metodo {@code buildLog} della classe {@link ResourceManager}.<br>
 * Questa classe implementa i seguenti metodi:
 * <ul>
 *    <li>
 *       {@link #logPrint(String)}
 *    </li>
 *    <li>
 *       {@link #printTime()}
 *    </li>
 * </ul>
 * @author Matteo Frosini
 */
public class LogManager {
    /**
     * Variabile statica necessaria per il desing pattern "Singleton"
     */
    private static LogManager logManager;
    private static ResourceManager resourceManager = ResourceManager.getInstance();
    /**
     * Oggetto {@link DateTimeFormatter} necessario per formattare la data.
     */
    public DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    /**
     * Costruttore della classe {@link LogManager}
     * */
    private LogManager() {}
    /**
     * Metodo statico necessario per il desing pattern "Singleton"
     */
    public static synchronized LogManager getInstance(){
        if(logManager == null) {
            logManager = new LogManager();
        }
        return logManager;
    }
    /**
     * Questo metodo serve per "Loggare" la stringa passata come parametro
     * sia sulla console che sul file di log. La stringa viene preceduta da
     * il tempo in cui viene eseguita la scrittura del Log.
     * @param stringToLog  la stringa da stampare.
     * */
    public synchronized void logPrint(String stringToLog) {
        ConsoleGUI.logGUI(printTime() + "[SERVER]" + " " + stringToLog);
        resourceManager.writeLogToFile(printTime() + "[SERVER]" + " " + stringToLog + "\n");
    }
    /**
     * Ritorna una stringa contenente il tempo attuale alla chiamata del metodo.
     * @return Time - La stringa contenente il tempo attuale, formattato e racchiuso tra parentesi quadre.
     */
    public String printTime(){
        LocalDateTime time = LocalDateTime.now();
        return "[" + time.format(format) + "]";
    }
}