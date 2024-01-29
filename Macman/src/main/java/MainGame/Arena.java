package MainGame;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {

    private final Hero hero;
    private final int width;
    private final int height;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;
    private List<Chave> chaves;
    private boolean isKey;
    private int level;
    private int pontos;

    private static final TerminalPosition SCORE_POSITION = new TerminalPosition(0, 0);
    private static final TextColor SCORE_COLOR = TextColor.ANSI.WHITE;
    private static int highScore = 0;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.level = 1;

        hero = new Hero(width / 2, height / 2);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
        this.chaves = new ArrayList<>();
        isKey = false;
        pontos = 0;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#01579B"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        hero.draw(graphics);

        for (Wall wall : walls) wall.draw(graphics);

        for (Coin coin : coins) coin.draw(graphics);

        for (Monster monster : monsters) monster.draw(graphics);

        for (Chave chave : chaves) chave.draw(graphics);

        drawScore(graphics);

        // Display the level in the top-right corner
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(width - 8, 0, "NIVEL: " + getLevel());
    }


    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Position position = new Position(x, y);
                if (!collidesWithWalls(position)) {
                    coins.add(new Coin(position.getX(), position.getY()));
                }
            }
        }

        return coins;
    }


    private List<Chave> createChave() {
        isKey = true;
        Random random = new Random();
        ArrayList<Chave> chaves = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Chave newChave;
            do {
                newChave = new Chave(random.nextInt(width - 2) + 1, random.nextInt(height - 2));
            } while (collidesWithWalls(newChave.getPosition()));
            chaves.add(newChave);
        }
        return chaves;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < (2 * level); i++) {
            Monster newMonster;
            do {
                newMonster = new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2));
            } while (collidesWithWalls(newMonster.getPosition()));
            monsters.add(newMonster);
        }
        return monsters;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        // Mapa
        String mapa =
                        "###########################################################################################" +
                        "                            ##" +
                        " # ###### ###### ########## ##" +
                        " # ###### ######            ##" +
                        " # ######        ####### ## ##" +
                        " # ###### ###### ####### ## ##" +
                        " #        ###### ####### ## ##" +
                        " ######## ###### ####### ## ##" +
                        " ######## ######         ## ##" +
                        " ######## ###### ####### ## ##" +
                        "                 ####### ## ##" +
                        " ### #### ###### ####### ## ##" +
                        " ### #### ###### ####### ## ##" +
                        " ### ####                   ##" +
                        " ### #### ###### ####### #####" +
                        " ### ####           #### #####" +
                        "          ##### ### #### #####" +
                        " ## ########### ### ####   ###" +
                        " ## ########### ### ###### ###" +
                        " ##        #### ### ###### ###" +
                        " ## ###### #### ### ###### ###" +
                        " ## ######          ###### ###" +
                        " ## ####   #### ###        ###" +
                        " ## #### ###### ####### ######" +
                        "         ###### #######     ##" +
                        " ## #### ###### #######   ####" +
                        " ## ####                  ####" +
                        "##############################";



        for (int i = 0; i < mapa.length(); i++) {
            char symbol = mapa.charAt(i);
            int col = i % width;
            int row = i / width;

            if (symbol == '#') {
                walls.add(new Wall(col, row, '#'));
            }
        }

        return walls;
    }


    private boolean collidesWithWalls(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position) && wall.getSymbol() == '#') {
                return true; // Houve Colisão
            }
        }
        return false; // Sem Colisão
    }

    public void processKey(KeyStroke key) {
        if (key.getKeyType() == KeyType.ArrowUp) moveHero(hero.moveUp());
        if (key.getKeyType() == KeyType.ArrowRight) moveHero(hero.moveRight());
        if (key.getKeyType() == KeyType.ArrowDown) moveHero(hero.moveDown());
        if (key.getKeyType() == KeyType.ArrowLeft) moveHero(hero.moveLeft());

        retrieveCoins();
        verifyMonsterCollisions();
        retrieveKey();
    }

    public void verifyMonsterCollisions() {
        for (Monster monster : monsters)
            if (hero.getPosition().equals(monster.getPosition())) {
                System.out.println("Morreu no nivel " + getLevel() + " com " + getPonto()+" pontos.");
                System.exit(0);

            }
    }
    public void moveMonsters() {
        for (Monster monster : monsters) {
            Position monsterPosition = monster.move();
            if (canHeroMove(monsterPosition)) {
                monster.setPosition(monsterPosition);
            }
        }
    }

    private void retrieveCoins() {
        if (!coins.isEmpty()) {
            for (Coin coin : coins)
                if (hero.getPosition().equals(coin.getPosition())) {
                    coins.remove(coin);
                    pontos = pontos + (getLevel());

                    break;
                }
        } else {
            if (!isKey) {
                this.chaves = createChave();
            }
        }
    }

    private void retrieveKey() {
        for (Chave chave : chaves)
            if (hero.getPosition().equals((chave.getPosition()))) {
                chaves.remove(chave);
                if (chaves.isEmpty()) {
                    System.out.println("Passou de Nível");
                    level++;
                    System.out.println("Agora está no Nível " + getLevel());
                    initializeNextLevel();
                }
                break;
            }
    }

    private void initializeNextLevel() {
        walls = createWalls();
        coins = createCoins();
        monsters = createMonsters();
        isKey = false;
    }

    public void moveHero(Position position) {
        if (canHeroMove(position)) {
            hero.setPosition(position);
        }
    }

    public int getLevel() {
        return level;
    }

    public int getPonto() {
        return pontos;
    }

    private boolean canHeroMove(Position position) {
        if (position.getX() < 0) return false;
        if (position.getX() > width - 1) return false;
        if (position.getY() < 0) return false;
        if (position.getY() > height - 1) return false;

        for (Wall wall : walls) {
            if (wall.getPosition().equals(position) && wall.getSymbol() == '#') {
                return false;
            }
        }
        return true;
    }

    private void drawScore(TextGraphics graphics) {
        String scoreText = "PONTOS: " + getPonto();
        graphics.setForegroundColor(SCORE_COLOR);
        graphics.putString(SCORE_POSITION, scoreText);

        if (getPonto() > highScore) {
            highScore = getPonto();
            }
        }
}




