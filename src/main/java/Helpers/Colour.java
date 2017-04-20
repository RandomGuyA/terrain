package Helpers;

import java.awt.*;
import static java.lang.Math.floor;

public class Colour {

    private int redInt, greenInt, blueInt, alphaInt;
    private float redFloat, greenFloat, blueFloat, alphaFloat;
    private int rgba, rgb;

    public Colour(int redInt, int greenInt, int blueInt) {
        this.redInt = redInt;
        this.greenInt = greenInt;
        this.blueInt = blueInt;
        this.alphaInt = 255;
        IntToValue(redInt, greenInt, blueInt, alphaInt);
        IntToFloat(redInt, greenInt, blueInt, alphaInt);
    }

    public Colour(int redInt, int greenInt, int blueInt, int alphaInt) {
        this.redInt = redInt;
        this.greenInt = greenInt;
        this.blueInt = blueInt;
        this.alphaInt = alphaInt;
        IntToValue(redInt, greenInt, blueInt, alphaInt);
        IntToFloat(redInt, greenInt, blueInt, alphaInt);
    }

    public Colour(float redFloat, float greenFloat, float blueFloat) {
        this.redFloat = redFloat;
        this.greenFloat = greenFloat;
        this.blueFloat = blueFloat;
        this.alphaFloat = 1;
        FloatToValue(redFloat, greenFloat, blueFloat, alphaFloat);
        FloatToInt(redFloat, greenFloat, blueFloat, alphaFloat);
    }

    public Colour(float redFloat, float greenFloat, float blueFloat, float alphaFloat) {
        this.redFloat = redFloat;
        this.greenFloat = greenFloat;
        this.blueFloat = blueFloat;
        this.alphaFloat = alphaFloat;
        FloatToValue(redFloat, greenFloat, blueFloat, alphaFloat);
        FloatToInt(redFloat, greenFloat, blueFloat, alphaFloat);
    }

    public Colour(int rgb) {

        redInt = (rgb >> 16) & 0xFF;
        greenInt = (rgb >> 8) & 0xFF;
        blueInt = rgb & 0xFF;
        alphaInt = 255;
        IntToFloat(redInt, greenInt, blueInt, alphaInt);

    }

    public String toString(){

        String rgbString = "\nInt RGBA( "+redInt+" , "+greenInt+" , "+blueInt+" , "+alphaInt+" )";
        String floatstring = "\nfloat RGBA( "+redFloat+" , "+greenFloat+" , "+blueFloat+" , "+alphaFloat+" )";
        String rgbaValue = "\nRGBA Value: "+rgba;
        String rgbValue = "\nRGB Value: "+rgb;

        return rgbString + floatstring + rgbaValue + rgbValue;
    }

    private void IntToValue(int redInt, int greenInt, int blueInt, int alphaInt) {
        rgba = new Color(redInt, greenInt, blueInt, alphaInt).getRGB();
        rgb = new Color(redInt, greenInt, blueInt).getRGB();
    }

    private void IntToFloat(int redInt, int greenInt, int blueInt, int alphaInt) {
        redFloat = (1.0f / 255) * redInt;
        greenFloat = (1.0f / 255) * greenInt;
        redFloat = (1.0f / 255) * blueInt;
        alphaFloat = (1.0f / 255) * alphaInt;
    }

    private void FloatToValue(float redFloat, float greenFloat, float blueFloat, float alphaFloat) {
        redInt = (int) floor(redFloat * 255.0f);
        greenInt = (int) floor(greenFloat * 255.0f);
        blueInt = (int) floor(blueFloat * 255.0f);
        alphaInt = (int) floor(alphaFloat * 255.0f);
        IntToValue(redInt, greenInt, blueInt, alphaInt);
    }

    private void FloatToInt(float redFloat, float greenFloat, float blueFloat, float alphaFloat) {

        redInt = (int) floor(redFloat * 255.0f);
        greenInt = (int) floor(greenFloat * 255.0f);
        blueInt = (int) floor(blueFloat * 255.0f);
        alphaInt = (int) floor(alphaFloat * 255.0f);
    }

    public int getRedInt() {
        return redInt;
    }

    public void setRedInt(int redInt) {
        this.redInt = redInt;
    }

    public int getGreenInt() {
        return greenInt;
    }

    public void setGreenInt(int greenInt) {
        this.greenInt = greenInt;
    }

    public int getBlueInt() {
        return blueInt;
    }

    public void setBlueInt(int blueInt) {
        this.blueInt = blueInt;
    }

    public int getAlphaInt() {
        return alphaInt;
    }

    public void setAlphaInt(int alphaInt) {
        this.alphaInt = alphaInt;
    }

    public float getRedFloat() {
        return redFloat;
    }

    public void setRedFloat(float redFloat) {
        this.redFloat = redFloat;
    }

    public float getGreenFloat() {
        return greenFloat;
    }

    public void setGreenFloat(float greenFloat) {
        this.greenFloat = greenFloat;
    }

    public float getBlueFloat() {
        return blueFloat;
    }

    public void setBlueFloat(float blueFloat) {
        this.blueFloat = blueFloat;
    }

    public float getAlphaFloat() {
        return alphaFloat;
    }

    public void setAlphaFloat(float alphaFloat) {
        this.alphaFloat = alphaFloat;
    }

    public int getRgba() {
        return rgba;
    }

    public void setRgba(int rgba) {
        this.rgba = rgba;
    }

    public int getRgb() {
        return rgb;
    }

    public void setRgb(int rgb) {
        this.rgb = rgb;
    }
}
