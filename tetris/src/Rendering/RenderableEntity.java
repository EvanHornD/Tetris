package Rendering;

import java.awt.Graphics2D;

public abstract class RenderableEntity implements Renderable {
    public RenderableEntity() {
    }

    @Override
    public abstract void render(Graphics2D g2d);
}

