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
* Don't forget to pull the code in the server machine too :). Just ssh to the server, go to your branch and pull :)
* Create a __Action.java class in networksrc. It implements Action.
* The constructor needs to take a parameter String senderName and save it in a senderName field. You can add more fields but they need to be serializable objects
* Implement isValid and execute. The network will call these on the server so only use classes/methods that are already in serversrc (very important!!! don't even import clientsrc)
* Send an ACK to all players in the specified game (see ExampleServerAction):
```
// get the player + game info
Player playerWhoSent = Player.getPlayerByName(senderName);
ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
// send to all players in the game
ACKManager ackManager = ACKManager.getInstance();
ExampleActionACK actionToSend = new ExampleActionACK(senderName);
ackManager.sentToAllPlayersInGame(actionToSend, playersCurrentGame);
```
* Or send an ACK to the sender only (see TestAction)
```
ACKManager ackManager = ACKManager.getInstance();
TestActionACK actionToSend = new TestActionACK();
ackManager.sendToSender(actionToSend, senderName);
```

* Create a __ActionACK.java class in networksrc. It implements Action.
* Implement isValid and execute. The network will call these on the client so only use classes/methods that are already in clientsrc (very important!! don't even import serversrc)

* Now you can send the message from client to server and wait for a reply:
``` 
ACTION_MANAGER.sendActionAndGetReply(new TestAction(currentUser.getName()));
```

* When it's not your turn, wait for messages from the server. This method will run until it's the player's turn again, then it will exit.
``` 
ACTION_MANAGER.waitForMessages();
```
