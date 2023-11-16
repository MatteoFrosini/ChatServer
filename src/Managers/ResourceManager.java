package Managers;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResourceManager {
    public LogManager logger;
    public FileWriter writer;
    public ResourceManager(LogManager logger) {
        this.logger = logger;
    }
    private void buildData() {
        if (!(new File(".\\data\\loginInfo\\loginInfo.txt").exists())){
            File loginInfo = new File(".\\data\\loginInfo\\loginInfo.txt");
            try {
                this.writer = new FileWriter(loginInfo, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            logger.logPrint("Creato file loginInfo.txt");
        }else {
            logger.logPrint("File loginInfo.txt già esistente o non creato");
        }
    }
}