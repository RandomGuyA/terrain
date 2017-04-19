package Model.Map;

public class Tile {

    private Quadrant[] quadrants = new Quadrant[4];
    private int textureID;


    public Tile() {

    }

    public Quadrant[] getQuadrants() {
        return quadrants;
    }

    public void setQuadrants(Quadrant[] quadrants) {
        this.quadrants = quadrants;
    }

    public int getTextureID() {
        return textureID;
    }
}
