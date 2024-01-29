package MainGame;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Chave extends Element {
    public Chave(int x, int y) {
        super(x, y);
    }
    @Override
    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#019b13"));
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "y");
    }
}
