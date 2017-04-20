import Helpers.Assets;
import Helpers.Colour;

import org.junit.Test;

import java.awt.image.BufferedImage;

public class ColourTest {

    @Test
    public void colourTest (){

        BufferedImage terrainMap = Assets.loadImage("terrain_test.png");
        int rgb = terrainMap.getRGB(80,25);
        System.out.println(rgb);
        Colour colour = new Colour(rgb);

        System.out.println(colour.toString());

    }

}
