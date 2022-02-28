package serversrc;
/*
Abstract class for Tokens
 */

public abstract class Token {
    // type of counter
    private final CardType aCounterType;
    private boolean faceDwon;

    public Token(CardType pCT){
        this.aCounterType = pCT;
    }

    public CardType getCounterType(){
        return aCounterType;
    }

}
