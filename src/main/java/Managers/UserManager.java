package Managers;

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
    public void newThreadJoin(ClientThread clientThread){
        listaThreadConnessi.add(clientThread);
    }
    public ArrayList<ClientThread> getBroadcast(){
        return listaThreadConnessi;
    }
    public void getSpecificClient(){
        for (ClientThread t : listaThreadConnessi){

        }
    }
}