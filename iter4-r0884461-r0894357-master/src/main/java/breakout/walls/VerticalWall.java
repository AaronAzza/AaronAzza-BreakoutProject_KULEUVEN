package breakout.walls;

import breakout.math.Vector;

/**
 * Supertype for vertical walls.
 * A horizontal wall can be seen as an infinitely long line parallel to the Y-axis going through
 * a certain point on the X-axis.
 * This point is specified by the x-coordinate field.
 * 
 */
public abstract class VerticalWall extends Wall
{
	/**
	 * 
	 */
    private final long xCoordinate;
    
    /**
     * 
     * @post | getXCoordinate() == xCoordinate
     */
    public VerticalWall(long xCoordinate) {
    	
        this.xCoordinate = xCoordinate;
    }
    
    public long getXCoordinate()
    {
        return this.xCoordinate;
    }
    
    /**
     * @post | result != null
     * @post | result.isUnitVector()
     * @post | result.y() == 0
     */
    public abstract Vector getNormal();
}
