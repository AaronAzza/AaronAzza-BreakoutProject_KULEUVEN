package breakout.ui;

import breakout.BreakoutState;

/**
 * LEGIT
 */
public class Chronometer
{
    private long lastTimestamp;

    public Chronometer()
    {
        lastTimestamp = now();
    }

    private long now()
    {
        return System.currentTimeMillis();
    }

    public int millisecondsSinceLastMeasurement()
    {
        long current = now();
        var elapsedMilliseconds = (int) (current - this.lastTimestamp);
        this.lastTimestamp = current;

        return Math.min(elapsedMilliseconds, BreakoutState.MAXIMUM_TIME_DELTA);
    }
}
