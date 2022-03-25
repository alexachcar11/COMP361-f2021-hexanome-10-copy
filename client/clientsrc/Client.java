package clientsrc;

import java.io.*;
import java.net.*;

import org.minueto.MinuetoEvent;
import org.minueto.MinuetoEventQueue;

import networksrc.Action;
import networksrc.GiveNameAction;
import networksrc.NetworkNode;
import clientsrc.ClientMain;

public class Client implements NetworkNode {
    private Socket aSocket;
    private ObjectOutputStream aObjectOut;
    private ObjectInputStream aObjectIn;
    private String name;
    private Thread listenThread;

    public Client(String pHost, int pPort, String userString) {
        try {
            aSocket = new Socket(pHost, pPort);
            System.out.println("Client is connected!");
            OutputStream aOut = aSocket.getOutputStream();
            InputStream aIn = aSocket.getInputStream();
            aObjectOut = new ObjectOutputStream(aOut);
            aObjectIn = new ObjectInputStream(aIn);
            System.out.println("Client created at host: " + pHost + ", and port: " + pPort);
            this.name = userString;
            this.listenThread = new Thread(() -> listenToServer());
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + pHost);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + pHost);
        }
    }

    /**
     * confirms communication with server via GiveNameAction
     * creates thread to listen for actions to execute
     */
    @Override
    public void start() {
        // notify the server of this client's username
        try {
            aObjectOut.writeObject(new GiveNameAction(name));
            System.out.println("Client create at address: " + this.aSocket.getInetAddress().getHostName());
            this.listenThread.start();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        while (true) {
            if (ClientMain.gui.currentBackground == GUI.Screen.MENU) {
                ClientMain.gui.window.draw(ClientMain.playScreenImage, 0, 0);
                while (ClientMain.entryScreenQueue.hasNext()) {
                    ClientMain.entryScreenQueue.handle();
                }
            } else if (ClientMain.gui.currentBackground == GUI.Screen.LOGIN) {
                ClientMain.gui.window.draw(ClientMain.loginScreenImage, 0, 0);
                while (ClientMain.loginScreenQueue.hasNext()) {
                    ClientMain.loginScreenQueue.handle();
                    try {
                        this.receiveAction();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.err.println(
                                "Failed to receive communication from server. Fatal, terminating application...");
                        e.printStackTrace();
                        System.exit(-1);
                    }
                }

            } else if (ClientMain.gui.currentBackground == GUI.Screen.LOBBY) {
                ClientMain.gui.window.draw(ClientMain.lobbyBackground, 0, 0);
                while (ClientMain.lobbyScreenQueue.hasNext()) {
                    ClientMain.lobbyScreenQueue.handle();
                }

            } else if (ClientMain.gui.currentBackground == GUI.Screen.CREATELOBBY) {
                ClientMain.gui.window.draw(ClientMain.createGameBackground, 0, 0);
                while (ClientMain.createGameQueue.hasNext()) {
                    ClientMain.createGameQueue.handle();
                }

                // display current Mode
                if (ClientMain.modeSel.equals(Mode.ELFENLAND)) {
                    ClientMain.gui.window.draw(ClientMain.modeElfenlandText, 285, 180);
                    // display current rounds option
                    if (ClientMain.numRoundsSel == 3) {
                        ClientMain.gui.window.draw(ClientMain.rounds3Text, 384, 388);
                    } else if (ClientMain.numRoundsSel == 4) {
                        ClientMain.gui.window.draw(ClientMain.rounds4Text, 384, 388);
                    }
                } else if (ClientMain.modeSel.equals(Mode.ELFENGOLD)) {
                    ClientMain.gui.window.draw(ClientMain.modeElfengoldText, 285, 180);
                    // display current Town Gold option
                    if (ClientMain.townGoldOption.equals(TownGoldOption.NO)) {
                        ClientMain.gui.window.draw(ClientMain.townGoldNoText, 388, 389);
                    } else if (ClientMain.townGoldOption.equals(TownGoldOption.YESDEFAULT)) {
                        ClientMain.gui.window.draw(ClientMain.townGoldYesText, 388, 389);
                    } else if (ClientMain.townGoldOption.equals(TownGoldOption.YESRANDOM)) {
                        ClientMain.gui.window.draw(ClientMain.townGoldYesRandText, 388, 389);
                    }
                    // display current Witch option
                    if (ClientMain.witchSel) {
                        ClientMain.gui.window.draw(ClientMain.witchYesText, 178, 458);
                    } else {
                        ClientMain.gui.window.draw(ClientMain.witchNoText, 178, 458);
                    }
                }

                // display current size
                if (ClientMain.numberPlayers == 2) {
                    ClientMain.gui.window.draw(ClientMain.size2Text, 152, 240);
                } else if (ClientMain.numberPlayers == 3) {
                    ClientMain.gui.window.draw(ClientMain.size3Text, 152, 240);
                } else if (ClientMain.numberPlayers == 4) {
                    ClientMain.gui.window.draw(ClientMain.size4Text, 152, 240);
                } else if (ClientMain.numberPlayers == 5) {
                    ClientMain.gui.window.draw(ClientMain.size5Text, 152, 240);
                } else if (ClientMain.numberPlayers == 6) {
                    ClientMain.gui.window.draw(ClientMain.size6Text, 152, 240);
                }

                // display current destination option
                if (ClientMain.destinationTownSel) {
                    ClientMain.gui.window.draw(ClientMain.destinationTownYesText, 389, 310);
                } else {
                    ClientMain.gui.window.draw(ClientMain.destinationTownNoText, 389, 310);
                }

                // display dropdowns
                if (ClientMain.modeDropdownActive) {
                    ClientMain.gui.window.draw(ClientMain.modeDropdownRectangle, 268, 215);
                    ClientMain.gui.window.draw(ClientMain.modeElfenlandText, 285, 231);
                    ClientMain.gui.window.draw(ClientMain.modeElfengoldText, 285, 273);
                } else if (ClientMain.sizeDropdownActive) {
                    ClientMain.gui.window.draw(ClientMain.sizeDropdownRectangle, 142, 275);
                    ClientMain.gui.window.draw(ClientMain.size2Text, 152, 300);
                    ClientMain.gui.window.draw(ClientMain.size3Text, 152, 342);
                    ClientMain.gui.window.draw(ClientMain.size4Text, 152, 384);
                    ClientMain.gui.window.draw(ClientMain.size5Text, 152, 426);
                    ClientMain.gui.window.draw(ClientMain.size6Text, 152, 468);
                } else if (ClientMain.destinationDropdownActive) {
                    ClientMain.gui.window.draw(ClientMain.destinationTownDropdownRectangle, 376,
                            347);
                    ClientMain.gui.window.draw(ClientMain.destinationTownNoText, 389, 359);
                    ClientMain.gui.window.draw(ClientMain.destinationTownYesText, 389, 400);
                } else if (ClientMain.roundsDropdownActive) {
                    ClientMain.gui.window.draw(ClientMain.roundsDropdownRectangle, 375, 418);
                    ClientMain.gui.window.draw(ClientMain.rounds3Text, 384, 435);
                    ClientMain.gui.window.draw(ClientMain.rounds4Text, 384, 477);
                } else if (ClientMain.townGoldDropdownActive) {
                    ClientMain.gui.window.draw(ClientMain.townGoldDropdownRectangle, 375, 418);
                    ClientMain.gui.window.draw(ClientMain.townGoldNoText, 388, 433);
                    ClientMain.gui.window.draw(ClientMain.townGoldYesText, 388, 475);
                    ClientMain.gui.window.draw(ClientMain.townGoldYesRandText, 388, 517);
                } else if (ClientMain.witchDropdownActive) {
                    ClientMain.gui.window.draw(ClientMain.witchDropdownRectangle, 168, 487);
                    ClientMain.gui.window.draw(ClientMain.witchNoText, 178, 500);
                    ClientMain.gui.window.draw(ClientMain.witchYesText, 178, 542);
                }

            } else if (ClientMain.gui.currentBackground == GUI.Screen.CHOOSEBOOT) {
                ClientMain.gui.window.draw(ClientMain.chooseBootBackground, 0, 0);
                while (ClientMain.chooseBootQueue.hasNext()) {
                    ClientMain.chooseBootQueue.handle();
                }

            } else if (ClientMain.gui.currentBackground == GUI.Screen.LOBBYELFENLAND) {
                ClientMain.gui.window.draw(ClientMain.lobbyElfenlandBackground, 0, 0);
                while (ClientMain.elfenlandLobbyQueue.hasNext()) {
                    ClientMain.elfenlandLobbyQueue.handle();
                }

            } else if (ClientMain.gui.currentBackground == GUI.Screen.LOBBYELFENGOLD) {
                ClientMain.gui.window.draw(ClientMain.lobbyElfengoldBackground, 0, 0);
                while (ClientMain.elfenlandLobbyQueue.hasNext()) {
                    ClientMain.elfenlandLobbyQueue.handle();
                }

            } else if (ClientMain.gui.currentBackground == GUI.Screen.ELFENLAND) {
                if (ClientMain.currentGame.getCurrentPhase() == 4 &&
                        ClientMain.currentPlayer.isTurn()) {
                    // mouseHandler to click on route
                    while (ClientMain.placeCounterQueue.hasNext()) {
                        ClientMain.placeCounterQueue.handle();
                    }
                }

                while (ClientMain.elfenlandQueue.hasNext()) {
                    ClientMain.elfenlandQueue.handle();
                }

            } else if (ClientMain.gui.currentBackground == GUI.Screen.ELFENGOLD) {
                ClientMain.gui.window.draw(ClientMain.elfengoldImage, 0, 0);
            }

            // Add a button in the bottom right to pause the music
            if (ClientMain.soundOn) {
                ClientMain.gui.window.draw(ClientMain.soundOffButton, 1000, 745);
            } else {
                ClientMain.gui.window.draw(ClientMain.soundOnButton, 1000, 745);
            }

            ClientMain.WINDOW.render();
        }
    }

    private void listenToServer() {
        try {
            Action action = (Action) this.aObjectIn.readObject();
            if (action.isValid()) {
                action.execute();
            }
        } catch (IOException e) {
            System.err.println("Fatal Error: failed to receive communication from server. Terminating application...");
            e.printStackTrace();
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the host of the Client (i.e. their machine)
     * 
     * @return Socket's address
     */
    public String getHost() {
        return aSocket.getInetAddress().getHostName();
    }

    /**
     * Retrieve the ObjectOutputStream of the Client. This is where we
     * writeObject(ActionToSend)
     * 
     * @return aObjectOut
     */
    public ObjectOutputStream getObjectOutputStream() {
        return aObjectOut;
    }

    /**
     * Retrieve the ObjectInputStream of the Client. This is where we
     * readObject(ActionReceived)
     * 
     * @return aObjectIn
     */
    public ObjectInputStream getObjectInputStream() {
        return aObjectIn;
    }

    /**
     * 
     * blocks thread until action is received
     * Should not be called outside of main thread.
     */
    public void receiveAction() throws IOException, ClassNotFoundException {
        Action action = (Action) this.aObjectIn.readObject();
        if (action.isValid()) {
            action.execute();
        }
    }

    /**
     * Sets a new name of this client.
     * The server is notified.
     * 
     * @param newName new name associated with the client
     */
    public void setName(String newName) {
        // notify the server of this client's username
        try {
            aObjectOut.writeObject(new GiveNameAction(newName));
            System.out.println("gave name " + newName);
            this.name = newName;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
