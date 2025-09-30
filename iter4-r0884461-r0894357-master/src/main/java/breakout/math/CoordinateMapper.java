package breakout.math;


/**
 * LEGIT
 */
public class CoordinateMapper
{
    private final IntervalMapper xCoordinateMapper;

    private final IntervalMapper yCoordinateMapper;

    public CoordinateMapper(IntervalMapper xCoordinateMapper, IntervalMapper yCoordinateMapper)
    {
        if ( xCoordinateMapper == null )
        {
            throw new IllegalArgumentException();
        }

        if ( yCoordinateMapper == null )
        {
            throw new IllegalArgumentException();
        }

        this.xCoordinateMapper = xCoordinateMapper;
        this.yCoordinateMapper = yCoordinateMapper;
    }

    public Point map(Point p)
    {
        var x = xCoordinateMapper.map(p.x());
        var y = yCoordinateMapper.map(p.y());

        return new Point(x, y);
    }
}
