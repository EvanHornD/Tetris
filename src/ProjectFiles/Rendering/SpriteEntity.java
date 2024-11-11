package ProjectFiles.Rendering;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class SpriteEntity extends RenderableEntity {

    private BufferedImage sprite;
    private double[] coordinates;

    public SpriteEntity(BufferedImage sprite, double[] coordinates) {
        this.sprite = sprite;
        this.coordinates = coordinates;
    }

    public double[] getCoords() {
        return coordinates;
    }

    public void moveSprite(double x, double y){
        coordinates[0] = x;
        coordinates[1] = y;
    }

    @Override
    public void render(Graphics2D g2d, double scale) {
        g2d.drawImage(sprite,null, (int)(coordinates[0]/scale), (int)(coordinates[1]/scale));
    }
}


