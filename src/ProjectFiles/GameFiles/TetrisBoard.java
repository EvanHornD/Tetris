package ProjectFiles.GameFiles;

import static ProjectFiles.GameFiles.TetrisPiece.TetrisPieces;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TetrisBoard extends ReferenceFrame{

    TetrisBlock[][] Grid;
    long[] gridRows;
    public TetrisBoard(BufferedImage backGround, double[] coords, double tileWidth, int[] gridDimensions){
        super(backGround, coords, tileWidth);
        this.Grid = new TetrisBlock[gridDimensions[0]][gridDimensions[1]];
        this.gridRows = new long[gridDimensions[0]];
    }

    public TetrisBlock[][] getGrid(){
        return this.Grid;
    }

    public boolean checkCollision(int[][] blockCoordinates, int[] pieceCoordinates){
        for (int i = 0; i < blockCoordinates.length; i++) {
            int [] coordinate = new int[]{blockCoordinates[i][0]+pieceCoordinates[0],blockCoordinates[i][1]+pieceCoordinates[1]};
            if(checkEdgeCollision(coordinate)){
                return true;
            }
            if(checkBlockCollision(coordinate)){
                return true;
            }
        }
        return false;
    }

    private boolean checkBlockCollision(int[] coordinate){
        if(Grid[coordinate[1]][coordinate[0]]==null){
            return false;
        }
        return true;
    }
    
    private boolean checkEdgeCollision(int[] coordinate){
        if(coordinate[0]<0||coordinate[0]>=Grid[0].length){
            return true;
        }
        if(coordinate[1]>=Grid.length){
            return true;
        }
        return false;
    }

    public void addPieceToGrid(TetrisPiece piece){
        int[][] pieceCoords = TetrisPieces[piece.getType()][piece.getRotation()];
        int[] gridCoords = piece.getCoords();
        TetrisBlock[] blocks = piece.getBlocks();
        for (int i = 0; i < blocks.length; i++) {
            Grid[pieceCoords[i][1]+gridCoords[1]][pieceCoords[i][0]+gridCoords[0]] = blocks[i];
            gridRows[pieceCoords[i][1]+gridCoords[1]] += (Math.pow(10, pieceCoords[i][0]+gridCoords[0]));
        }
    }

    public void clearRows(){
        for (int i = 0; i < gridRows.length; i++) {
            if(checkRow(i)){
                clearRow(i);
            }
        }
    }

    public boolean checkRow(int rowIndex){
        if(gridRows[rowIndex]==1111111111){
            return true;
        }
        return false;
    }

    public void clearRow(int rowIndex){
        for (int i = rowIndex-1; i >= 0; i--) {
            moveRowDown(i);
        }
        gridRows[0] = 0;
        Grid[0] = new TetrisBlock[Grid[0].length];
    }

    public void moveRowDown(int row){
        for (int i = 0; i < Grid[row].length; i++) {
            if(Grid[row][i]!=null){
                Grid[row][i].updateGridCoords(new int[]{i,row+1});
            }
        }
        gridRows[row+1]=gridRows[row];
        Grid[row+1]=Grid[row];
    }

    @Override
    public void render(Graphics2D g2d, double scale) {
        super.render(g2d, scale);
        for (int i = 0; i < Grid.length; i++) {
            for (int j = 0; j < Grid[i].length; j++) {
                if (Grid[i][j] != null) {
                    Grid[i][j].render(g2d, scale);
                }
            }
        }
    }
}
