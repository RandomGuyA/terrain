package Model.Map;


import libnoiseforjava.util.ColorCafe;

public class Quadrant {

    private ColorCafe color;
    private int s, t, x, y;


    public Quadrant(int s, int t, int x, int y) {
        this.s = s;
        this.t = t;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ColorCafe getColor() {
        return color;
    }

    public void setColor(ColorCafe color) {
        this.color = color;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }
}
