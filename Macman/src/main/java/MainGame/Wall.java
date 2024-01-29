package MainGame;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

public class Wall {

    private Position position;
    private char symbol;

    public Wall(int x, int y, char symbol) {
        this.position = new Position(x, y);
        this.symbol = symbol;
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.GREEN);
        graphics.setCharacter(position.getX(), position.getY(), symbol);
    }

    public Position getPosition() {
        return position;
    }

    public char getSymbol() {
        return symbol;
    }
}


