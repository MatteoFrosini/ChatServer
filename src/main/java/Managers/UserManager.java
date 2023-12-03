package Managers;

import GUI.ConsoleGUI;
import ServerThreads.ClientThread;

import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {
    private static UserManager userManager;
    private static LogManager logManager = LogManager.getInstance();
    public ArrayList<ClientThread> listaThreadConnessi;
    private UserManager(){
        listaThreadConnessi = new ArrayList<>();
    }
    public static synchronized UserManager getInstance(){
        if(userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }
    public void removeFromList(ClientThread clientThread) {
        listaThreadConnessi.remove(clientThread);
        ConsoleGUI.removeUserFromGUI(clientThread);
        logManager.logPrint("Rimosso " + clientThread.getName() + "(" + clientThread.getUser().getNome() + ")" + " dalla lista user");
    }
    public void newThreadJoin(ClientThread clientThread){
        listaThreadConnessi.add(clientThread);
        ConsoleGUI.addUserToLogGUI(clientThread);
        logManager.logPrint("Aggiunto " + clientThread.getName() + " a lista user");
    }
    public boolean doesClientExist(String user){
        logManager.logPrint("Ricevuta la richiesta di esistenza del client " + user);
        for (ClientThread t : listaThreadConnessi){
            if (t.getUser().getNome().equals(user)) {
                logManager.logPrint(user + " esiste");
                return true;
            }
        }
        logManager.logPrint(user + " non esiste");
        return false;
    }
    public ClientThread getSpecificClient(String user){
        logManager.logPrint("Ricevuta la richiesta dell'oggetto del client " + user);
        for (ClientThread t : listaThreadConnessi){
            if (t.getUser().getNome().equals(user)) {
                logManager.logPrint(user + " Trovato");
                return t;
            }
        }
        logManager.logPrint(user + " non Trovato");
        return null;
    }
    public String getClientListAsString(){
        logManager.logPrint("Processo la richiesta di lista dei client");
        StringBuilder clientListAsString = new StringBuilder();
        clientListAsString.append(listaThreadConnessi.size() +";");
        for (ClientThread t : listaThreadConnessi){
            clientListAsString.append(t.getUser().getNome()).append(";");
        }
        clientListAsString.deleteCharAt(clientListAsString.lastIndexOf(";"));
        logManager.logPrint("Creata la stringa di lista dei client");
        return clientListAsString.toString();
    }
}