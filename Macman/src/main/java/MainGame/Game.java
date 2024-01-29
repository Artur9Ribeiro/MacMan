package MainGame;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Game {
    private final TerminalScreen screen;
    private final Arena arena;

    private Font font;

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font changeFont(String path, int size) {
        File fontFile = new File(path);
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        Font loaded = font.deriveFont(Font.PLAIN, size);
        return loaded;
    }

    public Game(int width, int heigt) throws IOException {
        setFont(changeFont("src\\main\\resources\\fonts\\SquareUpdated.ttf", 20));
        AWTTerminalFontConfiguration cfg = new SwingTerminalFontConfiguration(true,
                AWTTerminalFontConfiguration.BoldMode.NOTHING, getFont());
        com.googlecode.lanterna.terminal.Terminal terminal = new DefaultTerminalFactory()
                .setForceAWTOverSwing(true)
                .setInitialTerminalSize(new TerminalSize(width, heigt))
                .setTerminalEmulatorFontConfiguration(cfg)
                .createTerminal();
        screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();

        arena = new Arena(width, heigt);
    }

    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    private void processKey(KeyStroke key) {
        arena.processKey(key);
    }

    public void run() throws IOException {
        int FPS = 20;
        int frameTime = 1000 / FPS;
        long lastMonsterMovement = 0;

        while (true) {
            long startTime = System.currentTimeMillis();

            draw();
            KeyStroke key = screen.pollInput();
            if (key != null) {
                if (key.getKeyType() == KeyType.Character && key.getCharacter() == 'q')
                    screen.close();
                if (key.getKeyType() == KeyType.EOF)
                    break;
                processKey(key);
            }

            if (startTime - lastMonsterMovement > 500) {
                arena.moveMonsters();
                arena.verifyMonsterCollisions();

                lastMonsterMovement = startTime;
            }

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            if (sleepTime > 0) try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }

        }
    }
}
