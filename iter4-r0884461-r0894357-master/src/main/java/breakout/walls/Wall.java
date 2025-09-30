package breakout.walls;

import breakout.Collision;
import breakout.balls.Ball;
import breakout.math.Vector;

/**
 * Supertype for all walls.
 * 
 * A wall is an invisible boundary to the game world.
 * The game world is bounded by three walls: to the left, to the right and at the top.
 * Balls bounce off these walls.
 * 
 * Walls can be seen as infinite long lines.
 */
public abstract class Wall
{
    /**
     * Computes the collision between this wall and the given ball.
     * Returns null if there is no collision. 
     * @pre | ball!=null
     * 
     */
    public abstract Collision findCollision(Ball ball);
    
    /**
     * Normal unit vector.
     * It is perpendicular to the wall.
     * It must have length 1.
     * It must point inwards.
     * 
     * @post | result != null
     * @post | result.isUnitVector()
     * 
     * note: This method is never called, but it should exist and be correctly specced in subclasses
     */
    public abstract Vector getNormal();
}
