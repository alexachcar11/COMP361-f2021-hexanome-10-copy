package serversrc;

import java.util.Optional;

public class Obstacle extends Token {
    // the travel counter placed on the path the obstacle occupies
    private Optional<Token> tokenOnPath = Optional.empty();

    /**
     * @return new Obstacle with CardType OBSTACLE
     */
    public Obstacle() {
        super(CardType.OBSTACLE);
    }

    /**
     * @return type of token occupying path. if only obstacle, return obstacle type
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
