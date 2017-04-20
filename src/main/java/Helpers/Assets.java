package Helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Assets {

    private static String DIR = "res/terrain/";


    public static BufferedImage loadImage(String fileName) {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(DIR + fileName));
        } catch (IOException e) {
            System.out.println("failed to load asset");
        }

        return img;
    }

}
