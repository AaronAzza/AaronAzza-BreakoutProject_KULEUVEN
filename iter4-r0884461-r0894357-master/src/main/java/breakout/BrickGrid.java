package breakout;

import java.util.ArrayList;
import java.util.stream.Stream;

import breakout.balls.Ball;
import breakout.bricks.Brick;
import breakout.bricks.GrowPaddleBrick;
import breakout.bricks.ShrinkPaddleBrick;
import breakout.bricks.SlowDownBrick;
import breakout.bricks.SpeedUpBrick;
import breakout.bricks.StandardBrick;
import breakout.bricks.StrengtheningBrick;
import breakout.bricks.SturdyBrick;
import breakout.bricks.WeakeningBrick;
import breakout.bricks.lock.LockedBrick;
import breakout.bricks.lock.MasterBrick;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;
import breakout.util.Grid;
import breakout.util.SpecUtil;

/**
 * @invar | getWidth() == getColumnCount() * getBrickWidth()
 * @invar | getHeight() == getRowCount() * getBrickHeight()
 */
public class BrickGrid
{
	/**
	 * @invar | grid != null
	 * @invar | grid.getPositions().stream()
     * |  		.map(pos -> grid.at(pos))
     * |  		.filter(brick -> brick != null)
     * |        .allMatch(brick -> grid.at(brick.getGridPosition()) == brick)
     * this means that all bricks in the grid have a position corresponding to
     * the position in the grid (otherwise it might be shown on the different
     * place than where the brick itself thinks it actually is!
	 */
    private final Grid<Brick> grid;

    /**
     * @invar | brickWidth > 0
     */
    private final int brickWidth;

    /**
     * @invar | brickHeight > 0
     */
    private final int brickHeight;
    
    /**
     * @throws IllegalArgumentException
     *   | columnCount <= 0 || rowCount <= 0
     * @throws IllegalArgumentException
     *   | brickWidth <= 0 || brickHeight <= 0
     * @post | getBricks().isEmpty()
     * @post | getBrickWidth() == brickWidth
     * @post | getBrickHeight() == brickHeight
     * @post | getColumnCount() == columnCount
     * @post | getRowCount() == rowCount
     */
    public BrickGrid(int columnCount, int rowCount, int brickWidth, int brickHeight)
    {
    	if ( brickWidth <= 0 || brickHeight <= 0 )
        {
            throw new IllegalArgumentException();
        }	// grid constructor doet de andere @throws!
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.grid = new Grid<Brick>(columnCount, rowCount);
    }
    
    /**
     * @post | result > 0
     */
    public int getBrickWidth()
    {
        return brickWidth;
    }

    /**
     * @post | result > 0
     */
    public int getBrickHeight()
    {
        return brickHeight;
    }

    /**
     * @post | result > 0
     */
    public int getColumnCount()
    {
        return grid.getWidth();
    }

    /**
     * @post | result > 0
     */
    public int getRowCount()
    {
        return grid.getHeight();
    }

    /**
     * @post | result > 0
     */
    public int getWidth()
    {
        return this.getColumnCount() * this.brickWidth;
    }

    /**
     * @post | result > 0
     */
    public int getHeight()
    {
        return this.getRowCount() * this.brickHeight;
    }
    
    /**
     * @pre | gridPosition != null
     * @pre | isValidGridPosition(gridPosition)
     * @post | result == null || result.getGridPosition().equals(gridPosition)
     */
    public Brick getBrickAt(Point gridPosition)
    {
        return grid.at(gridPosition);
    }

    /**
     * @pre | gridPosition != null
     * @post | result == (0 <= gridPosition.x() && gridPosition.x() < getColumnCount() 
     * 		 | && 0 <= gridPosition.y() && gridPosition.y() < getRowCount())
     */
    public boolean isValidGridPosition(Point gridPosition)
    {
    	
//    	return 0 <= gridPosition.x() && gridPosition.x() < getColumnCount() && 0 <= gridPosition.y() && gridPosition.y() < getRowCount();
        return this.grid.isValidPosition(gridPosition);
    }

    /**
     * Returns the brick at the given {@code gridPosition} if this {@code gridPosition}
     * falls within the borders of the playing field. If not, {@code null} is returned.
     * @pre | gridPosition != null
     * @post | SpecUtil.implies(!isValidGridPosition(gridPosition), result == null)
     * @post | SpecUtil.implies(isValidGridPosition(gridPosition), () -> result == getBrickAt(gridPosition))
     */
    public Brick getBrickAtGridPositionOrNull(Point gridPosition)
    {
        if ( isValidGridPosition(gridPosition) )
        {
            return this.getBrickAt(gridPosition);
        }
        else
        {
            return null;
        }
    }

    /**
     * LEGIT
     */
    public BrickCollision findEarliestCollision(Ball ball)
    {
        var earliestHorizontalCollision = findEarliestHorizontalCollision(ball);
        var earliestVerticalCollision = findEarliestVerticalCollision(ball);

        return Collision.getEarliestCollision(earliestHorizontalCollision, earliestVerticalCollision);
    }

