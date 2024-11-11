package ProjectFiles.GameFiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class StoredPiece extends ReferenceFrame{
    TetrisPiece storedPiece;
    TetrisPiece previousPiece;
    public StoredPiece(BufferedImage backGround, double[] coords, double tileWidth){
        super(backGround, coords, tileWidth);
    }

    public void setStoredPiece(TetrisPiece storedPiece) {
        this.storedPiece = storedPiece;
    }

    public TetrisPiece storePiece(TetrisPiece activePiece){
        if(activePiece==previousPiece){
            return activePiece;
        }
        previousPiece = storedPiece;
        previousPiece.updateReferenceFrame(activePiece.getReferenceFrame());
        previousPiece.setCoords(new int[]{4,0});
        storedPiece = activePiece;
        storedPiece.updateReferenceFrame(this);
        storedPiece.setCoords(new int[]{0,0});
        return previousPiece;
    }

    @Override
    public void render(Graphics2D g2d, double scale) {
        super.render(g2d, scale);
        storedPiece.render(g2d, scale);
    }
}
