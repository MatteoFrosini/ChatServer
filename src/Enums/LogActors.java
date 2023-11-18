package Enums;

public enum LogActors {
    SERVER ("[SERVER]"),
    CLIENT_THREADS("[" + " " + "]");
    private final String actor;
    LogActors(String actor) {
        this.actor = actor;
    }
    public String getActor(){
        return actor;
    }
}
