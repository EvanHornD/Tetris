package ProjectFiles.GameFiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ProjectFiles.Rendering.SpriteEntity;

public class ReferenceFrame extends SpriteEntity{

    int TileWidth;

    public ReferenceFrame(BufferedImage Sprite, double[] coordinates, int tileWidth){
        super(Sprite, coordinates);
        this.TileWidth = tileWidth;
    }

    public double[] getTileCoords(int[] gridCoords){
        double x = this.getCoords()[0]+TileWidth*gridCoords[0];
        double y = this.getCoords()[1]+TileWidth*gridCoords[1];
        return new double[]{x,y};
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
    }
}