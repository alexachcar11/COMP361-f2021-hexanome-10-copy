package serversrc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// import clientsrc.Token;

public class TokenStack implements Iterable<Token> {

    private final List<Token> aTokens;

    // Creates an empty TokenStack.
    public TokenStack() {
        aTokens = new ArrayList<>();
    }

    /**
     * Creates a TokenStack that contains all the tokens in pToken.
     * 
     * @param pList The tokens to initialize the stack with.
     */
    public TokenStack(List<Token> pList) {
        this();
        aTokens.addAll(pList);
    }

    /**
     * Shuffles TokenStack
     * 
     */
    public void shuffle() {
        Collections.shuffle(aTokens);
    }

    /**
     * Removes the token on top of the stack and returns it.
     *
     * @return The token on top of the stack.
     * @pre !isEmpty()
     */
    public Token pop() {
        assert !isEmpty();
        return aTokens.remove(aTokens.size() - 1);
    }

    /**
     * @return The number of tokens in the stack.
     */
    public int size() {
        return aTokens.size();
    }

    /**
     * @return True if and only if the stack has no cards in it.
     */
    public boolean isEmpty() {
        return aTokens.size() == 0;
    }

    @Override
    public Iterator<Token> iterator() {
        return aTokens.iterator();
    }

    /**
     * 8x all travel counters and 6x obstacles
     * 
     * @return TokenStack with all tokens and obstacles for elfenland
     */
    public static TokenStack getFullTokenStackEL() {
        ArrayList<Token> tokens = new ArrayList<>();
        for (int cT = 0; cT <= CardType.DRAGON.ordinal(); cT++) {
            for (int i = 0; i < 8; i++) {
                tokens.add(new Token(CardType.values()[cT]));
            }
        }
        TokenStack toReturn = new TokenStack(tokens);
        return toReturn;
    }

    public static TokenStack getFullTokenStackEG() {
        // TODO
        return new TokenStack();
    }

    public boolean contains(Object o) {
        return aTokens.contains(o);
    }

    public boolean remove(Object o) {
        return aTokens.remove(o);
    }
}
