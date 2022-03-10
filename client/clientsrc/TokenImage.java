package clientsrc;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

public class TokenImage extends MinuetoImageFile implements HitBox {

    private String tokenName;
    private int x;
    private int y;
    private MinuetoImage mediumImage;
    private MinuetoImage smallImage;

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public TokenImage(CardType cT) throws MinuetoFileException {
        super("images/elfenroads-sprites/M0" + (cT.ordinal() + 1) + "small.png");
        this.tokenName = cT.toString();
        this.x = 0;
        this.y = 0;
        mediumImage = new MinuetoImageFile("images/elfenroads-sprites/M0" + (cT.ordinal() + 1) + "medium.png");
        smallImage = new MinuetoImageFile("images/elfenroads-sprites/M0" + (cT.ordinal() + 1) + "small.png");
    }

    public String getFileAdress() {
        CardType cT = this.getCardType();
        return "images/elfenroads-sprites/M0" + (cT.ordinal() + 1) + "small.png";
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean hasCollidePoint(int x, int y) {
        int maxX = this.x + this.getWidth();
        int maxY = this.y + this.getHeight();
        return x >= this.x && x <= maxX && y >= this.y && y <= maxY;
    }

    public static TokenImage getTokenImageByString(String tokenString)
            throws MinuetoFileException, IllegalArgumentException {
        for (CardType cT : CardType.values()) {
            if (cT.toString().equals(tokenString)) {
                return new TokenImage(cT);
            }
        }
        throw new IllegalArgumentException(tokenString + " is not a valid type for a token.");
    }

    public CardType getCardType() throws IllegalArgumentException {
        for (CardType cT : CardType.values()) {
            if (cT.toString().equals(this.tokenName)) {
                return cT;
            }
        }
        throw new IllegalArgumentException(this.tokenName + " is not a valid type for a token.");
    }
}
