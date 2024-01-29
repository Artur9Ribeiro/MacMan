package MainGame;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin {
    private Position position;

    public Coin(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return position;
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.YELLOW);
        graphics.setForegroundColor(TextColor.Factory.fromString("#ffffff"));
        graphics.setCharacter(position.getX(), position.getY(), 'f');
    }
}
