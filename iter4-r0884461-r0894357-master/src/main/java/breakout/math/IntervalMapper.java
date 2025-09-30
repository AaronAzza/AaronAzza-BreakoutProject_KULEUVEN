package breakout.math;

/**
 * LEGIT
 */
public class IntervalMapper
{
    /**
     * @invar | sourceInterval != null
     */
    private final Interval sourceInterval;

    /**
     * @invar | targetInterval != null
     */
    private final Interval targetInterval;

    /**
     * @post | getSourceInterval() == sourceInterval
     * @post | getTargetInterval() == targetInterval
     * 
     * @throws IllegalArgumentException
     *   | sourceInterval == null
     * @throws IllegalArgumentException
     *   | targetInterval == null
     */
    public IntervalMapper(Interval sourceInterval, Interval targetInterval)
    {
        if ( sourceInterval == null )
        {
            throw new IllegalArgumentException();
        }

        if ( targetInterval == null )
        {
            throw new IllegalArgumentException();
        }

        this.sourceInterval = sourceInterval;
        this.targetInterval = targetInterval;
    }
    
    /**
     * @post | result != null
     */
    public Interval getSourceInterval()
    {
        return this.sourceInterval;
    }
    
    /**
     * @post | result != null
     */
    public Interval getTargetInterval()
    {
        return this.targetInterval;
    }

    /**
     * LEGIT
     */
    public long map(long x)
    {
        return this.targetInterval.fromRelative(sourceInterval.toRelative(x));
    }
}
