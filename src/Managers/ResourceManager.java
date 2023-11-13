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
    public void initDataFolders(){
        executeChecks();
        buildData();
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
    private boolean dataFolderChek(){
        File dataFolder = new File("data");
        return dataFolder.mkdir();
    }
    private boolean loginInfoCheck(){
        File loginInfo = new File(".\\data\\loginInfo");
        return loginInfo.mkdir();
    }
    private boolean chatDataCheck(){
        File chatDataFolder  = new File(".\\data\\chatData");
        return chatDataFolder.mkdir();
    }
    private void executeChecks(){
        if (dataFolderChek()) {
            logger.logPrint("Cartella dataFolder creata");
        }else {
            logger.logPrint("Cartella dataFolder già esistente o non creata");
        }
        if (loginInfoCheck()){
            logger.logPrint("Cartella loginInfo creata");
        }else {
            logger.logPrint("Cartella loginInfo già esistente o non creata");
        }
        if (chatDataCheck()){
            logger.logPrint("Cartella chatData creata");
        }else {
            logger.logPrint("Cartella chatData già esistente o non creata");
        }
    }
}
