package breakout.util;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/**
 * LEGIT
 */
public class SpecUtil
{
    private SpecUtil() { }
    
    /**
     * Checks that oldList and newList contain the same elements in the same order, except for removedElement,
     * which must be present in oldList but not in newList.
     */
    public static <T> boolean sameListsWithElementRemoved(List<T> oldList, List<T> newList, T removedElement)
    {
        if ( oldList.size() != newList.size() + 1 )
        {
            return false;
        }
        
        int index = 0;
        while ( index < newList.size() && oldList.get(index) == newList.get(index) )
        {
            index++;
        }
        
        if ( oldList.get(index) != removedElement )
        {
            return false;
        }
        
        while ( index < newList.size() && oldList.get(index + 1) == newList.get(index) )
        {
            index++;
        }
        
        return index == newList.size();
    }
    
    public static <T> boolean containsDuplicateObjects(List<T> objects)
    {
        for ( int i = 0; i < objects.size(); ++i )
        {
            for ( int j = i + 1; j < objects.size(); ++j )
            {
                if ( objects.get(i) == objects.get(j) )
                {
                    return true;
                }
            }
        }

        return false;
    }
    
    /**
     * Important: == is used to determine equality
     * Each element is assumed to only appear once in each list
     */
    public static <T> boolean sameElementsInPossiblyDifferentOrder(List<T> xs, List<T> ys)
    {
        if ( xs.size() != ys.size() )
        {
            return false;
        }
        
        return xs.stream().allMatch(x -> ys.stream().anyMatch(y -> x == y));
    }

    public static <T> boolean containsNoNulls(List<T> objects)
    {
        return objects.stream().allMatch(obj -> obj != null);
    }
    
    public static boolean approx(long x, long y, long maxDifference)
    {
        return Math.abs(x - y) < maxDifference;
    }

    public static boolean implies(boolean p, boolean q)
    {
        return !p || q;
    }
    
    public static boolean implies(boolean p, BooleanSupplier q)
    {
        return !p || q.getAsBoolean();
    }

    public static boolean iff(boolean p, boolean q)
    {
        return p == q;
    }
    
    public static <T> boolean let(T value, Predicate<T> predicate)
    {
        return predicate.test(value);
    }
}
