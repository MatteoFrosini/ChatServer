package Managers;

import GUI.ConsoleGUI;
import ServerThreads.ClientThread;

import java.util.ArrayList;
import java.util.HashMap;

public class UserManager {
    private static UserManager userManager;
    private static ArrayList<ClientThread> listaThreadConnessi;
    private static HashMap<String,String> clientsLoginInfo;
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
        ConsoleGUI.removeUserFromGUI(clientThread);
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
    public static void setClientsLoginInfo(HashMap<String, String> clientsLoginInfo) {
        UserManager.clientsLoginInfo = clientsLoginInfo;
        for (String i : clientsLoginInfo.keySet()){
            LogManager.getInstance().logPrint("Chiave :" + i + " Valore : " + clientsLoginInfo.get(i));
        }
    }
    public boolean verifyCreateUser(String user, String password, ClientThread clientThread){
        LogManager.getInstance().logPrint("Analizzando la richesta di creazione di un nuovo utente da parte di " + clientThread.getName());
        if (clientsLoginInfo.containsKey(user)){
            LogManager.getInstance().logPrint("Richiesta rifiutata: questo user già esiste");
            return false;
        } else {
            addNewClient(user,password);
            LogManager.getInstance().logPrint("Richiesta accettata: creato nuovo user " + user);
            return true;
        }
    }
    public void addNewClient(String user, String password){
        clientsLoginInfo.put(user,password);
        ResourceManager.getInstance().addNewClient(user,password);
    }
    public boolean verifyLoginInfo(String user, String password){
        return clientsLoginInfo.get(user).equals(password);
    }
}