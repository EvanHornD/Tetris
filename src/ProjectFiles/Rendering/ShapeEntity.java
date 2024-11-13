package ProjectFiles.Rendering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ShapeEntity extends RenderableEntity {
    public Color defaultColor = new Color(32,32,32);
    public Color defaultOutline = new Color(42,42,42);
    public Color highlightedOutLine = new Color(52,52,52);
    private BufferedImage shape;
    private double[] coordinates;
    private int[] dimensions;
    private Color color;
    private Color outLineColor;

    public ShapeEntity(String shape, double[] coordinates, int[] dimensions, Color color) {
        this.shape = createShape(shape, dimensions, color, color);
        this.coordinates = coordinates;
        this.dimensions = dimensions;
        this.color = color;
        this.outLineColor = defaultOutline;
    }

    public BufferedImage createShape(String shape, int[] dimensions, Color color, Color outLineColor){
        BufferedImage newShape = new BufferedImage(dimensions[0], dimensions[1], BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = newShape.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, dimensions[0], dimensions[1]);
        g2d.setColor(outLineColor);
        g2d.drawRect(0, 0, dimensions[0], dimensions[1]);
        return newShape;
    }


    public double[] getCoords() {
        return coordinates;
    }

    public int[] getDimensions() {
        return dimensions;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color newColor) {
        this.color = newColor;
    }

    public void setOutLineColor(Color newColor) {
        this.outLineColor = newColor;
    }

    public void moveShape(double x, double y){
        coordinates[0] += x;
        coordinates[1] += y;
    }

    @Override
    public void render(Graphics2D g2d, double scale) {
        g2d.drawImage(shape,null, (int)(coordinates[0]/scale), (int)(coordinates[1]/scale));
    }
}


