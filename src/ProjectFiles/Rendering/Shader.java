package ProjectFiles.Rendering;

import java.awt.image.BufferedImage;

public abstract class Shader {

    BufferedImage image;
    Shader(BufferedImage image){
        this.image = image;
    }

    public abstract BufferedImage applyShaders();
}
