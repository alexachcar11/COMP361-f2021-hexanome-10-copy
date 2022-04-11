package serversrc;

/**
 * This class represents the card showing the travel costs for the different
 * terrains and types of travel cards.
 * 
 * 0 in COST_ARRAY means that the route cannot be traversed with that travel
 * card.
 * 
 * This class is a SINGLETON.
 */

public class CostCard extends AbstractCard {

    private final int[][] COST_ARRAY = {
            { 1, 1, 2, 0, 1, 1, 0, 0 },
            { 1, 1, 2, 1, 2, 2, 0, 0 },
            { 0, 0, 0, 2, 2, 1, 0, 0 },
            { 0, 2, 1, 1, 2, 1, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 1, 2 }
    };
    private static final CostCard INSTANCE = new CostCard();

    private CostCard() {
        super(CardType.COST_CARD);
    }

    public static CostCard getInstance() {
        return INSTANCE;
    }

    /**
     * 
     * @param rT
     * @param cT
     * @return cost, as number of cards of matching type, to travel route
     * @return 0, if cannot travel
     */
    public static int getCost(int rT, int cT) {
        return INSTANCE.COST_ARRAY[rT][cT];
    }
}
