package breakout;

import static breakout.util.SpecUtil.*;

import breakout.math.Vector;
import breakout.util.SpecUtil;

/**
 * Objects of this class contain collision-related information.
 * Collisions can happen between balls and walls & paddles & bricks.
 * Note that there is a more specialized BrickCollision class for ball/brick collisions.
 * @immutable
 */
public class Collision
{
    /**
     * Time until collision.
     * @invar | millisecondsUntilCollision >= 0
     */
    private final long millisecondsUntilCollision;

    /**
     * Normal vector on the surface that was hit.
     * The vector must have size approximately 1000, see {@link Vector#isKiloUnitVector()}.
     * @invar | kiloNormal.isKiloUnitVector()
     */
    private final Vector kiloNormal;

    /**
     * @throws IllegalArgumentException | millisecondsUntilCollision < 0 || kiloNormal == null || !kiloNormal.isKiloUnitVector()
     * @post | getKiloNormal() == kiloNormal
     * @post | getMillisecondsUntilCollision() == millisecondsUntilCollision
     */
    public Collision(long millisecondsUntilCollision, Vector kiloNormal)
    {
    	if(millisecondsUntilCollision < 0) {
    		throw new IllegalArgumentException();
    	}
    	if(kiloNormal == null || !kiloNormal.isKiloUnitVector()) {
    		throw new IllegalArgumentException();
    	}
        this.millisecondsUntilCollision = millisecondsUntilCollision;
        this.kiloNormal = kiloNormal;
    }
    
    /**
     * @post | result >= 0
     */
    public long getMillisecondsUntilCollision()
    {
        return millisecondsUntilCollision;
    }

    /**
     * @post | result != null
     * @post | result.isKiloUnitVector()
     */
    public Vector getKiloNormal()
    {
        return kiloNormal;
    }

    /**
     * Returns the "earliest" collision, i.e., the one with the lowest milliseconds until collision.
     * Note that the parameters can be null.
     * @post | SpecUtil.implies((c1 == null || (c1 == null && c2 == null)), result == c2)
     * @post | SpecUtil.implies((c2 == null && c1 != null), result == c1)
     * @post | SpecUtil.implies(c1 != null && c2 != null && 
     * | c1.getMillisecondsUntilCollision() <= c2.getMillisecondsUntilCollision(), result == c1)
     * @post | SpecUtil.implies(c1 != null && c2 != null && 
     * | c1.getMillisecondsUntilCollision() > c2.getMillisecondsUntilCollision(), result == c2)
     */
    public static <T extends Collision> T getEarliestCollision(T c1, T c2)
    {
    	// returns c2 if both null
    	if(c1 == null)
    		return c2;
    	if(c2 == null)
    		return c1;
    	// returns c1 if time is equal or less
    	if(c1.getMillisecondsUntilCollision() <= c2.getMillisecondsUntilCollision())
    		return c1;
    	return c2;
    }

    /**
     * LEGIT
     */
    @Override
    public String toString()
    {
        return String.format("Collision(t=%d, n=%s)", millisecondsUntilCollision, kiloNormal);
    }
}
