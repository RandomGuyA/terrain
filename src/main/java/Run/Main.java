package Run;

import Controller.MainController;
import Model.Game;
import View.MainView;

import javax.swing.*;

public class Main {

    public static void createAndShowGUI(){

        Game game = new Game();
        MainView view = new MainView();
        new MainController(game, view);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
