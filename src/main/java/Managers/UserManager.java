package Managers;

import GUI.ConsoleGUI;
import ServerThreads.ClientThread;

import java.util.ArrayList;

public class UserManager {
    private static UserManager userManager;
    private static ArrayList<ClientThread> listaThreadConnessi;
    private UserManager(){
        listaThreadConnessi = new ArrayList<>();
    }
    public static synchronized UserManager getInstance(){
        if(userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public static void removeFromList(ClientThread clientThread) {
        listaThreadConnessi.remove(clientThread);
        LogManager.getInstance().logPrint("Rimosso " + clientThread.getName() + "(" + clientThread.getUser().getNome() + ")" + " dalla lista user");
    }

    public void newThreadJoin(ClientThread clientThread){
        listaThreadConnessi.add(clientThread);
        ConsoleGUI.addUserToLogGUI(clientThread);
        LogManager.getInstance().logPrint("Aggiunto " + clientThread.getName() + " a lista user");
    }
    public ArrayList<ClientThread> getBroadcast(){
        return listaThreadConnessi;
    }
    public boolean doesClientExist(String user){
        for (ClientThread t : listaThreadConnessi){
            if (t.getUser().getNome().equals(user)) {
                return true;
            }
        }return false;
    }
    public ClientThread getSpecificClient(String user){
        for (ClientThread t : listaThreadConnessi){
            if (t.getUser().getNome().equals(user)) {
                return t;
            }
        }
        return null;
    }
    public String getClientListAsString(){
        StringBuilder clientListAsString = new StringBuilder();
        clientListAsString.append(listaThreadConnessi.size() +";");
        for (ClientThread t : listaThreadConnessi){
            clientListAsString.append(t.getUser().getNome()).append(";");
        }
        clientListAsString.deleteCharAt(clientListAsString.lastIndexOf(";"));
        return clientListAsString.toString();
    }
}