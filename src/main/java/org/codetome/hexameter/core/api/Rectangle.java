package org.codetome.hexameter.core.api;

public class Rectangle {
    public float x, y;
    public float width, height;


    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public Rectangle(Rectangle rect) {
        x = rect.x;
        y = rect.y;
        width = rect.width;
        height = rect.height;
    }

    public Rectangle set(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        return this;
    }

    public float getX() {
        return x;
    }

    public Rectangle setX(float x) {
        this.x = x;

        return this;
    }

    public float getY() {
        return y;
    }

    public Rectangle setY(float y) {
        this.y = y;

        return this;
    }

    public float getWidth() {
        return width;
    }

    public Rectangle setWidth(float width) {
        this.width = width;

        return this;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle setHeight(float height) {
        this.height = height;

        return this;
    }


    public Rectangle setPosition(float x, float y) {
        this.x = x;
        this.y = y;

        return this;
    }

    public Rectangle setSize(float width, float height) {
        this.width = width;
        this.height = height;

        return this;
    }

    public Rectangle setSize(float sizeXY) {
        this.width = sizeXY;
        this.height = sizeXY;

        return this;
    }

    public Rectangle set(Rectangle rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;

        return this;
    }

    public boolean contains(float x, float y) {
        return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
    }

    public boolean contains(Rectangle rectangle) {
        float xMin = rectangle.x;
        float xMax = xMin + rectangle.width;

        float yMin = rectangle.y;
        float yMax = yMin + rectangle.height;

        return ((xMin > x && xMin < x + width) && (xMax > x && xMax < x + width))
                && ((yMin > y && yMin < y + height) && (yMax > y && yMax < y + height));
    }

    public boolean overlaps(Rectangle r) {
        return x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y;
    }


    public float getAspectRatio() {
        return (height == 0) ? Float.NaN : width / height;
    }

    public Rectangle setCenter(float x, float y) {
        setPosition(x - width / 2, y - height / 2);
        return this;
    }

    public String toString() {
        return "[" + x + "," + y + "," + width + "," + height + "]";
    }

}
