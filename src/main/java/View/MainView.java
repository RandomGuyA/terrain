package View;


import Global.Config;
import Model.Game;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

public class MainView {

    private Game game;
    private float translate_x = 0;
    private float translate_y = 0;
    private float scale = 1.0f;
    private float delta = 12.0f;

    public MainView() {
        setUpDisplay();
        setUpOpenGL();
    }

    private void setUpDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT));
            Display.setTitle(Config.TITLE);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
    }

    private void setUpOpenGL(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
    }

    public void startGame(Game game) {
        this.game = game;
        game.loadAssets();
        gameLoop();
        Display.destroy();
        System.exit(1);
    }

    private void gameLoop() {
        while (!Display.isCloseRequested()) {
            render();
            logic();
            input();
            update();
        }
    }

    private void render(){
        glClear(GL_COLOR_BUFFER_BIT);
        glPushMatrix();
        glTranslatef(translate_x, translate_y, 0);
        glScaled(scale, scale, 1);
        game.draw();
        glPopMatrix();
    }

    private void logic(){

    }

    private void input(){
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            translate_y+=delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            translate_y-=delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            translate_x+=delta;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            translate_x-=delta;
        }
    }

    private void update() {
        Display.update();
        Display.sync(60);
    }
}
