package breakout.math;

import java.util.Objects;

/**
 * Represents a rectangle in a 2-dimensional integer coordinate system.
 * The rectangle's sides are parallel to X- and Y-axis.
 * Mathematically, the rectangle can be written as
 * [left, left + width] x [top, top + bottom].
 * @invar | getTop() <= getBottom()
 * @invar | getLeft() <= getRight()
 * @invar | getWidth() >= 0
 * @invar | getHeight() >= 0
 * @invar | getRight() == getLeft() + getWidth()
 * @invar | getBottom() == getTop() + getHeight()
 */
public class Rectangle
{
    private long left;

    private long top;
    
    /**
     * @invar | width >= 0
     */
    private long width;

    /**
     * @invar | height >= 0
     */
    private long height;

    /**
     * Construct a new rectangle with given the coordinates of its top left
     * and bottom right corners.
     * @throws IllegalArgumentException | topLeft == null || bottomRight == null
     * @throws IllegalArgumentException | topLeft.x() > bottomRight.x() || topLeft.y() > bottomRight.y()
     * @post | getLeft() == topLeft.x()
     * @post | getTop() == topLeft.y()
     * @post | getWidth() == bottomRight.x() - topLeft.x()
     * @post | getHeight() == bottomRight.y() - topLeft.y()
     */
    public Rectangle(Point topLeft, Point bottomRight)
    {
    	if (topLeft == null || bottomRight == null)
    		throw new IllegalArgumentException();
    	if (topLeft.x() > bottomRight.x() || topLeft.y() > bottomRight.y())
    		throw new IllegalArgumentException();
    	left = topLeft.x();
    	top = topLeft.y();
    	width = bottomRight.x() - topLeft.x();
    	height = bottomRight.y() - topLeft.y();
    }

    /**
     * Create rectangle given the coordinates of its top left corner
     * and its dimensions.
     * @throws IllegalArgumentException | width < 0 || height < 0
     * @post | getLeft() == left
     * @post | getTop() == top
     * @post | getWidth() == width
     * @post | getHeight() == height
     */
    public Rectangle(long left, long top, long width, long height)
    {
		if (width < 0 || height < 0)
			throw new IllegalArgumentException();
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
    }

    /**
     * Returns x-coordinate of the rectangle's left side.
     */
    public long getLeft()
    {
        return left;
    }

    /**
     * Returns the y-coordinate of the rectangle's top side.
     * Note that since the Y-axis is pointing down,
     * the top side has a lower Y-coordinate than the bottom side.
     */
    public long getTop()
    {
        return top;
    }

    /**
     * Returns the width of this rectangle.
     */
    public long getWidth()
    {
        return width;
    }

    /**
     * Returns the height of this rectangle.
     */
    public long getHeight()
    {
        return height;
    }

    /**
     * Returns the x-coordinate of the right side of this rectangle.
     */
    public long getRight()
    {
        return left + width;
    }

    /**
     * Returns the y-coordinate of the bottom side of this rectangle.
     */
    public long getBottom()
    {
        return top + height;
    }

    /**
     * Updates the x-coordinate of the left side of this rectangle.
     * The width of this rectangle remains unaffected, i.e., using this
     * method moves this rectangle horizontally.
     * @mutates_properties | getLeft()
     * @post | getLeft() == left
     */
    public void setLeft(long left)
    {
    	this.left = left;
    }

    /**
     * Updates the y-coordinate of the top side of this rectangle.
     * The height remains unaffected, i.e., using this method
     * moves the rectangle vertically.
     * @mutates_properties | getTop()
     * @post | getTop() == top
     */
    public void setTop(long top)
    {
		this.top = top;
    }

    /**
     * Updates the width of this rectangle.
     * The width cannot be negative, but is allowed to be zero.
     * @pre | width >= 0
     * @mutates_properties | getWidth(), getRight()
     * @post | getWidth() == width
     * @post | getRight() == old(getLeft()) + width
     */
    public void setWidth(long width)
    {
    	this.width = width;
    }

    /**
     * Updates the height of this rectangle.
     * The height cannot be negative, but is allowed to be zero.
     * @pre | height >= 0
     * @mutates_properties | getHeight(), getBottom()
     * @post | getHeight() == height
     * @post | getBottom() == old(getTop()) + height
     */
    public void setHeight(long height)
    {
    	this.height = height;
    }

    /** 
     * Return the top-left point of this rectangle
     * @creates | result
     * @post | result != null
     * @post | result.x() == getLeft()
     * @post | result.y() == getTop()
     */
    public Point getTopLeft()
    {
        return new Point(left, top);
    }

    /**
     * Return the bottom-right point of this rectangle
     * @creates | result
     * @post | result != null
     * @post | result.x() == getLeft() + getWidth()
     * @post | result.y() == getTop() + getHeight()
     */
    public Point getBottomRight()
    {
        return new Point(left + width, top + height);
    }

    /**
     * Returns whether given point is inside this rectangle.
     * @pre | point != null
     * @post | result == (getLeft() <= point.x() && point.x() <= getLeft() + getWidth()
     *   	 |	&& getTop() <= point.y() && point.y() <= getTop() + getHeight())
     */
    public boolean contains(Point point)
    {
        return left <= point.x() && point.x() <= left+width
        		&& top <= point.y() && point.y() <= top+height;
    }

    /**
     * Return whether this rectangle contains a given circle.
     * 
     * LEGIT
     */
    public boolean contains(Circle circle)
    {
        return contains(circle.getBoundingRectangle());
    }

    /**
     * Return whether this rectangle contains a given other rectangle.
     * @inspects | other
     * @pre | other != null
     * @post | result == (contains(other.getTopLeft()) && contains(other.getBottomRight()))
     */
    public boolean contains(Rectangle other)
    {
        return contains(other.getTopLeft()) && contains(other.getBottomRight());
    }

    /**
     * LEGIT
     */
    public Point getBottomCenter()
    {
        var x = left + width / 2;
        var y = top + height;

        return new Point(x, y);
    }

    /**
     * LEGIT
     */
    public Point getCenter()
    {
        var x = left + width / 2;
        var y = top + height / 2;

        return new Point(x, y);
    }

    /**
     * Returns a copy of this rectangle.
     * @creates | result
     * @post | result != null
     * @post | result.equals(this)
     */
    public Rectangle copy()
    {
        return new Rectangle(getTopLeft(), getBottomRight());
    }

    /**
     * Returns a new rectangle with same left, top and width properties.
     * The height equals this rectangle's height plus the given extra height.
     * @pre | extraHeight >= 0
     * @creates | result
     * @post | result != null
     * @post | result.getTopLeft().equals(getTopLeft())
     * @post | result.getWidth() == getWidth()
     * @post | result.getHeight() == getHeight() + extraHeight
     */
    public Rectangle growHeight(long extraHeight)
    {
        return new Rectangle(left, top, width, height+extraHeight);
    }

    /**
     * LEGIT
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(left, top, width, height);
    }

    /**
     * LEGIT
     */
    @Override
    public boolean equals(Object obj)
    {
        if ( obj instanceof Rectangle that )
        {
            return this.left == that.left && this.top == that.top && this.width == that.width && this.height == that.height;
        }
        else
        {
            return false;
        }
    }

    /**
     * LEGIT
     */
    public String toString()
    {
        return String.format("Rectangle[left=%s, top=%s, width=%s, height=%s]", left, top, width, height);
    }
}
