package Model.Map;

import Global.Config;
import Helpers.Assets;
import Helpers.Colour;
import Helpers.Util;
import NoiseModules.Radial;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.Perlin;
import libnoiseforjava.util.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Terrain {

    private Tile[][] tiles;
    private BufferedImage perlinMap, terrainMap;
    private int tileSize = 32;
    private int TILE_COUNT_X = Config.TILE_COUNT_X;
    private int TILE_COUNT_Y = Config.TILE_COUNT_Y;
    private int WIDTH = tileSize * TILE_COUNT_X;
    private int HEIGHT = tileSize * TILE_COUNT_Y;
    private double NOISE_FREQUENCY = 0.05;


    public Terrain() {

        tiles = new Tile[TILE_COUNT_X][TILE_COUNT_Y];
        terrainMap = Assets.loadImage("terrain_test.png");
        perlinMap = Assets.loadImage("perlin_large.png");
        //perlinMap = generateTextureMap();
        make_map();
        generateTerrain();

    }

    public void make_map() {

        System.out.println("Generate Map");

        try {

            Perlin noiseModule = new Perlin();
            noiseModule.setFrequency(0.09);
            noiseModule.setSeed(Util.randInt(0, 10000));
            //noiseModule.setSeed(200);

            Radial radial = new Radial();
            radial.setMax_width(2.0);

            /*Add add = new Add(radial, noiseModule);
            ScaleBias scaleBias = new ScaleBias(add);
            scaleBias.setBias(-0.55);*/

            // create Noisemap object
            NoiseMap heightMap = new NoiseMap(TILE_COUNT_X + 1, TILE_COUNT_Y + 1);//extra tile for reference

            // create Builder object
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(noiseModule);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(TILE_COUNT_X + 1, TILE_COUNT_Y + 1);

            heightMapBuilder.setBounds(-16.0, 16.0, -16.0, 16.0);
            //heightMapBuilder.setBounds (-1.0, 1.0, -1.0, 1.0);

            heightMapBuilder.build();

            // create renderer object
            RendererImage renderer = new RendererImage();

            // terrain gradient
            renderer.clearGradient();

            renderer.addGradientPoint(-0.85, new ColorCafe(7, 52, 127, 255));
            renderer.addGradientPoint(-0.60, new ColorCafe(12, 86, 212, 255));
            renderer.addGradientPoint(-0.50, new ColorCafe(14, 158, 255, 255));
            renderer.addGradientPoint(-0.4625, new ColorCafe(229, 228, 124, 255));
            renderer.addGradientPoint(-0.30, new ColorCafe(0, 90, 10, 255));
            renderer.addGradientPoint(0.20, new ColorCafe(0, 133, 16, 255));
            renderer.addGradientPoint(0.50, new ColorCafe(127, 102, 50, 255));
            renderer.addGradientPoint(0.65, new ColorCafe(128, 128, 128, 255));
            renderer.addGradientPoint(1.00, new ColorCafe(255, 255, 255, 255));


            // Set up the texture renderer and pass the noise map to it.
            ImageCafe destTexture = new ImageCafe(heightMap.getWidth(), heightMap.getHeight());
            renderer.setSourceNoiseMap(heightMap);
            renderer.setDestImage(destTexture);
            //renderer.enableLight(true);
            //renderer.setLightContrast(2.0); // Triple the contrast
            //renderer.setLightBrightness(2.0); // Double the brightness

            // Render the texture.
            renderer.render();

            //create black and white analogue
            /*renderer.clearGradient();
            ImageCafe grayscaleVersion = new ImageCafe(heightMap.getWidth(), heightMap.getHeight());
            renderer.setDestImage(grayscaleVersion);
            renderer.addGradientPoint(-1.0, new ColorCafe(0, 0, 0, 255)); //Black
            renderer.addGradientPoint(1.0, new ColorCafe(255, 255, 255, 255)); //White
            renderer.render();
            */

            terrainMap = buffBuilder(destTexture.getHeight(), destTexture.getWidth(), destTexture);
            //gray_image = buffBuilder(destTexture.getHeight(), destTexture.getWidth(), grayscaleVersion);
            //tile_map = textureMapBuilder(destTexture.getHeight(), destTexture.getWidth(), destTexture, grayscaleVersion);


        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }

        try {
            ImageIO.write(terrainMap, "png", new File("terrain_test.png"));
        } catch (IOException e1) {
            System.out.println("Could not write the image file.");
        }
        System.out.println("Map finished");
    }

    private BufferedImage generateTextureMap() {

        System.out.println("Generate Texture Map");

        BufferedImage image = null;
        Perlin noiseModule = new Perlin();
        noiseModule.setFrequency(NOISE_FREQUENCY);
        //noiseModule.setSeed(Util.randInt(0, 10000));
        noiseModule.setSeed(20152);
        NoiseMap heightMap = null;

        try {
            heightMap = new NoiseMap(WIDTH, HEIGHT);

            // create Builder object
            NoiseMapBuilderPlane heightMapBuilder = new NoiseMapBuilderPlane();
            heightMapBuilder.setSourceModule(noiseModule);
            heightMapBuilder.setDestNoiseMap(heightMap);
            heightMapBuilder.setDestSize(WIDTH, HEIGHT);

            heightMapBuilder.setBounds(0, WIDTH, 0, HEIGHT);

            heightMapBuilder.build();

            // create renderer object
            RendererImage renderer = new RendererImage();

            // terrain gradient
            renderer.clearGradient();
            renderer.addGradientPoint(-1.0, new ColorCafe(0, 0, 0, 255)); //Black
            renderer.addGradientPoint(1.0, new ColorCafe(255, 255, 255, 255)); //White

            ImageCafe destTexture = new ImageCafe(heightMap.getWidth(), heightMap.getHeight());
            renderer.setSourceNoiseMap(heightMap);
            renderer.setDestImage(destTexture);
            renderer.enableLight(true);
            //renderer.setLightContrast(2.0); // Triple the contrast
            //renderer.setLightBrightness(2.0); // Double the brightness
            // Render the texture.
            renderer.render();

            image = buffBuilder(destTexture.getHeight(), destTexture.getWidth(), destTexture);

        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }

        try {
            ImageIO.write(image, "png", new File("perlin_texture.png"));
        } catch (IOException e1) {
            System.out.println("Could not write the image file.");
        }

        System.out.println("Texture Map Finished");

        return image;
    }

    public BufferedImage buffBuilder(int height, int width, ImageCafe imageCafe) {

        BufferedImage im = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int c = getRGBA(imageCafe.getValue(i, j));
                im.setRGB(i, j, c);
            }
        }
        return im;
    }

    public int getRGBA(ColorCafe colorCafe) {
        int red, blue, green, alpha;
        red = colorCafe.getRed();
        blue = colorCafe.getBlue();
        green = colorCafe.getGreen();
        alpha = colorCafe.getAlpha();
        Color color = new Color(red, green, blue, alpha);
        int rgbnumber = color.getRGB();
        return rgbnumber;
    }

    public void generateTerrain() {

        System.out.println("Generating Terrain");

        for (int y = 0; y < TILE_COUNT_X; y++) {
            for (int x = 0; x < TILE_COUNT_Y; x++) { //-1 stops out of bounds exception

                //Setup Tile
                Tile tile = new Tile(tileSize, tileSize);

                //Add Texture
                tile.setTextureImage(perlinMap.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize));

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


        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {

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
