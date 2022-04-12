package clientsrc;

import javax.swing.ImageIcon;

public class SwingTokenSprite extends ImageIcon {
    private String typeString;

    public SwingTokenSprite(TokenSprite sprite) {
        super(sprite.getFileAddress());
        this.typeString = sprite.getTokenName();
    }

    public String getTypeString() {
        return this.typeString;
    }
}
