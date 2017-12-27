package org.codetome.hexameter.core.internal.impl;

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

    public boolean contains(float x, float y) {
        return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y;
    }

    public boolean contains(Rectangle rectangle) {
        float xmin = rectangle.x;
        float xmax = xmin + rectangle.width;

        float ymin = rectangle.y;
        float ymax = ymin + rectangle.height;

        return ((xmin > x && xmin < x + width) && (xmax > x && xmax < x + width))
                && ((ymin > y && ymin < y + height) && (ymax > y && ymax < y + height));
    }

    public boolean overlaps(Rectangle r) {
        return x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y;
    }

    public Rectangle set(Rectangle rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;

        return this;
    }

    public Rectangle merge(Rectangle rect) {
        float minX = Math.min(x, rect.x);
        float maxX = Math.max(x + width, rect.x + rect.width);
        x = minX;
        width = maxX - minX;

        float minY = Math.min(y, rect.y);
        float maxY = Math.max(y + height, rect.y + rect.height);
        y = minY;
        height = maxY - minY;

        return this;
    }

    public Rectangle merge(float x, float y) {
        float minX = Math.min(this.x, x);
        float maxX = Math.max(this.x + width, x);
        this.x = minX;
        this.width = maxX - minX;

        float minY = Math.min(this.y, y);
        float maxY = Math.max(this.y + height, y);
        this.y = minY;
        this.height = maxY - minY;

        return this;
    }


    public float getAspectRatio() {
        return (height == 0) ? Float.NaN : width / height;
    }

    public Rectangle setCenter(float x, float y) {
        setPosition(x - width / 2, y - height / 2);
        return this;
    }

    public Rectangle fitOutside(Rectangle rect) {
        float ratio = getAspectRatio();

        if (ratio > rect.getAspectRatio()) {
            setSize(rect.height * ratio, rect.height);
        } else {
            setSize(rect.width, rect.width / ratio);
        }

        setPosition((rect.x + rect.width / 2) - width / 2, (rect.y + rect.height / 2) - height / 2);
        return this;
    }

    public Rectangle fitInside(Rectangle rect) {
        float ratio = getAspectRatio();

        if (ratio < rect.getAspectRatio()) {
            setSize(rect.height * ratio, rect.height);
        } else {
            setSize(rect.width, rect.width / ratio);
        }

        setPosition((rect.x + rect.width / 2) - width / 2, (rect.y + rect.height / 2) - height / 2);
        return this;
    }

    public String toString() {
        return "[" + x + "," + y + "," + width + "," + height + "]";
    }

}
