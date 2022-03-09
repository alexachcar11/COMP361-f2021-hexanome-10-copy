package clientsrc;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImageFile;

/**
 * observes the Transportation counter tokens
 */
import serversrc.Token;

public class TokenImage extends MinuetoImageFile implements HitBox {
    private String tokenName;
    private int x;
    private int y;

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public TokenImage(String tName, String url) throws MinuetoFileException {
        super(url);
        this.tokenName = tName;
        this.x = 0;
        this.y = 0;
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
}
