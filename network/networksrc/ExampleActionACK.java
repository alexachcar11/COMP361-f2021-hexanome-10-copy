package networksrc;

public class ExampleActionACK implements Action{

    // NOTE: the receiverName is not necessary. It's more of a sanity check that the correct Client received the message
    // You can remove it and everything should be ok.

    private String receiverName;

    public ExampleActionACK(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public boolean isValid() {
        // put some validation here
        return true;
    }

    @Override
    public void execute() {
       
        // execute something on the client
        System.out.println("ExampleActionACK received by " + receiverName);
        
    }

    
    
}
