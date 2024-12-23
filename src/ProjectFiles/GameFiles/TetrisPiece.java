package ProjectFiles.GameFiles;

import ProjectFiles.Rendering.RenderableEntity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TetrisPiece extends RenderableEntity{
    public static final int[][][][] TetrisPieces = new int[][][][]{
        // O Piece
        {{{0,0},{0,1},{1,0},{1,1}},
        {{0,0},{0,1},{1,0},{1,1}},
        {{0,0},{0,1},{1,0},{1,1}},
        {{0,0},{0,1},{1,0},{1,1}}},
        // I Piece
        {{{0,1},{1,1},{2,1},{3,1}},
        {{1,0},{1,1},{1,2},{1,3}},
        {{0,2},{1,2},{2,2},{3,2}},
        {{2,0},{2,1},{2,2},{2,3}}},
        // T Piece
        {{{1,0},{0,1},{1,1},{2,1}},
        {{1,0},{1,1},{2,1},{1,2}},
        {{0,1},{1,1},{2,1},{1,2}},
        {{1,0},{0,1},{1,1},{1,2}}},
        // L Piece
        {{{2,0},{0,1},{1,1},{2,1}},
        {{1,0},{1,1},{1,2},{2,2}},
        {{0,1},{1,1},{2,1},{0,2}},
        {{0,0},{1,0},{1,1},{1,2}}},
        // J Piece
        {{{0,0},{0,1},{1,1},{2,1}},
        {{1,0},{2,0},{1,1},{1,2}},
        {{0,1},{1,1},{2,1},{2,2}},
        {{1,0},{1,1},{1,2},{0,2}}},
        // S Piece
        {{{1,0},{2,0},{0,1},{1,1}},
        {{1,0},{1,1},{2,1},{2,2}},
        {{1,1},{2,1},{0,2},{1,2}},
        {{0,0},{0,1},{1,1},{1,2}}},
        // Z Piece 
        {{{0,0},{1,0},{1,1},{2,1}},
        {{2,0},{1,1},{2,1},{1,2}},
        {{0,1},{1,1},{1,2},{2,2}},
        {{1,0},{0,1},{1,1},{0,2}}}
    };

    public static final int[] TetrisColors = {
        0xffff8000, // O Piece
        0xff00ffff, // I Piece
        0xff800080, // T Piece
        0xffffff00, // L Piece
        0xff0000ff, // J Piece
        0xff00ff00, // S Piece 
        0xffff0000  // Z Piece
    };

    public static final int OPiece = 0, IPiece = 1, TPiece = 2, LPiece = 3, JPiece = 4 , SPiece = 5, ZPiece = 6;

    public static final int[] TetrisBag = {
        OPiece,
        IPiece,
        TPiece,
        LPiece,
        JPiece,
        SPiece,
        ZPiece
    };

    public static BufferedImage[] TetrisBlocks;

    private int[] coords;
    private TetrisBlock[] blocks;
    private int rotation;
    private int type;
    

    public TetrisPiece(int type, int rotation, ReferenceFrame frame, int[] coords){
        this.blocks = new TetrisBlock[4];
        this.coords = coords;
        this.type = type;
        this.rotation = rotation;
        if(type<0){
            blocks = new TetrisBlock[0];
            return;
        }
        generatePiece(TetrisPieces[type][rotation], frame, type);
    }

    public void generatePiece(int[][] blockCoords, ReferenceFrame frame, int type){
        for (int i = 0; i < blockCoords.length; i++) {
            int[] gridCoords = new int[]{blockCoords[i][0]+coords[0],blockCoords[i][1]+coords[1]};
            blocks[i] = new TetrisBlock(TetrisBlocks[type], frame, gridCoords, type);
        }
    }

    public int getType(){
        return this.type;
    }

    public int getRotation(){
        return this.rotation;
    }

    public int[] getCoords(){
        return this.coords;
    }

    public TetrisBlock[] getBlocks(){
        return this.blocks;
    }

    public ReferenceFrame getReferenceFrame(){
        return blocks[0].getReferenceFrame();
    }

    public void updateReferenceFrame(ReferenceFrame newFrame){
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].updateReferenceFrame(newFrame);
        }
        updatepiece(TetrisPieces[type][rotation]);
    }

    private void updatepiece(int[][] blockCoords){
        for (int i = 0; i < blockCoords.length; i++) {
            int[] gridCoords = new int[]{blockCoords[i][0]+coords[0],blockCoords[i][1]+coords[1]};
            blocks[i].updateGridCoords(gridCoords);
        }
    }

    public void setCoords(int[] newCoords){
        this.coords = new int[]{newCoords[0],newCoords[1]};
        int[][] blockCoords = TetrisPieces[type][rotation];
        updatepiece(blockCoords);
    }

    public void movePiece(int[] newCoords){
        this.coords = new int[]{coords[0]+newCoords[0],coords[1]+newCoords[1]};
        int[][] blockCoords = TetrisPieces[type][rotation];
        updatepiece(blockCoords);
    }

    public void rotatePiece(){
        if(rotation==3){
            rotation=0;
        } else {
            rotation++;
        }
        int[][] blockCoords = TetrisPieces[type][rotation];
        updatepiece(blockCoords);
    }

    @Override
    public void render(Graphics2D g2d, double scale) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].render(g2d, scale);
        }
    }
}
