package ProjectFiles.Rendering;

import java.awt.image.BufferedImage;

public class ColorizerShader extends Shader {

    BufferedImage image;
    int color;
    ColorizerShader(BufferedImage image, int color){
        super(image);
        this.image = image;
        this.color = color;
    }

    ColorizerShader(BufferedImage image){
        super(image);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public BufferedImage applyShaders(){
        BufferedImage updatedImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        
        return this.image;
    }

}
