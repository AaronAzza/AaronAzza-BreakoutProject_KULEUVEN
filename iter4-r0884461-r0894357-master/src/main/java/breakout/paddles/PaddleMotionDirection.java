package breakout.paddles;

/**
 * Represents the direction in which a paddle is moving.
 * There are three options:
 * - The paddle is moving left.
 * - The paddle is moving right.
 * - The paddle is not moving at all.
 * 
 * @immutable
 * 
 * LEGIT
 */
public class PaddleMotionDirection
{
    public static PaddleMotionDirection LEFT = new PaddleMotionDirection(-1, "LEFT");

    public static PaddleMotionDirection STATIONARY = new PaddleMotionDirection(0, "STATIONARY");

    public static PaddleMotionDirection RIGHT = new PaddleMotionDirection(1, "RIGHT");

    private final int factor;
    
    private final String stringRepresentation;

    private PaddleMotionDirection(int factor, String stringRepresentation)
    {
        this.factor = factor;
        this.stringRepresentation = stringRepresentation;
    }

    public int getFactor()
    {
        return this.factor;
    }
    
    @Override
    public String toString()
    {
        return stringRepresentation;
    }
}
