package ProjectFiles.Rendering;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
public class CRTShader extends Shader {

    private float gammaInput = 2.0f;
    private float gammaOutput = 1.8f;
    private float brightnessBoost = 1.2f;
    private float scanlineStrength = 0.7f;
    private float maskStrength = 0.3f;

    public CRTShader(BufferedImage image){
        super(image);
    }

    public CRTShader(){
        super(null);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public BufferedImage applyShaders(){
        BufferedImage updatedImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_3BYTE_BGR); 
        byte[] oldPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        byte[] newPixels = ((DataBufferByte) updatedImage.getRaster().getDataBuffer()).getData();

        for (int i = 0; i < oldPixels.length; i+=3) {
            int x = (i / 3) % image.getWidth();
            int y = (i / 3) / image.getWidth();
        
            // Interpret the byte values as unsigned (0–255 range)
            float red = (oldPixels[i + 2] & 0xFF);
            float green = (oldPixels[i + 1] & 0xFF);
            float blue = (oldPixels[i] & 0xFF);
        
            // Apply scanline effect - darken every other row slightly
            float scanlineFactor = (y % 4 > 2) ? scanlineStrength : 2-scanlineStrength;
            red *= scanlineFactor;
            green *= scanlineFactor;
            blue *= scanlineFactor;
        
            // Basic color mask effect to simulate CRT RGB sub-pixels
            if (x % 3 == 0) red *= maskStrength;
            else if (x % 3 == 1) green *= maskStrength;
            else blue *= maskStrength;
        
            // Apply brightness boost (but now without gamma correction)
            red = Math.min(255, red * brightnessBoost);
            green = Math.min(255, green * brightnessBoost);
            blue = Math.min(255, blue * brightnessBoost);
        
            // Convert back to byte for the new pixel array
            newPixels[i] = (byte) blue;
            newPixels[i + 1] = (byte) green;
            newPixels[i + 2] = (byte) red;
        }
        return updatedImage;
    }
}
