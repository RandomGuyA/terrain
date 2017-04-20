package Model.Map;

import Global.Config;
import Helpers.Assets;
import Helpers.Colour;
import java.awt.image.BufferedImage;
import static org.lwjgl.opengl.GL11.*;

public class Terrain {

    private Tile[][] tiles;
    private BufferedImage perlinMap, terrainMap;
    private int tileSize = 32;

    public Terrain() {

        tiles = new Tile[Config.TILE_COUNT_X][Config.TILE_COUNT_Y];
        terrainMap = Assets.loadImage("terrain_test.png");
        perlinMap = Assets.loadImage("perlin_test.png");

        //loadAssets();
        generateTerrain();

    }

    public void generateTerrain() {

        System.out.println("Generating Terrain");
        //this is hardcoded for the png, change to generated terrain later

        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) { //-1 stops out of bounds exception

                //Setup Tile
                Tile tile = new Tile(tileSize, tileSize);

                //Add Texture
                tile.setTextureImage(perlinMap.getSubimage(x * 32, y * 32, 32, 32));

                //setup Quadrants
                tile.setQuadrants(calculateQuads(x, y));

                //add tile
                tiles[x][y] = tile;
            }
        }
        System.out.println("Terrain Generation Finished");
    }

    public Quadrant[] calculateQuads(int x, int y) {

        //Setup Quads
        Quadrant[] quads = new Quadrant[4];

        int trueX = (x * tileSize);
        int trueY = (y * tileSize);
        Quadrant quadrant = new Quadrant(0, 0, trueX, trueY);
        quadrant.setColour(new Colour(terrainMap.getRGB(x, y)));
        quads[0] = quadrant;

        trueX = (x * tileSize) + tileSize;
        trueY = (y * tileSize);
        quadrant = new Quadrant(1, 0, trueX, trueY);
        quadrant.setColour(new Colour(terrainMap.getRGB(x + 1, y)));
        quads[1] = quadrant;

        trueX = (x * tileSize) + tileSize;
        trueY = (y * tileSize) + tileSize;
        quadrant = new Quadrant(1, 1, trueX, trueY);
        quadrant.setColour(new Colour(terrainMap.getRGB(x + 1, y + 1)));
        quads[2] = quadrant;

        trueX = (x * tileSize);
        trueY = (y * tileSize) + tileSize;
        quadrant = new Quadrant(0, 1, trueX, trueY);
        quadrant.setColour(new Colour(terrainMap.getRGB(x, y + 1)));
        quads[3] = quadrant;

        return quads;
    }

    public void draw() {

        //Apply Texture
        glEnable(GL_TEXTURE_2D);
        glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {

                //Grab Tile
                Tile tile = tiles[x][y];

                //Call texture before GLBegin
                glBindTexture(GL_TEXTURE_2D, tile.getTextureID());

                glBegin(GL_QUADS);

                //Grab Quadrants
                Quadrant[] quadrants = tile.getQuadrants();

                for (int q = 0; q < 4; q++) {

                    Quadrant quadrant = quadrants[q];

                    //Add Color
                    Colour colour = quadrant.getColour();
                    glColor4f(colour.getRedFloat(), colour.getGreenFloat(), colour.getBlueFloat(), colour.getAlphaFloat());

                    //Add Texture
                    glTexCoord2f(quadrant.getS(), quadrant.getT());

                    //Add Vertex Position
                    glVertex2f(quadrant.getX(), quadrant.getY());
                    // System.out.println("(" + quadrant.getX() + " , " + quadrant.getY() + ")");

                }
                glEnd();
            }
        }
        glDisable(GL_TEXTURE_2D);

    }


}
