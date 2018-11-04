package org.hexworks.mixite.core.api

class Rectangle {
    var x: Double = 0.0
    var y: Double = 0.0
    var width: Double = 0.0
    var height: Double = 0.0


    val aspectRatio: Double
        get() = if (height == 0.0) Double.NaN else width / height


    constructor(x: Double, y: Double, width: Double, height: Double) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }


    constructor(rect: Rectangle) {
        x = rect.x
        y = rect.y
        width = rect.width
        height = rect.height
    }

    fun withRectangle(x: Double, y: Double, width: Double, height: Double) = also {
        this.x = x
        this.y = y
        this.width = width
        this.height = height

        return this
    }

    fun withX(x: Double) = also {
        this.x = x
    }

    fun withY(y: Double) = also {
        this.y = y
    }

    fun withWidth(width: Double) = also {
        this.width = width
    }

    fun withHeight(height: Double) = also {
        this.height = height
    }


    fun withPosition(x: Double, y: Double) = also {
        this.x = x
        this.y = y
    }

    fun withSize(width: Double, height: Double) = also {
        this.width = width
        this.height = height
    }

    fun withSize(sizeXY: Double) = also {
        this.width = sizeXY
        this.height = sizeXY
    }

    fun withRectangle(rect: Rectangle) = also {
        this.x = rect.x
        this.y = rect.y
        this.width = rect.width
        this.height = rect.height
    }

    fun contains(x: Double, y: Double): Boolean {
        return this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y
    }

    operator fun contains(rectangle: Rectangle): Boolean {
        val xMin = rectangle.x
        val xMax = xMin + rectangle.width

        val yMin = rectangle.y
        val yMax = yMin + rectangle.height

        return xMin > x && xMin < x + width && xMax > x && xMax < x + width && yMin > y && yMin < y + height && yMax > y && yMax < y + height
    }

    fun overlaps(r: Rectangle): Boolean {
        return x < r.x + r.width && x + width > r.x && y < r.y + r.height && y + height > r.y
    }

    fun withCenter(x: Double, y: Double) = also {
        withPosition(x - width / 2, y - height / 2)
    }

    override fun toString(): String {
        return "[$x,$y,$width,$height]"
    }

}
