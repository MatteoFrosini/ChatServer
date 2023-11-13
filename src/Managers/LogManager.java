package Managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * La classe LogManager ci permette di creare un log per tutte le attività del server.<br>
 * Usando il metodo {@link #logPrint(String)} siamo capaci di scrivere sul file di log.<br>
 * Tutti i log verranno salvati in una cartella nominata {@code "serverLogs"}.<br>
 * Tutti i log utilizzano il format di denominazione descritto nel metodo {@link #buildLog()}.<br>
 * Questa classe è dotata di 3 attributi.
 * <ul>
 * <li>
 *     {@link #time} - {@link LocalDateTime}
 * </li>
 * <li>
 *     {@link #writer} - Il {@link FileWriter}
 * </li>
 * <li>
 *     {@link #format} - Il {@link DateTimeFormatter}
 * </li>
 * </ul>
 * @author Matteo Frosini (<a href="https://docs.oracle.com/en/java/">matte275.ddns.net</a>)
 */
public class LogManager {
    /**
     * Oggetto {@link LocalDateTime} necessario per ottenere il tempo.
     */
    public LocalDateTime time;
    /**
     * Oggetto {@link FileWriter} necessario per modificare il Log creato col
     * metodo {@link #buildLog()}.
     */
    public FileWriter writer;
    /**
     * Oggetto {@link DateTimeFormatter} necessario per formattare la data.
     */
    public DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
    /**
     * Costruttore della classe {@link LogManager}
     * */
    public LogManager() {}
    /**
     * Inizializza il LogManager creando, se non esiste
     * la cartella "serverLogs".
     * */
    public void initLogsFolder (){
        File logFolder = new File("serverLogs");
        if (logFolder.mkdirs()) {
            System.out.println("Cartella serverLogs creata");
        }else {
            System.out.println("Cartella serverLogs già esistente o non creata");
        }
        buildLog();
        this.logPrint("Creato Log");
    }
    /**
     * Questo metodo stampa la striga inserita preceduta dal tempo attuale all'esecuzione.<br>
     * La stringa viene stamapata sia sul file del log sia sulla console del server.<br>
     * Il file del Log è creato col metodo {@link #buildLog()}
     * @param string La stringa da stampare
     */
    public void logPrint(String string) {
        try {
            System.out.println(printTime() + " " + string + "\n");
            writer.write(printTime() + " " + string + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ritorna una stringa contenente il tempo attuale alla chiamata del metodo.
     * @return Time - La stringa contenente il tempo attuale, formattato e racchiuso tra parentesi quadre.
     */
    public String printTime(){
        time = LocalDateTime.now();
        return "[" + time.format(format) + "]";
    }
    /**
     * Questo metodo costruisce il file di log, viene infatti chiamato una volta
     * per "vita" del server.
     * Quando il metodo viene eseguito creiamo un log con il seguente format per il nome:
     * <ul>
     * <li>{@code "ServerLog-"} : Questa stringa è la prima parte del nome è indica il file come log del Server
     * </li>
     * <li>{@code "[date]"} : I log usano il metodo {@link #printTime() printTime()} per assegnarsi la parte secondaria del nome.<br>
     *     Questa parte rappresenta la data di creazione del log ed usa la seguente formattazione* : {@code "dd-MM-yyyy HH-mm-ss"} </li>
     * </ul>
     * Il log viene creato nella cartella {@code "serverLogs"} precedentemente creata col metodo {@link #initLogsFolder()}<br><br>
     * //<a href="https://docs.oracle.com/en/java/">note275</a> <br><b>*</b> : Per colpa delle limitazioni su i nomi dei file di Windows non posso usare i {@code ":"} per rappresentare in modo più bellino la data.
     */
    public void buildLog(){
        File log = new File(".\\serverLogs\\ServerLog-" + printTime() + ".txt");
        try {
            this.writer = new FileWriter(log, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Log creato");
    }
}
