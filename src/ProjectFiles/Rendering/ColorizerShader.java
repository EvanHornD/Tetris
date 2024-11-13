package ProjectFiles.Rendering;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ColorizerShader extends Shader {

    int color;
    public ColorizerShader(BufferedImage image, int color){
        super(image);
        this.color = color;
    }

    public ColorizerShader(BufferedImage image){
        super(image);
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public BufferedImage applyShaders(){
        BufferedImage updatedImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_4BYTE_ABGR); 
        byte[] oldPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        byte[] newPixels = ((DataBufferByte) updatedImage.getRaster().getDataBuffer()).getData();
        int blue = (color&0xff);
        int green = ((color>>8)&(0xff));
        int red = ((color>>16)&(0xff));
        byte alpha = (byte)((color>>24)&(0xff));
        for (int i = 0; i < oldPixels.length; i+=4) {
            int brightness = oldPixels[i+1] & 0xff;
            byte newBlue = (byte)((blue*brightness)/255);
            byte newGreen = (byte)((green*brightness)/255);
            byte newRed = (byte)((red*brightness)/255);
            newPixels[i]=alpha;
            newPixels[i+1]=newBlue;
            newPixels[i+2]=newGreen;
            newPixels[i+3]=newRed;
        }
        return updatedImage;
    }

}
