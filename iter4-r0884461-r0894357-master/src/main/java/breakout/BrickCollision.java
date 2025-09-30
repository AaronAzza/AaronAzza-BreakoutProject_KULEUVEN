package breakout;

import breakout.bricks.Brick;
import breakout.math.Vector;

/**
 * Objects of this class contain data about the collision
 * between a ball and a brick.
 * @immutable
 */
public class BrickCollision extends Collision
{
	/**
	 * @invar | brick != null
	 */
    private final Brick brick;

    /**
     * @throws IllegalArgumentException | time < 0 || kiloNormal == null || !kiloNormal.isKiloUnitVector()
     * @throws IllegalArgumentException | brick == null
     * @post | getKiloNormal() == kiloNormal
     * @post | getMillisecondsUntilCollision() == time
     * @post | getBrick() == brick
     */
    public BrickCollision(long time, Vector kiloNormal, Brick brick)
    {
        super(time, kiloNormal);
        if(brick == null) {
    		throw new IllegalArgumentException();
    	}
        this.brick = brick;
    }

    /**
     * @post | result != null
     */
    public Brick getBrick()
    {
        return this.brick;
    }

    /**
     * LEGIT
     */
    @Override
    public String toString()
    {
        return String.format("Collision(t=%d, n=%s, p=%s)", getMillisecondsUntilCollision(), getKiloNormal(), getBrick().getGridPosition());
    }
}