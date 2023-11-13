package Managers;
import java.io.File;
public class ResourceManager {
    public LogManager logger;
    public ResourceManager(LogManager logger) {
        this.logger = logger;
    }
    public void initLogsFolder (){
        File dataFolder = new File("data");
        if (dataFolder.mkdirs()) {
            System.out.println("Cartella dataFolder creata");
        }else {
            System.out.println("Cartella dataFolder già esistente o non creata");
        }
        buildData();
        logger.logPrint("File data up to date");
    }
    private void buildData() {
        File loginInfo = new File(".\\data\\loginInfo.txt");
    }
}
