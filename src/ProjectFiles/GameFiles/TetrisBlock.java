package ProjectFiles.GameFiles;

import ProjectFiles.Rendering.SpriteEntity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TetrisBlock extends SpriteEntity{

    private ReferenceFrame reference;
    public TetrisBlock(BufferedImage Sprite, ReferenceFrame reference, int[] coords, int type){
        super(Sprite, reference.getTileCoords(coords));
        this.reference = reference;
    }

    public ReferenceFrame getReferenceFrame(){
        return reference;
    }

    public void updateReferenceFrame(ReferenceFrame newFrame){
        reference = newFrame;
    }

    public void updateGridCoords(int[] newGridCoords){
        double[] newCoords = reference.getTileCoords(newGridCoords);
        moveSprite(newCoords[0], newCoords[1]);
    }

    @Override
    public void render(Graphics2D g2d, double scale) {
        super.render(g2d, scale);
    }
}
