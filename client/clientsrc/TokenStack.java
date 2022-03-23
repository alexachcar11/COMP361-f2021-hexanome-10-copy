package clientsrc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// import serversrc.Token;

public class TokenStack implements Iterable<TokenSprite> {

    private final List<TokenSprite> aTokens;

    // Creates an empty TokenStack.
    public TokenStack() {
        aTokens = new ArrayList<>();
    }

    /**
     * Creates a TokenStack that contains all the tokens in pToken.
     * 
     * @param pList The tokens to initialize the stack with.
     */
    public TokenStack(List<TokenSprite> pList) {
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
    public TokenSprite pop() {
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
    public Iterator<TokenSprite> iterator() {
        return aTokens.iterator();
    }

}
