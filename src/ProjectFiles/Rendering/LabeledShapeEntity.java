package ProjectFiles.Rendering;

import java.awt.Graphics2D;

public class LabeledShapeEntity extends RenderableEntity {

    private ShapeEntity shape;
    private TextEntity text;

    public LabeledShapeEntity(ShapeEntity shape, String text,int textSize, String textAlignment){
        this.shape = shape;
        double[] coords = shape.getCoords();
        int[] dimensions = shape.getDimensions();
        this.text = new TextEntity(text,textSize,coords,textAlignment,dimensions);
    }

    public ShapeEntity getShape() {
        return shape;
    }

    public TextEntity getText() {
        return text;
    }

    public void moveEntity(double x, double y){
        text.moveText(x, y);
        shape.moveShape(x, y);
    }

    @Override
    public void render(Graphics2D g2d, double scale){
        shape.render(g2d,scale);
        text.render(g2d,scale);
    }
}
