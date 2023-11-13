package Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public LocalDateTime time;
    public FileWriter writer;
    public DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
    public Logger() {

    }
    /**
     * Inizializza il logger creando, se non esiste
     * la cartella "serverLogs". Questa cartella
     * contiene tutti i log.del server. Ad ogni
     * avvio viene creato un log diverso.
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
    public void logPrint(String string) {
        try {
            System.out.println(printTime() + " " + string + "\n");
            writer.write(printTime() + " " + string + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String printTime(){
        time = LocalDateTime.now();
        return "[" + time.format(format) + "]";
    }
    public void buildLog(){
        String creationDate = printTime();
        File log = new File("C:\\Users\\Matteo\\Desktop\\Progammi\\Java\\Chat\\ChatServer\\serverLogs\\Log-" + creationDate + ".txt");
        try {
            this.writer = new FileWriter(log, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Log creato");
    }
}
