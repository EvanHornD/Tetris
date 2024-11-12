package ProjectFiles.Rendering;

import java.awt.image.BufferedImage;

public abstract class Shader {

    Shader(BufferedImage image){
    }

    public abstract BufferedImage applyShaders();
}
