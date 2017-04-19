package Model.Map;


import Global.Config;
import libnoiseforjava.util.ColorCafe;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Terrain {

    private Tile[][] tiles;
    private BufferedImage perlinMap;
    private static Texture texture;

    public Terrain() {

        tiles = new Tile[Config.TILE_COUNT_X][Config.TILE_COUNT_Y];
        perlinMap = loadImage();
        generateTerrain();

    }

    public void generateTerrain() {

        //this is hardcoded for the png, change to generated terrain later

        for (int y = 0; y < Config.TILE_COUNT_Y - 1; y++) {
            for (int x = 0; x < Config.TILE_COUNT_X - 1; x++) { //-1 stops out of bounds exception

                //Setup Tile
                Tile tile = new Tile();

                //Setup Quads
                Quadrant[] quads = new Quadrant[4];

                //create index for quads
                int index = 0;

                for (int dy = 0; dy < 2; dy++) {
                    for (int dx = 0; dx < 2; dx++) {

                        Quadrant quadrant = new Quadrant(x + dx, y + dy, x, y);

                        //Setup Colour
                        int rgb = perlinMap.getRGB(x + dx, y + dy);
                        int red = (rgb >> 16) & 0xFF;
                        int green = (rgb >> 8) & 0xFF;
                        int blue = rgb & 0xFF;

                        quadrant.setColor(new ColorCafe(red, green, blue, 1));
                        quads[index] = quadrant;
                        index++;
                    }
                }

                tile.setQuadrants(quads);
                tiles[x][y] = tile;
            }
        }
    }

    public void loadAssets() {
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/terrain/grass_2_32.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void draw() {

        glEnable(GL_TEXTURE_2D);
        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

        glBegin(GL_QUADS);
        int SIZE = 32;
        int space = 0;

        for (int y = 0; y < Config.TILE_COUNT_Y; y++) {
            for (int x = 0; x < Config.TILE_COUNT_X; x++) {

                //Grab Tile
                Tile tile = tiles[x][y];

                //Apply Texture
                glBindTexture(GL_TEXTURE_2D, tile.getTextureID());

                //Grab Quadrants
                Quadrant[] quadrants = tile.getQuadrants();

                for (int q = 0; q < 4; q++) {

                    Quadrant quadrant = quadrants[q];

                    //Add Color
                    ColorCafe color = quadrant.getColor();
                    glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());

                    //Add Texture
                    glTexCoord2f(quadrant.getS(), quadrant.getT());

                    //Add Vertex Position
                    glVertex2f(quadrant.getX(), quadrant.getY());

                }

                /*
                glColor4f(r, g, b, 1);

                setColor(x, y);

                glTexCoord2f(0, 0);
                glVertex2f((x * SIZE) + space, (y * SIZE) + space); // Upper-left

                setColor(x, y + 1);
                // glColor4f(0f, 0.9f, 0.6f, 1);
                glTexCoord2f(0, 1);
                glVertex2f((x * SIZE) + space, (y * SIZE) + SIZE); // Upper-right
                setColor(x + 1, y + 1);
                //glColor4f(0f, 0.9f, 0.6f, 1);
                glTexCoord2f(1, 1);
                glVertex2f((x * SIZE) + SIZE, (y * SIZE) + SIZE); // Bottom-right
                setColor(x + 1, y);
                //glColor4f(0f, 0.9f, 0.6f, 1);
                glTexCoord2f(1, 0);
                glVertex2f((x * SIZE) + SIZE, (y * SIZE) + space); // Bottom-left
                */

            }
        }

        glEnd();
        glDisable(GL_TEXTURE_2D);

    }

    public void setColor(int x, int y) {

        int rgb = perlinMap.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        float r = (1.0f / 255) * red;
        float g = (1.0f / 255) * green;
        float b = (1.0f / 255) * blue;

        glColor4f(r, g, b, 1);
    }

    public BufferedImage loadImage() {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("res/terrain/terrain_test.png"));
        } catch (IOException e) {
            System.out.println("failed to load asset");
        }

        return img;
    }
}
