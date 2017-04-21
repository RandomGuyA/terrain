package run;

import controller.MainController;
import model.Game;
import view.MainView;

import javax.swing.*;

public class Main {

    public static void createAndShowGUI(){

        MainView view = new MainView();
        Game game = new Game();
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
