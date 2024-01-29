package MainGame;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.TextColor;

public class Hero {

    private Position position;

    public Hero(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position moveUp() {
        return new Position(position.getX(), position.getY() - 1);
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }

    public Position moveDown() {
        return new Position(position.getX(), position.getY() + 1);
    }

    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.ANSI.YELLOW);
        graphics.setForegroundColor(TextColor.Factory.fromString("#d5e028"));
        graphics.setCharacter(position.getX(), position.getY(), 'm');
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
