package breakout.math;

import java.util.Objects;

import breakout.util.SpecUtil;

/**
 * Represents a 2-dimensional integer vector.
 *
 * @immutable
 * 
 * LEGIT
 */
public class Vector
{
    private final long x;
    private final long y;

    public static final Vector DOWN = new Vector(0, 1);
    public static final Vector UP = new Vector(0, -1);
    public static final Vector RIGHT = new Vector(1, 0);
    public static final Vector LEFT = new Vector(-1, 0);
    
    /**
     * We are using integers to represent coordinates.
     * This prevents us from representing useful vectors,
     * such as a unit vector pointing NW.
     * To circumvent this limitation, we sometimes
     * work with "unit" vectors of size 1000,
     * given us three decimal digits of precision.
     * The math has to take into account this extra factor.
     */
    public static final Vector KILO_DOWN = createKiloVector(0, 1000);
    public static final Vector KILO_UP = createKiloVector(0, -1000);
    public static final Vector KILO_RIGHT = createKiloVector(1000, 0);
    public static final Vector KILO_LEFT = createKiloVector(-1000, 0);
    public static final Vector KILO_UP_LEFT = createKiloVector(-707, -707);
    public static final Vector KILO_UP_RIGHT = createKiloVector(707, -707);
    public static final Vector KILO_DOWN_LEFT = createKiloVector(-707, 707);
    public static final Vector KILO_DOWN_RIGHT = createKiloVector(707, 707);

    /**
     * Helper method. Used when our intention is to create a kilovector, i.e.,
     * a vector with length 1000. This method checks that the x, y parameters
     * do indeed form a kilovector.
     * 
     * @pre | SpecUtil.approx(x * x + y * y, 1000000, 1000)
     */
    public static Vector createKiloVector(long x, long y)
    {
        return new Vector(x, y);
    }
    
    /**
     * Return a new Coordinate with given x and y coordinates.
     * 
     * @post | x() == x
     * @post | y() == y
     */
    public Vector(long x, long y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Return this vector's x coordinate
     */
    public long x()
    {
        return x;
    }

    /**
     * Return this vector's y coordinate
     */
    public long y()
    {
        return y;
    }

    /**
     * Return the Coordinate obtained by scaling this coordinate with a given
     * factor.
     * 
     * @creates | result
     * @post | result != null
     * @post | result.x() == x() * factor
     * @post | result.y() == y() * factor
     */
    public Vector multiply(long factor)
    {
        return new Vector(x * factor, y * factor);
    }

    /**
     * Return the Coordinate obtained by adding this vector with vector `other`.
     * 
     * @creates | result
     * @inspects | other
     * @pre | other != null
     * @post | result != null
     * @post | result.x() == x() + other.x()
     * @post | result.y() == y() + other.y()
     */
    public Vector add(Vector other)
    {
        return new Vector(x + other.x, y + other.y);
    }

    /**
     * Returns the coordinate obtained by subtracting vector `other` from this
     * vector.
     * 
     * @creates | result
     * @inspects | other
     * @pre | other != null
     * @post | result != null
     * @post | result.x() == x() - other.x()
     * @post | result.y() == y() - other.y()
     */
    public Vector subtract(Vector other)
    {
        return new Vector(x - other.x, y - other.y);
    }
    
    /**
     * Scales this vector down by dividing its coordinates by the given factor.
     * 
     * @creates | result
     * @pre | d != 0
     * @post | result != null
     * @post | result.x() == x() / d
     * @post | result.y() == y() / d
     */
    public Vector divide(long d)
    {
        return new Vector(x() / d, y() / d);
    }

    /**
     * Computes the dot product of this vector with vector `other`.
     * 
     * @inspects | v
     * @pre | v != null
     * @post | result == x() * v.x() + y() * v.y()
     */
    public long dotProduct(Vector v)
    {
        return v.x * x + v.y * y;
    }

    /**
     * Returns the square of the length of this vector.
     * 
     * @post | result == this.dotProduct(this)
     */
    public long getSquaredLength()
    {
        return this.dotProduct(this);
    }

    /**
     * Returns the (approximate, due to rounding) length of this vector.
     */
    public long getLength()
    {
        return (long) Math.sqrt(getSquaredLength());
    }
    
    /**
     * Rescales this vector.
     */
    public Vector rescale(long newLength)
    {
        return multiply(newLength).divide(getLength());
    }
    
    /**
     * @inspects | kiloNormal
     * @pre | kiloNormal != null
     * @pre | kiloNormal.isKiloUnitVector()
     */
    public Vector kiloBounce(Vector kiloNormal)
    {
        return this.multiply(1000000).subtract(kiloNormal.multiply(2 * this.dotProduct(kiloNormal))).divide(1000000);
    }
    
    /**
     * Checks if this vector is a unit vector.
     * Due to our usage of integer coordinates, there are only 4 possible unit vectors.
     */
    public boolean isUnitVector()
    {
        return (Math.abs(x) == 1 && y == 0) || (x == 0 && Math.abs(y) == 1); 
    }
    
    /**
     * Checks if this vector has (approximately) size 1000.
     */
    public boolean isKiloUnitVector()
    {
        return Math.abs(getLength() - 1000) < 5;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object obj)
    {
        if ( obj instanceof Vector that )
        {
            return this.x == that.x && this.y == that.y;
        }
        else
        {
            return false;
        }
    }
    
    public String toString()
    {
        return String.format("(%d, %d)", x, y);
    }
}
