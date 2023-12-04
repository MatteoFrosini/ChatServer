package Constants;
public enum Constants {
    HELLO ("hello"),
    CHATREQUEST ("chatRequest"),
    CHATDATA ("chatData"),
    BYE ("bye"),
    USERLISTREQUEST ("userListRequest"),
    USERLIST ("userList"),
    SWITCHBROADCAST ("switchBroadcast"),
    SWITCHTOUSER ("switchToUser"),
    MSG ("msg"),
    MSGREQUEST ("msgRequest"),
    MSGRECIVEDBROADCAST ("msgRecivedBroadcast");

    private String comandi;
    Constants(String comandi){
        this.comandi = comandi;
    }
    public String toString(){
        return this.comandi;
    }
}
