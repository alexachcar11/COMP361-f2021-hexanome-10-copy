# f2021-hexanome-10

[Link to shared Google Doc folder](https://drive.google.com/drive/folders/19WheCCSq9KSggepEXmfFJuBdx57oNWcC?usp=sharing)

Potential chat GUI?: https://www.guru99.com/java-swing-gui.html

## OUR SERVER MACHINE

IP address: 144.217.155.147 or elfenland.simui.com

Connect:
```
ssh elfen@elfenland.simui.com
```

Start docker: 
```
docker start ls-db
```

Start the lobby service: 
```
mvn clean package spring-boot:run
```

Test the lobby service connection: http://elfenland.simui.com:4242/api/online

Installed:
* git
* openjdk-17 jre
* openjdk-17 jdk
* docker
* maven 3.8.4

## How to send message through the network
* Create a __Action.java class in networksrc. It implements Action.
* The constructor needs to take a parameter String senderName and save it in a senderName field. You can add more fields but they need to be serializable objects
* Implement isValid and execute. The network will call these on the server so only use classes/methods that are already in serversrc.
* Send an ACK to the client in execute():
```
// send an ACK to all clients in the game
try {
    Server serverInstance = Server.getInstance();
    for (Player p : playersCurrentGame.getAllPlayers()) {
        String playersName = p.getName();
        // get the player's socket
        ClientTuple clientTupleToNotify = serverInstance.getClientTupleByUsername(playersName);
        // get the socket's output stream (don't forget to import java.io.ObjectOutputStream;)
        ObjectOutputStream objectOutputStream = clientTupleToNotify.output();
        // send the acknowledgment
        objectOutputStream.writeObject(new ExampleActionACK(playersName));
    }
} catch (IOException e) { // dont forget to import java.io.IOException;
    System.err.println("IOException in ExampleServerAction.execute().");
}
```
```
// or send an ACK to the sender only
try {
  // get the senderName's socket
  Server serverInstance = Server.getInstance();
  ClientTuple clientTupleToNotify = serverInstance.getClientTupleByUsername(senderName);
  // get the socket's output stream
  ObjectOutputStream objectOutputStream = clientTupleToNotify.output();
  objectOutputStream.writeObject(new TestActionACK(senderName));
} catch (IOException e) {
  System.err.println("IOException in TestAction.execute().");
}
```

* Create a __ActionACK.java class in networksrc. It implements Action.
* Implement isValid and execute. The network will call these on the client so only use classes/methods that are already in clientsrc.

*Now you can send the message from client to server:
``` 
// send TestAction
ObjectOutputStream out = currentUser.getClient().getObjectOutputStream();
out.writeObject(new TestAction(currentUser.getName()));
//System.out.println("sent action from main. waiting for reply...");
```

* Now you can wait for an ACK from the server to client:
``` 
// wait for reply
ObjectInputStream in = currentUser.getClient().getObjectInputStream();
boolean noAnswer = true;
while (noAnswer) {
    Action actionIn = (Action) in.readObject();
    if (actionIn != null) {
        // action received
        if (actionIn.isValid()) {
            actionIn.execute();
        }
        noAnswer = false;
    }   
}
```
