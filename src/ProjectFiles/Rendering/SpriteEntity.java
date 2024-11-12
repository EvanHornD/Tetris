package ProjectFiles.Rendering;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class SpriteEntity extends RenderableEntity {

    private BufferedImage sprite;
    private double[] coordinates;
    private ArrayList<Shader> shaders;
    private boolean hasShaders; 

    public SpriteEntity(BufferedImage sprite, double[] coordinates) {
        this.sprite = sprite;
        this.coordinates = coordinates;
    }

    public void addShader(Shader shader){
        if(!hasShaders){
            shaders = new ArrayList<>();
            hasShaders=true;
        }
    }

    public double[] getCoords() {
        return coordinates;
    }

    public void moveSprite(double x, double y){
        coordinates[0] = x;
        coordinates[1] = y;
    }

    public void applyShaders(){

    }

    @Override
    public void render(Graphics2D g2d, double scale) {
        if(hasShaders){
            applyShaders();
        }
        g2d.drawImage(sprite,null, (int)(coordinates[0]/scale), (int)(coordinates[1]/scale));
    }
}


