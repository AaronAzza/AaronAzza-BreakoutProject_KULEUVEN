package breakout.walls;

import breakout.math.Vector;

/**
 * Supertype for horizontal walls.
 * A horizontal wall can be seen as an infinitely long line parallel to the X-axis going through
 * a certain point on the Y-axis.
 * This point is specified by the y-coordinate field.
 * 
 */
public abstract class HorizontalWall extends Wall
{
	/**
	 * 
	 */
    private final long yCoordinate;
    
    /**
     *  
     * @post | getYCoordinate() == yCoordinate
     * 
     */
    public HorizontalWall(long yCoordinate) {
    
        this.yCoordinate = yCoordinate;
    }
    
    /**
     * Returns the Y-coordinate of the wall.
     */
    public long getYCoordinate()
    {
        return this.yCoordinate;
    }
    
    /**
     * @post | result != null
     * @post | result.isUnitVector()
     * @post | result.x() == 0
     */
    public abstract Vector getNormal(); 
}
