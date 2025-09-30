package breakout.math;

import java.util.Objects;

/**
 * Represents a circle in a 2-dimensional integer coordinate system.
 *
 * @immutable
 * @invar | getCenter() != null
 * @invar | getRadius() >= 0
 */
public class Circle
{	
	/**
	 * @invar | center != null
	 */
    private final Point center;
    
    /**
     * @invar | radius >= 0
     */
    private final long radius;

    /**
     * Construct a circle with a given center point and diameter.
     * @throws IllegalArgumentException | center == null || radius < 0
     * @post | center == getCenter()
     * @post | radius == getRadius()
     */
    public Circle(Point center, long radius)
    {
    	if (center == null || radius < 0) {
    		throw new IllegalArgumentException();
    	}
    	
        this.center = center;
        this.radius = radius;
    }

    /**
     * Return the center point of this circle.
     */
    public Point getCenter()
    {
        return center;
    }

    public long getRadius()
    {
        return radius;
    }
    
    /**
     * @creates | result
     * @post | result != null
     * @post | result.x() == getRight()
     * @post | result.y() == getCenter().y()
     */
    public Point getRightmostPoint()
    {
        var x = getRight();
        var y = center.y();

        return new Point(x, y);
    }
    
    /**
     * @creates | result
     * @post | result != null
     * @post | result.x() == getLeft()
     * @post | result.y() == getCenter().y()
     */
    public Point getLeftmostPoint()
    {
        var x = getLeft();
        var y = center.y();

        return new Point(x, y);
    }

    /**
     * @creates | result
     * @post | result != null
     * @post | result.x() == getCenter().x()
     * @post | result.y() == getTop()
     */
    public Point getTopmostPoint()
    {
        var x = center.x();
        var y = getTop();

        return new Point(x, y);
    }
    
    /**
     * @creates | result
     * @post | result != null
     * @post | result.x() == getCenter().x()
     * @post | result.y() == getBottom()
     */
    public Point getBottommostPoint()
    {
        var x = center.x();
        var y = getBottom();

        return new Point(x, y);
    }

    /**
     * Return the outermost point of this circle in the given direction `dir`.
     *
     * LEGIT
     *
     * @inspects | direction
     * @creates | result
     * @pre | direction != null
     * @post | result != null
     */
    public Point getPointInDirection(Vector direction)
    {
        return getCenter().add(direction.multiply(getRadius()).divide(direction.getLength()));
    }
    
    /**
     * @post | result == getCenter().x() - getRadius()
     */
    public long getLeft()
    {
        return this.center.x() - radius;
    }
    
    /**
     * @post | result == getCenter().y() - getRadius()
     */
    public long getTop()
    {
        return this.center.y() - radius;
    }
    
    /**
     * @post | result == getCenter().x() + getRadius()
     */
    public long getRight()
    {
        return this.center.x() + radius;
    }
    
    /**
     * @post | result == getCenter().y() + getRadius()
     */
    public long getBottom()
    {
        return this.center.y() + radius;
    }
    
    /**
     * @creates | result
     * @post | result != null
     * @post | result.getLeft() == getLeft()
     * @post | result.getTop() == getTop()
     * @post | result.getWidth() == 2*getRadius()
     * @post | result.getHeight() == 2*getRadius()
     * @post | result.getCenter().equals(getCenter())
     */
    public Rectangle getBoundingRectangle()
    {
        return new Rectangle(getLeft(), getTop(), 2 * getRadius(), 2 * getRadius());
    }

    /**
     * Moves the circle in the direction given by {@code vector}.
     *
     * @inspects | vector
     * @creates | result
     * @pre | vector != null
     * @post | result != null
     * @post | result.getRadius() == getRadius()
     * @post | result.getCenter().equals(this.getCenter().add(vector))
     */
    public Circle move(Vector vector)
    {
        var newCenter = this.center.add(vector);

        return moveTo(newCenter);
    }

    /**
     * Sets the circle's center to {@code newCenter}.
     *
     * @inspects | newCenter
     * @creates | result
     * @pre | newCenter != null
     * @post | result != null
     * @post | result.getRadius() == getRadius()
     * @post | result.getCenter().equals(newCenter)
     */
    public Circle moveTo(Point newCenter)
    {
        return new Circle(newCenter, this.radius);
    }

    /**
     * LEGIT
     */
    @Override
    public boolean equals(Object other)
    {
        if ( other instanceof Circle that )
        {
            return this.center.equals(that.center) && this.radius == that.radius;
        }
        else
        {
            return false;
        }
    }

    /**
     * LEGIT
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(this.center, this.radius);
    }
}
