package breakout.bricks;

import java.awt.Color;

import breakout.BreakoutState;
import breakout.balls.Ball;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.ui.Canvas;
import breakout.util.SpecUtil;
//import logicalcollections.LogicalList;

/**
 * Superclass for all bricks.
 * 
 * All bricks have two properties:
 * - a rectangle (geometry), representing their position in the game world.
 * - a grid position, representing the position in the block grid
 * 
 *@invar| getGridPosition() != null
 *@invar |getGeometry() != null
 *
 */
public abstract class Brick
{
	/**
	 * @invar | geometry != null 
	 */
    private final Rectangle geometry;

    /**
     * @invar | gridPosition != null
     */
    private final Point gridPosition;

    /**
     * Constructor.
     * @throws IllegalArgumentException |  geometry== null || gridPosition ==null
     * @post| getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     * 
     */
    public Brick(Rectangle geometry, Point gridPosition){
    	if (geometry== null || gridPosition ==null)
    		throw new IllegalArgumentException();
        this.geometry = geometry;
        this.gridPosition = gridPosition;
    }

    /**
     * Returns the grid position.
     */
    public Point getGridPosition()
    {
        return this.gridPosition;
    }

    /**
     * Returns the rectangle occupied by this brick in the game world.
     */
    public Rectangle getGeometry()
    {
        return geometry;
    }

    /**
     * Paints the brick using the canvas.
     * 
     * LEGIT
     * 
     * @pre | canvas != null
     * @mutates | canvas
     */
    public void paint(Canvas canvas)
    {
        canvas.drawRectangle(getColor(), getGeometry());
    }

    /**
     * Used in the paint method to determine the color of the rectangle on screen.
     * Can be overriden in the subclasses to give each brick their own color.
     * @post | result != null
     */
    public Color getColor()
    {
        return Color.WHITE;
    }

    /**
     * Called when this brick has been hit by a ball.
     * It is given the full BreakoutState and the Ball which has hit the brick.
     * This method should update the state and/or ball,
     * e.g., remove the brick from the state, change the paddle's size, etc.
     * @pre | state != null
     * @pre | ball != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     */
    public abstract void hit(BreakoutState state, Ball ball);

    /**
     * Called when this brick has been hit by a strong ball.
     * Like the hit method, it should update the state/ball to reflect what
     * happens when this brick is hit by a strong ball. 
     * 
     * Returns true if brick survives, false otherwise.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     * @mutates_properties | state.getBricks()
     * 
     * gebruik hier implies om te testen voor wanneer result == true
     * dat er niks veranderde
     * en bij result == false verandert de getBricks()
     * @post | SpecUtil.implies(result == true, old(state.getBricks()).equals(state.getBricks()))
     * @post | SpecUtil.implies(result == false,
     * 		 | old(state.getBricks()).contains(this) 
     *       | && !state.getBricks().contains(this)
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == this)).allMatch(state.getBricks()::contains)
     *       | && state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null)
     */
    public boolean strongHit(BreakoutState state, Ball ball)
    {
        this.hit(state, ball); 

        return false;
    }
}
