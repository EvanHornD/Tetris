package ProjectFiles.Rendering;

import java.awt.Color;
import java.awt.Graphics2D;

public class ShapeEntity extends RenderableEntity {
    public Color defaultColor = new Color(32,32,32);
    public Color defaultOutline = new Color(42,42,42);
    public Color highlightedOutLine = new Color(52,52,52);
    private String shape;
    private double[] coordinates;
    private int[] dimensions;
    private Color color;
    private Color outLineColor;

    public ShapeEntity(String shape, double[] coordinates, int[] dimensions) {
        this.shape = shape;
        this.coordinates = coordinates;
        this.dimensions = dimensions;
        this.color = defaultColor;
        this.outLineColor = defaultOutline;
    }

    public ShapeEntity(Color color) {
        this.shape = "Rectangle";
        this.coordinates = new double[]{0.,0.};
        this.dimensions = new int[]{20000,20000};
        this.color = color;
        this.outLineColor = color;
    }

    public String getShape() {
        return shape;
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
        g2d.setColor(color);
        switch (shape) {
            case "Rectangle":
                g2d.fillRect((int)coordinates[0], (int)coordinates[1], dimensions[0], dimensions[1]);
                g2d.setColor(outLineColor);
                g2d.drawRect((int)coordinates[0], (int)coordinates[1], dimensions[0], dimensions[1]);
                break;
            default:
                break;
        }
    }
}


