package ProjectFiles.GameFiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import ProjectFiles.Rendering.SpriteEntity;

public class TetrisBlock extends SpriteEntity{

    public TetrisBlock(BufferedImage Sprite, ReferenceFrame reference, int[] coords){
        super(Sprite, reference.getTileCoords(coords));
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
    }
}
