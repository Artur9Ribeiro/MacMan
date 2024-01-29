package MainGame;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            new Game(30, 30).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}