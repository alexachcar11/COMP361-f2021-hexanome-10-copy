package networksrc;

public abstract class ServerAction extends Action{

    String senderName;

    public void setSender(String name) {
        this.senderName = name;
    }
    
}
