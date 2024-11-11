package ProjectFiles;

import ProjectFiles.GameFiles.*;
import static ProjectFiles.GameFiles.TetrisPiece.*;
import ProjectFiles.Input.KeyBindsManager;
import ProjectFiles.Rendering.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import javax.swing.*;

public final class TetrisPanel extends JPanel {

    //#region   Class attributes
    gameTimer gameTimer;
    static KeyBindsManager keyBinds;
    static GraphicsRenderer renderer;
    static int panelWidth;
    static int panelHeight;
    static double screenRatio;
    static int frameNumber = 0;
    ShapeEntity backGround = new ShapeEntity(new Color(24,24,24));
    BufferedImage[] images = new BufferedImage[3]; 
    static TetrisBoard board;
    static TetrisPiece activePiece;
    static int[] randomBag = TetrisBag;
    static boolean state = true;
    static int pieceMoveTimer = 20;
    static ReferenceFrame[] nextPiecesGrid;
    static TetrisPiece[] nextPieces;
    static final int nextPiecesLength = 3;
    //#endregion
    
    TetrisPanel(Dimension dimensions){
        this.setPreferredSize(dimensions);
        this.setFocusable(true);
        this.requestFocus();
        keyBinds = new KeyBindsManager(this);
        renderer = new GraphicsRenderer(this);
        gameTimer = new gameTimer();
        panelWidth = (int)dimensions.getWidth();
        panelHeight = (int)dimensions.getHeight();
        screenRatio = panelWidth/(1920.);
        try {
            images[0] = ImageIO.read(new File("src\\ProjectFiles\\Images\\tetrisGrid.png"));
            images[1] = ImageIO.read(new File("src\\ProjectFiles\\Images\\tetrisGreyScale.png"));
            images[2] = ImageIO.read(new File("src\\ProjectFiles\\Images\\tetrisNextPieceGrid.png"));
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
        TetrisPiece.sprite = images[1];
        board = new TetrisBoard(images[0], new double[]{panelWidth/2-160,panelHeight/2-320}, 32, new int[]{22,10});
        generateNextPieceGrid();
        generateNextPieces();
        activePiece.movePiece(new int[]{4,0});
        setReferenceFrames();
        startGameTimer();
    }

    public void generateNextPieceGrid(){
        nextPiecesGrid = new ReferenceFrame[nextPiecesLength];
        for (int i = 0; i < nextPiecesLength; i++) {
            nextPiecesGrid[i] = new ReferenceFrame(images[2], new double[]{panelWidth/2+192,panelHeight/2-320+i*(160)}, 32);
        }
    }

    public void generateNextPieces(){
        nextPieces = new TetrisPiece[nextPiecesLength];
        for (int i = 0; i < nextPiecesLength+1; i++) {
            generateNextPiece();
        }
    }

    // -------------
    // Start Game
    // -------------

    public void startGameTimer() {
        gameTimer.start(deltaTime -> {
            // Update game state
            runGameLoop(deltaTime);
            keyBinds.updateFrameInformation();

            // Trigger the repaint
            renderer.triggerRepaint();
        });
    }

    // -------------
    // Run Game Logic
    // -------------
    public static void runGameLoop(double dt){
        if(state == false){
            return;
        }
        runUserInput(activePiece.getCoords());
        // check if the move timer is done
        if (frameNumber%pieceMoveTimer!=0) {
            frameNumber++;
            return;
        }
        // automatically move the piece down 
        if(movePiece(activePiece.getCoords(), 0, 1)){
            frameNumber++;
            return;
        }
        // if the piece wasnt moved then check if it is inside another piece
        if(!movePiece(activePiece.getCoords(), 0, 0)){
            state = false;
            return;
        }
        frameNumber++;
    }

    public static void runUserInput(int[] currentCoordinates){
        Map<String, Integer> keyActions = keyBinds.getKeyActions();
        Map<String, Integer> keyFrames = keyBinds.getKeyFrames();

        if(keyFrames.get("Down")%5==1){
            movePiece(activePiece.getCoords(), 0, 1);
        }

        if(keyFrames.get("Left")%5==1){
            movePiece(activePiece.getCoords(), -1, 0);
        }

        if(keyFrames.get("Right")%5==1){
            movePiece(activePiece.getCoords(), 1, 0);
        }

        if(keyActions.get("Rotate")==1&&keyFrames.get("Rotate")==1){
            rotatePiece(activePiece.getCoords());
        }
    }

    public static boolean rotatePiece(int[] currentCoordinates){
        int rotation = activePiece.getRotation();
        if(rotation>=3){
            rotation=0;
        } else {
            rotation++;
        }
        if(!board.checkCollision(TetrisPieces[activePiece.getType()][rotation], currentCoordinates)){
            activePiece.rotatePiece();
            return true;
        }
        return false;
    }

    public static boolean movePiece(int[] currentCoordinates,int x, int y){
        // get coordinates of the moved piece
        int[] newCoordinates = new int[]{currentCoordinates[0]+x,currentCoordinates[1]+y};

        // check if there was a collision
        if(!board.checkCollision(TetrisPieces[activePiece.getType()][activePiece.getRotation()], newCoordinates)){
            activePiece.movePiece(new int[]{x,y});
            return true;
        }
        if(x!=0){
            return false;
        }
        addPieceToGrid();
        return false;
    }

    public static void addPieceToGrid(){
        frameNumber = 1;
        board.addPieceToGrid(activePiece);
        board.clearRows();
        generateNextPiece();
        activePiece.movePiece(new int[]{4,0});
        setReferenceFrames();
    }

    public static void generateNextPiece(){
        Random random = new Random();
        if(randomBag.length==0){
            randomBag = TetrisBag;
        }
        int nextPiece = random.nextInt(randomBag.length);
        activePiece = nextPieces[0];
        for (int i = 0; i < nextPiecesLength-1; i++) {
            nextPieces[i] = nextPieces[i+1];
        }
        nextPieces[nextPiecesLength-1] = new TetrisPiece(randomBag[nextPiece], 0, nextPiecesGrid[nextPiecesLength-1], new int[]{0,0});

        int[] newBag = new int[randomBag.length-1];
        int j = 0;
        for (int i = 0; i < randomBag.length; i++) {
            if(i!=nextPiece){
                newBag[j] = randomBag[i];
                j++;
            }
        }
        randomBag = newBag;
    }

    public static void setReferenceFrames(){
        activePiece.updateReferenceFrame(board);
        for (int i = 0; i < nextPiecesLength; i++) {
            nextPieces[i].updateReferenceFrame(nextPiecesGrid[i]);
        }
    }

    public void render(Graphics2D g2d) {
        backGround.render(g2d);
        board.render(g2d);
        for (int i = 0; i < nextPiecesGrid.length; i++) {
            nextPiecesGrid[i].render(g2d);
            nextPieces[i].render(g2d);
        }
        activePiece.render(g2d);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        renderer.draw(g2d);
    }
}

