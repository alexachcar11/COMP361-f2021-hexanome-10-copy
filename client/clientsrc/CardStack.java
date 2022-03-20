package clientsrc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CardStack implements Iterable<CardSprite> {
    private final List<CardSprite> aCards;

    // Creates an empty CardStack.
    public CardStack() {
        aCards = new ArrayList<>();
    }

    /**
     * Creates a CardStack that contains all the cards in pCard.
     * 
     * @param pList The cards to initialize the stack with.
     */
    public CardStack(List<CardSprite> pList) {
        this();
        aCards.addAll(pList);
    }

    /**
     * Removes the card on top of the stack and returns it.
     *
     * @return The card on top of the stack.
     * @pre !isEmpty()
     */
    public CardSprite pop() {
        assert !isEmpty();
        return aCards.remove(aCards.size() - 1);
    }

    /**
     * @param pIndex The index to peek in the stack.
     * @return The card at the position indicated by pIndex
     * @pre pIndex >= 0 && pIndex < size();
     */
    public CardSprite peek(int pIndex) {
        assert pIndex >= 0 && pIndex < size();
        return aCards.get(pIndex);
    }

    /**
     * @return The number of cards in the stack.
     */
    public int size() {
        return aCards.size();
    }

    /**
     * Removes all the cards in the stack.
     */
    public void clear() {
        aCards.clear();
    }

    /**
     * @return True if and only if the stack has no cards in it.
     */
    public boolean isEmpty() {
        return aCards.isEmpty();
    }

    @Override
    public Iterator<CardSprite> iterator() {
        return aCards.iterator();
    }

}