    /**
     * LEGIT
     */
    private BrickCollision findEarliestVerticalCollision(Ball ball)
    {
        if ( ball.getVelocity().y() < 0 )
        {
            return findEarliestUpwardsCollision(ball);
        }
        else if ( ball.getVelocity().y() > 0 )
        {
            return findEarliestDownwardsCollision(ball);
        }
        else
        {
            return null;
        }
    }

    /**
     * LEGIT
     */
    private BrickCollision findEarliestHorizontalCollision(Ball ball)
    {
        if ( ball.getVelocity().x() < 0 )
        {
            return findEarliestLeftwardsCollision(ball);
        }
        else if ( ball.getVelocity().x() > 0 )
        {
            return findEarliestRightwardsCollision(ball);
        }
        else
        {
            return null;
        }
    }

    /**
     * LEGIT
     */
    private BrickCollision findEarliestUpwardsCollision(Ball ball)
    {
        var v = ball.getVelocity();
        var p = ball.getGeometry().getPointInDirection(v);
        var y = p.y() / this.brickHeight * this.brickHeight;

        while ( y > 0 )
        {
            var preciseT = (y - p.y()) * 1000 / v.y();
            var x = p.x() + v.x() * preciseT / 1000;
            var brickGridPosition = new Point(Math.floorDiv(x, brickWidth), Math.floorDiv(y, brickHeight) - 1);
            var brick = getBrickAtGridPositionOrNull(brickGridPosition);

            if ( brick != null )
            {
                return new BrickCollision(preciseT / 1000, Vector.KILO_DOWN, brick);
            }

            y -= this.brickHeight;
        }

        return null;
    }

    /**
     * LEGIT
     */
    private BrickCollision findEarliestDownwardsCollision(Ball ball)
    {
        var v = ball.getVelocity();
        var p = ball.getGeometry().getPointInDirection(v);
        var y = (p.y() + this.brickHeight - 1) / this.brickHeight * this.brickHeight;
        var yMax = this.getHeight();

        while ( y < yMax )
        {
            var preciseT = (y - p.y()) * 1000 / v.y();
            var x = p.x() + v.x() * preciseT / 1000;
            var brickGridPosition = new Point(Math.floorDiv(x, brickWidth), Math.floorDiv(y, brickHeight));
            var brick = getBrickAtGridPositionOrNull(brickGridPosition);

            if ( brick != null )
            {
                return new BrickCollision(preciseT / 1000, Vector.KILO_UP, brick);
            }

            y += this.brickHeight;
        }

        return null;
    }

    /**
     * LEGIT
     */
    private BrickCollision findEarliestLeftwardsCollision(Ball ball)
    {
        var v = ball.getVelocity();
        var p = ball.getGeometry().getPointInDirection(v);
        var x = p.x() / this.brickWidth * this.brickWidth;

        while ( x > 0 )
        {
            var preciseT = (x - p.x()) * 1000 / v.x();
            var y = p.y() + v.y() * preciseT / 1000;
            var brickGridPosition = new Point(Math.floorDiv(x, brickWidth) - 1, Math.floorDiv(y, brickHeight)); 
            var brick = getBrickAtGridPositionOrNull(brickGridPosition);

            if ( brick != null )
            {
                return new BrickCollision(preciseT / 1000, Vector.KILO_RIGHT, brick);
            }

            x -= this.brickWidth;
        }

        return null;
    }

    /**
     * LEGIT
     */
    private BrickCollision findEarliestRightwardsCollision(Ball ball)
    {
        var v = ball.getVelocity();
        var p = ball.getGeometry().getPointInDirection(v);
        var x = (p.x() + this.brickWidth - 1) / this.brickWidth * this.brickWidth;
        var xMax = this.getWidth();

        while ( x < xMax )
        {
            var preciseT = (x - p.x()) * 1000 / v.x();
            var y = p.y() + v.y() * preciseT / 1000;
            var brickGridPosition = new Point(Math.floorDiv(x, brickWidth), Math.floorDiv(y, brickHeight));
            var brick = getBrickAtGridPositionOrNull(brickGridPosition);

            if ( brick != null )
            {
                return new BrickCollision(preciseT / 1000, Vector.KILO_LEFT, brick);
            }

            x += this.brickWidth;
        }

        return null;
    }

