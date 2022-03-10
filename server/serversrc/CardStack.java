package serversrc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

// import clientsrc.Card;

public class CardStack implements Iterable<AbstractCard>
{
    private final List<AbstractCard> aCards;
    
    //Creates an empty CardStack.
    public CardStack()
    {
        aCards = new ArrayList<>();
    }
    /**
     * Creates a CardStack that contains all the cards in pCard.
     * 
     * @param pList The cards to initialize the stack with.
     */
    public CardStack(List<AbstractCard> pList){
        this();
        aCards.addAll(pList);
    }

    /**
     * Shuffles CardStack
     * 
     */
    public void shuffle(){
        Collections.shuffle(aCards);
    }
    /**
     * Removes the card on top of the stack and returns it.
     *
     * @return The card on top of the stack.
     * @pre !isEmpty()
     */
    public AbstractCard pop()
    {
        assert !isEmpty();
        return aCards.remove(aCards.size()-1);
    }

    /**
     * @param pIndex The index to peek in the stack.
     * @return The card at the position indicated by pIndex
     * @pre pIndex >= 0 && pIndex < size();
     */
    public AbstractCard peek(int pIndex)
    {
        assert pIndex >= 0 && pIndex < size();
        return aCards.get(pIndex);
    }

    /**
     * @return The number of cards in the stack.
     */
    public int size()
    {
        return aCards.size();
    }

    /**
     * Removes all the cards in the stack.
     */
    public void clear()
    {
        aCards.clear();
    }

    /**
     * @return True if and only if the stack has no cards in it.
     */
    public boolean isEmpty()
    {
        return aCards.size() == 0;
    }

    @Override
    public Iterator<AbstractCard> iterator() {return aCards.iterator();}

    // add card to the card stack
    public void addCard(AbstractCard c){
        this.aCards.add(c);
    }
}