package serversrc;

import java.util.Optional;

public class Obstacle extends Token {
    // the travel counter placed on the path the obstacle occupies
    private Optional<Token> tokenOnPath = Optional.empty();

    /**
     * pCT must be one of the barrier types
     * 
     * @param pCT
     */
    public Obstacle(CardType pCT) {
        super(pCT);

    }

    /**
     * @pre !tokenOnPath.isEmpty()
     */
    @Override
    public int cost() {
        assert !tokenOnPath.isEmpty();
        return tokenOnPath.get().cost() * 2;
    }

    /**
     * @return type of token occupying path. if only barrier, return barrier type
     */
    @Override
    public CardType getTokenType() {
        if (tokenOnPath.isEmpty()) {
            return super.getTokenType();
        } else {
            return tokenOnPath.get().getTokenType();
        }
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    public void setTokenOnPath(Token token) {
        // cannot have more than one obstacle
        assert !token.isObstacle();
        tokenOnPath = Optional.ofNullable(token);
    }
}
