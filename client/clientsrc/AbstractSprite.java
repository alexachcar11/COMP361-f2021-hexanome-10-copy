package clientsrc;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImageFile;

/**
 * Abstract class representing our in-game sprites.
 * 
 * i.e. sprites for travel cards, travel counters, etc
 */
public abstract class AbstractSprite extends MinuetoImageFile implements Clickable {

    protected int x;
    protected int y;

    public AbstractSprite(String arg0) throws MinuetoFileException {
        super(arg0);
        this.x = 0;
        this.y = 0;
    }

    public abstract String getFileAddress();

    public abstract String getTokenName();

    @Override
    public boolean hasCollidePoint(int x, int y) {
        int maxX = this.x + this.getWidth();
        int maxY = this.y + this.getHeight();
        return x >= this.x && x <= maxX && y >= this.y && y <= maxY;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @return x coord
     */
    public int getX() {
        return this.x;
    }

    /**
     * 
     * @return y coord
     */
    public int getY() {
        return this.y;
    }
}
