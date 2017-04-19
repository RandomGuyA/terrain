package Model;

import Model.Map.Terrain;


public class Game {

    private Terrain terrain;


    public Game() {
        terrain = new Terrain();
    }

    public void draw(){
        terrain.draw();
    }

    public void update(){

    }

    public void loadAssets() {
        terrain.loadAssets();
    }
}
