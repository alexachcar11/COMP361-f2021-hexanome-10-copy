package clientsrc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


import org.minueto.MinuetoColor;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoCircle;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

import networksrc.Action;
import serversrc.CardType;
import serversrc.Token;

public class ActionManager {

    // singleton
    private static ActionManager INSTANCE = new ActionManager();
    List<Player> players;

    private ActionManager() {

    }

    /**
     * Returns the singleton INSTANCE of the ActionManager
     * 
     * @return INSTANCE
     */
    public static ActionManager getInstance() {
        return INSTANCE;
    }

    /**
     * Waits for a message from the Server. This runs while the session is NOT
     * launchable.
     * When a message is received, execute it if it's valid, then wait for another
     * message.
     * 
     * @throws MinuetoFileException
     */
    public void waitForPlayersAsCreator() throws MinuetoFileException {
        // WAIT FOR PLAYERS
        ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();

        // wait until launched
        while (!ClientMain.currentSession.isLaunchable()) {
            Action actionIn = null;
            try {
                actionIn = (Action) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (actionIn != null) {
                // MESSAGE RECEIVED
                if (actionIn.isValid()) {
                    actionIn.execute();
                }
                // update gui
                ClientMain.gui.window.render();
            }
        }
    }

    /**
     * Waits for a message from the Server. This runs while the session is NOT
     * launched.
     * When a message is received, execute it if it's valid, then wait for another
     * message.
     * 
     * @throws MinuetoFileException
     */
    public void waitForPlayers() throws MinuetoFileException {
        // WAIT FOR PLAYERS
        ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();

        // wait until launched
        while (!ClientMain.currentSession.isLaunched()) {
            Action actionIn = null;
            try {
                actionIn = (Action) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (actionIn != null) {
                // MESSAGE RECEIVED
                if (actionIn.isValid()) {
                    actionIn.execute();
                }
                // show wait for launch image
                if (ClientMain.currentSession.isLaunchable()) {
                    ClientMain.gui.window.draw(ClientMain.waitingForLaunch, 822, 580);
                }
                // update gui
                ClientMain.gui.window.render();
            }
        }
    }

    /**
     * Waits for a message from the Server. This runs while it is NOT the player's
     * turn.
     * When a message is received, execute it if it's valid, then wait for another
     * message.
     * 
     * @throws MinuetoFileException
     */
    public void waitForMessages() throws MinuetoFileException {
        // WAIT FOR A MESSAGE
        ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();
        while (!ClientMain.currentPlayer.isTurn()) {
            Action actionIn = null;
            try {
                actionIn = (Action) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (actionIn != null) {
                // MESSAGE RECEIVED
                if (actionIn.isValid()) {
                    actionIn.execute();
                }
            }

            players = ClientMain.currentGame.getPlayers();
            players.remove(ClientMain.currentPlayer);
            // List<Player> listOfPlayers = players;

            ClientMain.gui.window.draw(ClientMain.elfenlandImage, 0, 0);

            // draw Cards text
            MinuetoText cardsText = new MinuetoText("Cards:", ClientMain.fontArial22Bold, MinuetoColor.BLACK);
            ClientMain.gui.window.draw(cardsText, 145, 600);

            // draw Tokens text
            MinuetoText tokensText = new MinuetoText("Tokens:", ClientMain.fontArial22Bold, MinuetoColor.BLACK);
            ClientMain.gui.window.draw(tokensText, 580, 600);

            // draw line between the text:
            ClientMain.gui.window.drawLine(MinuetoColor.BLACK, 570, 602, 570, 763);

            // draw indication on all of the routes
            MinuetoCircle indicator = new MinuetoCircle(10, MinuetoColor.GREEN, true);
            ClientMain.gui.window.draw(indicator, 90, 55);
            ClientMain.gui.window.draw(indicator, 38, 189);
            ClientMain.gui.window.draw(indicator, 169, 126);
            ClientMain.gui.window.draw(indicator, 121, 162);
            ClientMain.gui.window.draw(indicator, 45, 318);
            ClientMain.gui.window.draw(indicator, 78, 307);
            ClientMain.gui.window.draw(indicator, 119, 231);
            ClientMain.gui.window.draw(indicator, 125, 282);
            ClientMain.gui.window.draw(indicator, 246, 130);
            ClientMain.gui.window.draw(indicator, 259, 210);
            ClientMain.gui.window.draw(indicator, 194, 424);
            ClientMain.gui.window.draw(indicator, 165, 510);
            ClientMain.gui.window.draw(indicator, 283, 442);
            ClientMain.gui.window.draw(indicator, 378, 545);
            ClientMain.gui.window.draw(indicator, 279, 57);
            ClientMain.gui.window.draw(indicator, 381, 199);
            ClientMain.gui.window.draw(indicator, 241, 342);
            ClientMain.gui.window.draw(indicator, 354, 401);
            ClientMain.gui.window.draw(indicator, 368, 462);
            ClientMain.gui.window.draw(indicator, 451, 467);
            ClientMain.gui.window.draw(indicator, 563, 431);
            ClientMain.gui.window.draw(indicator, 577, 483);
            ClientMain.gui.window.draw(indicator, 584, 557);
            ClientMain.gui.window.draw(indicator, 728, 489);
            ClientMain.gui.window.draw(indicator, 620, 171);
            ClientMain.gui.window.draw(indicator, 726, 373);
            ClientMain.gui.window.draw(indicator, 635, 252);
            ClientMain.gui.window.draw(indicator, 49, 450);
            ClientMain.gui.window.draw(indicator, 244, 551);
            ClientMain.gui.window.draw(indicator, 621, 423);
            ClientMain.gui.window.draw(indicator, 443, 219);
            ClientMain.gui.window.draw(indicator, 376, 255);
            ClientMain.gui.window.draw(indicator, 302, 311);
            ClientMain.gui.window.draw(indicator, 699, 395);
            ClientMain.gui.window.draw(indicator, 488, 80);
            ClientMain.gui.window.draw(indicator, 383, 131);
            ClientMain.gui.window.draw(indicator, 302, 313);
            ClientMain.gui.window.draw(indicator, 555, 141);
            ClientMain.gui.window.draw(indicator, 717, 291);
            ClientMain.gui.window.draw(indicator, 450, 339);
            ClientMain.gui.window.draw(indicator, 489, 254);
            ClientMain.gui.window.draw(indicator, 526, 390);
            ClientMain.gui.window.draw(indicator, 364, 315);
            ClientMain.gui.window.draw(indicator, 687, 174);
            ClientMain.gui.window.draw(indicator, 510, 310);
            ClientMain.gui.window.draw(indicator, 149, 102);
            ClientMain.gui.window.draw(indicator, 533, 185);
            ClientMain.gui.window.draw(indicator, 438, 549);
            ClientMain.gui.window.draw(indicator, 536, 185);
            ClientMain.gui.window.draw(indicator, 88, 439);

            MinuetoText passTurnText = new MinuetoText("PASS", ClientMain.fontArial20, MinuetoColor.BLACK);
            // ClientMain.gui.window.draw(passTurnText, arg1, arg2);

            List<TokenImage> listOfTokens = ClientMain.currentPlayer.getTokensInHand();
            List<TravelCard> listOfCards = ClientMain.currentPlayer.getCardsInHand();

            System.out.println("YOU HAVE " + listOfCards.size() + " CARDS!");
            System.out.println("YOU HAVE " + listOfTokens.size() + " TOKENS!");

            // organize tokens in inventory
            if (listOfTokens.size() == 1) {
                MinuetoImage p1 = listOfTokens.get(0);
                ClientMain.gui.window.draw(p1, 642, 640);
            } else if (listOfTokens.size() == 2) {
                MinuetoImage p1 = listOfTokens.get(0);
                MinuetoImage p2 = listOfTokens.get(1);
                ClientMain.gui.window.draw(p1, 587, 640);
                ClientMain.gui.window.draw(p2, 695, 640);
            } else if (listOfTokens.size() == 3) {
                MinuetoImage p1 = listOfTokens.get(0);
                MinuetoImage p2 = listOfTokens.get(1);
                MinuetoImage p3 = listOfTokens.get(2);
                ClientMain.gui.window.draw(p1, 615, 636);
                ClientMain.gui.window.draw(p2, 709, 636);
                ClientMain.gui.window.draw(p3, 663, 698);
            } else if (listOfTokens.size() == 4) {
                MinuetoImage p1 = listOfTokens.get(0);
                MinuetoImage p2 = listOfTokens.get(1);
                MinuetoImage p3 = listOfTokens.get(2);
                MinuetoImage p4 = listOfTokens.get(3);
                ClientMain.gui.window.draw(p1, 615, 636);
                ClientMain.gui.window.draw(p2, 709, 636);
                ClientMain.gui.window.draw(p3, 615, 698);
                ClientMain.gui.window.draw(p4, 709, 698);

            } else if (listOfTokens.size() == 5) {
                MinuetoImage p1 = listOfTokens.get(0);
                MinuetoImage p2 = listOfTokens.get(1);
                MinuetoImage p3 = listOfTokens.get(2);
                MinuetoImage p4 = listOfTokens.get(3);
                MinuetoImage p5 = listOfTokens.get(4);
                ClientMain.gui.window.draw(p1, 592, 636);
                ClientMain.gui.window.draw(p2, 663, 636);
                ClientMain.gui.window.draw(p3, 734, 636);
                ClientMain.gui.window.draw(p4, 615, 698);
                ClientMain.gui.window.draw(p5, 709, 698);
            }

            // organize cards in inventory
            if (listOfCards.size() == 1) {
                MinuetoImage p1 = listOfCards.get(0).getMediumImage();
                ClientMain.gui.window.draw(p1, 314, 634);
            } else if (listOfCards.size() == 2) {
                MinuetoImage p1 = listOfCards.get(0).getMediumImage();
                MinuetoImage p2 = listOfCards.get(1).getMediumImage();
                ClientMain.gui.window.draw(p1, 258, 634);
                ClientMain.gui.window.draw(p2, 370, 634);
            } else if (listOfCards.size() == 3) {
                MinuetoImage p1 = listOfCards.get(0).getMediumImage();
                MinuetoImage p2 = listOfCards.get(1).getMediumImage();
                MinuetoImage p3 = listOfCards.get(2).getMediumImage();
                ClientMain.gui.window.draw(p1, 202, 634);
                ClientMain.gui.window.draw(p2, 314, 634);
                ClientMain.gui.window.draw(p3, 426, 634);
            } else if (listOfCards.size() == 4) {
                MinuetoImage p1 = listOfCards.get(0).getMediumImage();
                MinuetoImage p2 = listOfCards.get(1).getMediumImage();
                MinuetoImage p3 = listOfCards.get(2).getMediumImage();
                MinuetoImage p4 = listOfCards.get(3).getMediumImage();
                ClientMain.gui.window.draw(p1, 153, 634);
                ClientMain.gui.window.draw(p2, 261, 634);
                ClientMain.gui.window.draw(p3, 369, 634);
                ClientMain.gui.window.draw(p4, 477, 634);
            } else if (listOfCards.size() == 5) {
                MinuetoImage p1 = listOfCards.get(0).getMediumImage();
                MinuetoImage p2 = listOfCards.get(1).getMediumImage();
                MinuetoImage p3 = listOfCards.get(2).getMediumImage();
                MinuetoImage p4 = listOfCards.get(3).getMediumImage();
                MinuetoImage p5 = listOfCards.get(4).getMediumImage();
                ClientMain.gui.window.draw(p1, 150, 634);
                ClientMain.gui.window.draw(p2, 232, 634);
                ClientMain.gui.window.draw(p3, 314, 634);
                ClientMain.gui.window.draw(p4, 396, 634);
                ClientMain.gui.window.draw(p5, 478, 634);
            } else if (listOfCards.size() == 6) {
                MinuetoImage p1 = listOfCards.get(0).getSmallImage();
                MinuetoImage p2 = listOfCards.get(1).getSmallImage();
                MinuetoImage p3 = listOfCards.get(2).getSmallImage();
                MinuetoImage p4 = listOfCards.get(3).getSmallImage();
                MinuetoImage p5 = listOfCards.get(4).getSmallImage();
                MinuetoImage p6 = listOfCards.get(5).getSmallImage();
                ClientMain.gui.window.draw(p1, 235, 605);
                ClientMain.gui.window.draw(p2, 348, 605);
                ClientMain.gui.window.draw(p3, 461, 605);
                ClientMain.gui.window.draw(p4, 235, 685);
                ClientMain.gui.window.draw(p5, 348, 685);
                ClientMain.gui.window.draw(p6, 461, 685);
            } else if (listOfCards.size() == 7) {
                MinuetoImage p1 = listOfCards.get(0).getSmallImage();
                MinuetoImage p2 = listOfCards.get(1).getSmallImage();
                MinuetoImage p3 = listOfCards.get(2).getSmallImage();
                MinuetoImage p4 = listOfCards.get(3).getSmallImage();
                MinuetoImage p5 = listOfCards.get(4).getSmallImage();
                MinuetoImage p6 = listOfCards.get(5).getSmallImage();
                MinuetoImage p7 = listOfCards.get(6).getSmallImage();
                ClientMain.gui.window.draw(p1, 235, 605);
                ClientMain.gui.window.draw(p2, 318, 605);
                ClientMain.gui.window.draw(p3, 414, 605);
                ClientMain.gui.window.draw(p4, 235, 685);
                ClientMain.gui.window.draw(p5, 318, 685);
                ClientMain.gui.window.draw(p6, 414, 685);
                ClientMain.gui.window.draw(p7, 510, 646);
            } else if (listOfCards.size() == 8) {
                MinuetoImage p1 = listOfCards.get(0).getSmallImage();
                MinuetoImage p2 = listOfCards.get(1).getSmallImage();
                MinuetoImage p3 = listOfCards.get(2).getSmallImage();
                MinuetoImage p4 = listOfCards.get(3).getSmallImage();
                MinuetoImage p5 = listOfCards.get(4).getSmallImage();
                MinuetoImage p6 = listOfCards.get(5).getSmallImage();
                MinuetoImage p7 = listOfCards.get(6).getSmallImage();
                MinuetoImage p8 = listOfCards.get(7).getSmallImage();
                ClientMain.gui.window.draw(p1, 222, 605);
                ClientMain.gui.window.draw(p2, 318, 605);
                ClientMain.gui.window.draw(p3, 414, 605);
                ClientMain.gui.window.draw(p4, 510, 605);
                ClientMain.gui.window.draw(p5, 222, 685);
                ClientMain.gui.window.draw(p6, 318, 685);
                ClientMain.gui.window.draw(p7, 414, 685);
                ClientMain.gui.window.draw(p8, 510, 685);
            }

            // draw circle for the current turn
            MinuetoCircle roundNumCircle = new MinuetoCircle(20, MinuetoColor.WHITE, true);
            ClientMain.gui.window.draw(roundNumCircle, 792, 562);
            int roundNumber = 1;
            if (roundNumber == 1) {
                MinuetoText firstRound = new MinuetoText("1", ClientMain.fontArial22Bold, MinuetoColor.BLACK);
                ClientMain.gui.window.draw(firstRound, 806, 570);
            } else if (roundNumber == 2) {
                MinuetoText secondRound = new MinuetoText("2", ClientMain.fontArial22Bold, MinuetoColor.BLACK);
                ClientMain.gui.window.draw(secondRound, 806, 570);
            } else if (roundNumber == 3) {
                MinuetoText thirdRound = new MinuetoText("3", ClientMain.fontArial22Bold, MinuetoColor.BLACK);
                ClientMain.gui.window.draw(thirdRound, 806, 570);
            } else if (roundNumber == 4) {
                MinuetoText fourthRound = new MinuetoText("4", ClientMain.fontArial22Bold, MinuetoColor.BLACK);
                ClientMain.gui.window.draw(fourthRound, 806, 570);
            } else if (roundNumber == 5) {
                MinuetoText fifthRound = new MinuetoText("5", ClientMain.fontArial22Bold, MinuetoColor.BLACK);
                ClientMain.gui.window.draw(fifthRound, 806, 570);
            }

            int numberPlayers = players.size();

            for(int i = 0; i < numberPlayers; i++) { 
                // Player opponent = players.get(i);
                int xName = 835;
                int yName = 70 + (i * 92);

                // MinuetoText pName = new MinuetoText(opponent.getName(), fontArial20,
                // opponent.getColor());
                MinuetoRectangle playerBackground = new MinuetoRectangle(190, 85, MinuetoColor.WHITE, true);
                ClientMain.gui.window.draw(playerBackground, xName - 10, yName - 10);
                
                MinuetoText pName = new MinuetoText(players.get(0).getName(), ClientMain.fontArial20, MinuetoColor.BLACK);
                ClientMain.gui.window.draw(pName, xName, yName);
                MinuetoText seeInv = new MinuetoText("See Inventory", ClientMain.fontArial20, MinuetoColor.BLACK);
                ClientMain.gui.window.draw(seeInv, xName + 25, yName + 35);
            }
            
            //HARDCODED TOKEN ON THE MAP 
            Token testToken = new Token(CardType.DRAGON);
            MinuetoImage testTokImage = testToken.getSmallImage();
            ClientMain.gui.window.draw(testTokImage, 368, 462);
            
            for(Player p: players) { 
                p.drawBoot();
            }
            ClientMain.currentPlayer.drawBoot();

            // update gui
            ClientMain.gui.window.render();
        }

    }

    /**
     * Sends the action to the Server and waits for a reply. When the reply is
     * received, it is executed if it is valid.
     * 
     * @param action action to send to the user. Don't forget to set senderName in
     *               the action parameters.
     */
    public Action sendActionAndGetReply(Action action) {
        try {
            // SEND TEST
            ObjectOutputStream out = ClientMain.currentUser.getClient().getObjectOutputStream();
            out.writeObject(action);

            // WAIT FOR REPLY
            ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();
            boolean noAnswer = true;
            while (noAnswer) {
                Action actionIn = null;
                actionIn = (Action) in.readObject();
                if (actionIn != null) {
                    // REPLY RECEIVED
                    if (actionIn.isValid()) {
                        actionIn.execute();
                    }
                    noAnswer = false;
                    return actionIn;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
