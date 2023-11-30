package Managers;

import GUI.ConsoleGUI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/**
 * La classe LogManager ci permette di creare un log per tutte le attività del server.<br>
 * Usando il metodo {@link } siamo capaci di scrivere sul file di log.<br>
 * Tutti i log verranno salvati in una cartella nominata {@code "serverLogs"}.<br>
 * Tutti i log utilizzano il format di denominazione descritto nel metodo {@code buildLog} della classe {@link ResourceManager}.<br>
 * Questa classe è dotata di 1 attributo.
 * <ul>
 * <li>
 *     {@link #format} - Il {@link DateTimeFormatter}
 * </li>
 * </ul>
 * @author Matteo Frosini (<a href="https://docs.oracle.com/en/java/">matte275.ddns.net</a>)
 */
public class LogManager {
    /**
     * Variabile statica necessaria per il desing pattern "Singleton"
     */
    private static LogManager logManager;
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

    public synchronized void logPrint(String string) {
        ConsoleGUI.logGUI(printTime() + "[SERVER]" + " " + string);
        ResourceManager.getInstance().writeLogToFile(printTime() + "[SERVER]" + " " + string + "\n");
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