package bomberman.entity.boundedbox;

import javafx.geometry.Rectangle2D;

public class RectBoundedBox {

    public int width;
    double x;
    double y;
    int height;
    Rectangle2D boundary;

    public RectBoundedBox(double x, double y, int w, int h) {
        this.x = x;
        this.y = y;
        width = w;
        height = h;
        boundary = new Rectangle2D(x, y, width, height);
    }

    public Rectangle2D getBoundary() {
        return boundary;
    }

    public void setBoundary(Rectangle2D boundaryRect) {
        boundary = boundaryRect;
    }

    public boolean checkCollision(RectBoundedBox b) {
        return b.getBoundary().intersects(getBoundary());
    }

    public void setPosition(double x, double y, double reductionPercent) {
        this.x = x + (int) (width * reductionPercent);
        this.y = y + (int) (height * reductionPercent);
        boundary = new Rectangle2D(this.x, this.y, width, height);
    }

}
