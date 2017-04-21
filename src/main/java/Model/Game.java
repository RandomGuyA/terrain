package model;

import model.map.Terrain;


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
}
