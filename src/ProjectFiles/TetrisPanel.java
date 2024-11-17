package ProjectFiles;

import ProjectFiles.GameFiles.*;
import static ProjectFiles.GameFiles.TetrisPiece.*;
import ProjectFiles.Input.KeyBindsManager;
import ProjectFiles.Input.gameTimer;
import ProjectFiles.Rendering.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    static int blockSize = 32;
    static int frameNumber = 0;
    ShapeEntity backGround;
    BufferedImage[] images = new BufferedImage[3]; 
    static TetrisBoard board;
    static TetrisPiece activePiece;
    static CRTShader crtShader = new CRTShader();
    static int[] randomBag = TetrisBag;
    static boolean state = true;
    static int pieceMoveTimer = 20;
    static ReferenceFrame[] nextPiecesGrid;
    static StoredPiece storedPieceGrid;
    static TetrisPiece[] nextPieces;
    static int nextPiecesLength = 3;
    static boolean changeMade = true;
    //#endregion
    
    @SuppressWarnings("CallToPrintStackTrace")
    TetrisPanel(Dimension dimensions){
        this.setPreferredSize(dimensions);
        this.setFocusable(true);
        this.requestFocus();
        keyBinds = new KeyBindsManager(this);
        renderer = new GraphicsRenderer(this);
        gameTimer = new gameTimer();
        panelWidth = (int)dimensions.getWidth();
        panelHeight = (int)dimensions.getHeight();
        screenRatio = panelHeight/(1080.);
        clampScreenRatio();
        blockSize *= screenRatio;
        this.backGround = new ShapeEntity("Rectangle",new double[]{0,0}, new int[]{panelWidth,panelHeight},new Color(24,24,24));
        getImages();
        TetrisPiece.TetrisBlocks = colorizeTetrisBlocks();
        board = new TetrisBoard(images[0], new double[]{panelWidth/2-(6*blockSize),panelHeight/2-(12*blockSize)}, blockSize, new int[]{22,10});
        storedPieceGrid = new StoredPiece(images[2], new double[]{panelWidth/2-(11*blockSize),panelHeight/2-(10*blockSize)}, blockSize);
        storedPieceGrid.setStoredPiece(new TetrisPiece(-1, 0, storedPieceGrid, new int[]{0,0}));
        generateNextPieceGrid();
        generateNextPieces();
        activePiece.movePiece(new int[]{4,0});
        setReferenceFrames();

        startGameTimer();
    }
    
    public void getImages(){
        try {
            images[0] = ImageIO.read(new File("src\\ProjectFiles\\Images\\tetrisGrid.png"));
            images[1] = ImageIO.read(new File("src\\ProjectFiles\\Images\\tetrisGreyScale.png"));
            images[2] = ImageIO.read(new File("src\\ProjectFiles\\Images\\tetrisNextPieceGrid.png"));
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public BufferedImage[] colorizeTetrisBlocks(){
        BufferedImage[] TetrisBlocks = new BufferedImage[7];
        ColorizerShader colorizer = new ColorizerShader(images[1]);
        for (int i = 0; i < TetrisColors.length; i++) {
            colorizer.setColor(TetrisColors[i]);
            TetrisBlocks[i] = colorizer.applyShaders();
        }
        return TetrisBlocks;
    }

    public void clampScreenRatio(){
        screenRatio = Math.round(screenRatio*4)/4.;
    }

    public void generateNextPieceGrid(){
        nextPiecesGrid = new ReferenceFrame[nextPiecesLength];
        for (int i = 0; i < nextPiecesLength; i++) {
            nextPiecesGrid[i] = new ReferenceFrame(images[2], new double[]{panelWidth/2+(6*blockSize),panelHeight/2-(10*blockSize)+i*(5*blockSize)}, blockSize);
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
        runUserInput();
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

    public static void runUserInput(){
        Map<String, Integer> keyFrames = keyBinds.getKeyFrames();

        if(keyFrames.get("Down")==1||keyFrames.get("Down")%2==1&&keyFrames.get("Down")>11){
            movePiece(activePiece.getCoords(), 0, 1);
        }

        if(keyFrames.get("Left")==1||keyFrames.get("Left")%2==1&&keyFrames.get("Left")>11){
            movePiece(activePiece.getCoords(), -1, 0);
        }

        if(keyFrames.get("Right")==1||keyFrames.get("Right")%2==1&&keyFrames.get("Right")>11){
            movePiece(activePiece.getCoords(), 1, 0);
        }

        if(keyFrames.get("Rotate")==1){
            rotatePiece(activePiece.getCoords());
            changeMade = true;
        }

        if(keyFrames.get("Store")==1){
            storePiece();
            changeMade = true;
        }

        if(keyFrames.get("QuickDrop")==1){
            int[] newCoordinates = new int[]{activePiece.getCoords()[0],activePiece.getCoords()[1]+1};
            // check if there was a collision
            while(!board.checkCollision(TetrisPieces[activePiece.getType()][activePiece.getRotation()], newCoordinates)){
                activePiece.movePiece(new int[]{0,1});
                newCoordinates = new int[]{activePiece.getCoords()[0],activePiece.getCoords()[1]+1};
            }
            addPieceToGrid();
            changeMade = true;
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
        changeMade = true;

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

    public static void storePiece(){
        activePiece = storedPieceGrid.storePiece(activePiece);
        if (activePiece.getType()==-1) {
            generateNextPiece();
            activePiece.movePiece(new int[]{4,0});
            setReferenceFrames();
        }
    }

    public static void setReferenceFrames(){
        activePiece.updateReferenceFrame(board);
        for (int i = 0; i < nextPiecesLength; i++) {
            nextPieces[i].updateReferenceFrame(nextPiecesGrid[i]);
        }
    }

    static BufferedImage finalImage;
    static boolean CRTShaderOn = true;
    public void render(Graphics2D g2d) {
        if(changeMade){
            BufferedImage preShaders = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D g2DBuffer = preShaders.createGraphics();
            g2DBuffer.scale(screenRatio, screenRatio);
            backGround.render(g2DBuffer, screenRatio);
            board.render(g2DBuffer, screenRatio);
            for (int i = 0; i < nextPiecesGrid.length; i++) {
                nextPiecesGrid[i].render(g2DBuffer, screenRatio);
                nextPieces[i].render(g2DBuffer, screenRatio);
            }
            storedPieceGrid.render(g2DBuffer, screenRatio);
            activePiece.render(g2DBuffer, screenRatio);
            if(CRTShaderOn){
                crtShader.setImage(preShaders);
                finalImage = crtShader.applyShaders();
            } else {
                finalImage = preShaders;
            }
            changeMade = false;
        }
        g2d.drawImage(finalImage, null, 0, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        renderer.draw(g2d);
    }
}

