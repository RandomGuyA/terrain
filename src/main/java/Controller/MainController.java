package Controller;


import Model.Game;
import View.MainView;

public class MainController {

    private Game game;
    private MainView view;

    public MainController(Game game, MainView view) {
        this.game = game;
        this.view = view;
        view.startGame(game);
    }
}
