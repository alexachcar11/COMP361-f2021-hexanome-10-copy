package serversrc;

public class TransportationCounter extends Token {
    // type of counter
    private final CardType aCounterType;
    private boolean faceDown;

    public TransportationCounter(CardType pCT){
        this.aCounterType = pCT;
        this.faceDown = false;
    }

    public CardType getCounterType(){
        return aCounterType;
    }

    public boolean isFaceDown(){
        return this.faceDown;
    }

    public void setFaceDown(){
        this.faceDown = true;
    }

    public void setFaceUp(){
        this.faceDown = false;
    }
}