    /**
     * Checks whether there is a brick at the given position.
     * This method returns {@code false} for positions outside the grid.
     * @pre | gridPosition != null
     * @post | result == (getBrickAtGridPositionOrNull(gridPosition) != null)
     */
    public boolean containsBrickAt(Point gridPosition)
    {
        return this.getBrickAtGridPositionOrNull(gridPosition) != null;
    }
    
    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * through invars/post conditions it becomes clear that other methods such as 
     * containsBrickAt() also change to represent the change of getBricks()
     * @post | getBrickAt(gridPosition) == result
     */
    public StandardBrick addStandardBrick(Point gridPosition)
    {
        var rectangle = getBrickRectangle(gridPosition);
        var brick = new StandardBrick(rectangle, gridPosition);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getLivesLeft() == lives
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public SturdyBrick addSturdyBrick(Point gridPosition, int lives)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new SturdyBrick(rectangle, gridPosition, lives);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }
    
    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | getBrickAt(gridPosition) == null
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public GrowPaddleBrick addGrowPaddleBrick(Point gridPosition)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new GrowPaddleBrick(rectangle, gridPosition);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public ShrinkPaddleBrick addShrinkPaddleBrick(Point gridPosition)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new ShrinkPaddleBrick(rectangle, gridPosition);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public WeakeningBrick addWeakeningBrick(Point gridPosition)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new WeakeningBrick(rectangle, gridPosition);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public StrengtheningBrick addStrengtheningBrick(Point gridPosition)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new StrengtheningBrick(rectangle, gridPosition);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public SpeedUpBrick addSpeedUpBrick(Point gridPosition)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new SpeedUpBrick(rectangle, gridPosition);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public SlowDownBrick addSlowDownBrick(Point gridPosition)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new SlowDownBrick(rectangle, gridPosition);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | result.getLockedBricks().equals(lockedBricks)
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public MasterBrick addMasterBrick(Point gridPosition, ArrayList<LockedBrick> lockedBricks)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new MasterBrick(rectangle, gridPosition, lockedBricks);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @creates | result
     * @pre | gridPosition != null
     * @pre | !containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | result != null
     * @post | result.getGridPosition() == gridPosition
     * @post | result.getGeometry().equals(getBrickRectangle(gridPosition))
     * @post | SpecUtil.sameListsWithElementRemoved(getBricks(), old(getBricks()), result)
     * @post | getBrickAt(gridPosition) == result
     */
    public LockedBrick addLockedBrick(Point gridPosition)
    {
    	var rectangle = getBrickRectangle(gridPosition);
        var brick = new LockedBrick(rectangle, gridPosition);
        this.grid.setAt(gridPosition, brick);
        return brick;
    }

    /**
     * @pre | gridCoordinates != null
     * @pre | isValidGridPosition(gridCoordinates)
     * @creates | result
     * @post | result.equals(new Rectangle(gridCoordinates.x() * getBrickWidth(),
     * 		 | gridCoordinates.y() * getBrickHeight(), getBrickWidth(), getBrickHeight()))
     */
    public Rectangle getBrickRectangle(Point gridCoordinates)
    {
        var left = gridCoordinates.x() * brickWidth;
        var top = gridCoordinates.y() * brickHeight;

        return new Rectangle(left, top, brickWidth, brickHeight);
    }

    /**
     * Checks whether there are any bricks left.
     * @post | result == getBricks().isEmpty()
     */
    public boolean isEmpty()
    {
        return !grid.getPositionStream().anyMatch(this::containsBrickAt);
    }

    /**
     * Returns all bricks from the grid in a list.
     * @post | result != null
     * @post | result.stream().allMatch(brick -> brick != null 
     * | && getBrickAt(brick.getGridPosition()) == brick)
     * @post | !SpecUtil.containsDuplicateObjects(result)
     */
    public ArrayList<Brick> getBricks()
    {
        return new ArrayList<Brick>(enumerateGridPositions()
        		.map(pos -> grid.at(pos))
        		.filter(brick -> brick != null)
        		.toList());
    }

    /**
     * Returns the smallest rectangle that encompasses the entire grid.
     * @creates | result
     * @post | result != null
     * @post | result.equals(new Rectangle(0, 0, getWidth(), getHeight()))
     */
    public Rectangle getBoundingRectangle()
    {
        return new Rectangle(0, 0, getWidth(), getHeight());
    }

    /**
     * Removes the brick at the given {@code gridPosition}.
     * @pre | gridPosition != null
     * @pre | containsBrickAt(gridPosition)
     * @mutates_properties | getBricks()
     * @post | SpecUtil.sameListsWithElementRemoved(old(getBricks()), getBricks(), old(getBrickAt(gridPosition)))
     * @post | getBrickAt(gridPosition) == null
     */
    public void removeBrickAt(Point gridPosition)
    {
    	grid.setAt(gridPosition, null);
    }

    /**
     * Removes the brick from the grid.
     * @pre | brick != null
     * @pre | getBricks().contains(brick)
     * @inspects | brick
     * @mutates_properties | getBricks()
     * @post | SpecUtil.sameListsWithElementRemoved(old(getBricks()), getBricks(), brick)
     * @post | getBrickAt(brick.getGridPosition()) == null
     */
    public void removeBrick(Brick brick)
    {
        // Hint: brick.getGridPosition()
    	removeBrickAt(brick.getGridPosition());
    }

    /**
     * LEGIT
     */
    public Stream<Point> enumerateGridPositions()
    {
        return this.grid.getPositionStream();
    }
}

