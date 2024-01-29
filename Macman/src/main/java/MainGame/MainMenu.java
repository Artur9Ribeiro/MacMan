package MainGame;


import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class MainMenu {


    public static void main(String[] args) {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            TerminalScreen screen = terminalFactory.createScreen();
            screen.startScreen();

            MultiWindowTextGUI textGUI = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLACK));

            // criar main window
            BasicWindow mainWindow = new BasicWindow("Main Menu");

            // criar um painel para o main menu
            Panel mainMenuPanel = new Panel();
            mainMenuPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

            // adicionar ao menu items
            mainMenuPanel.addComponent(new Button("Jogar", () -> openNewGame()));
            mainMenuPanel.addComponent(new Button("Desistir", () -> closeApplication()));

            mainWindow.setComponent(mainMenuPanel);

            // criar handler para fechar a janela
            textGUI.addWindowAndWait(mainWindow);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeApplication() {

        System.exit(0);
    }

    private static void openNewGame() {

        Application.main(new String[]{});
    }

}






