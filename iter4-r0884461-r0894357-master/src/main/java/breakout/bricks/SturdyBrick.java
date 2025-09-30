package breakout.bricks;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import breakout.BreakoutState;
import breakout.balls.Ball;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.util.SpecUtil;
//import logicalcollections.LogicalList;

/**
 * A sturdy brick has multiple lives: it takes more than one hit to make it disappear.
 * @invar | getLivesLeft() >= 0
 */
public class SturdyBrick extends LabeledBrick
{
    public static final List<Color> COLORS = Collections.unmodifiableList(Arrays.asList(new Color(160, 82, 45), new Color(123, 63, 0), new Color(92, 64, 51)));

    public static final Color LABEL_COLOR = Color.WHITE;
    /**
     * @invar | livesLeft >= 0
     */
    private int livesLeft;
    /**
     *@throws IllegalArgumentException |  geometry== null || gridPosition ==null || lives < 0
     *
     * @post| getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     * 
     */
    public SturdyBrick(Rectangle geometry, Point gridPosition, int lives)
    {	
        super(geometry, gridPosition);
        if (lives <0)
    		throw new IllegalArgumentException("Lives must be greater than 0");
        this.livesLeft = lives;
    }

    public int getLivesLeft()
    {
        return livesLeft;
    }
    /**
      * @post | result != null
      * @post | COLORS.contains(result)
      * @post | COLORS.indexOf(result) == getLivesLeft() - 1
     */
    @Override
    public Color getColor()
    {
        return COLORS.get(this.livesLeft - 1);
    }

    /**
     * Specifies what should happen when this brick is hit.
     * In the case of this class, the brick should lose one life.
     * When it has no lives left, it should disappear.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     * @mutates_properties| state.getBrickGrid()
     * @mutates_properties| state.getBricks()
     * @mutates_properties| getLivesLeft()
     * 
     * @post| (old(getLivesLeft()) == 1) ? (getLivesLeft() == 0) : (getLivesLeft() == old(getLivesLeft()) - 1)
     * 
     * @post | getLivesLeft()>0 ||  SpecUtil.sameListsWithElementRemoved(old(state.getBricks()), state.getBricks(), this)
     * @post | getLivesLeft()>0  || state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null
     * @post | getLivesLeft()==0  ||  state.getBricks().equals(old(state.getBricks()))
     * @post | getLivesLeft()==0  ||  state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  this
     * 
     */
    @Override
    public void hit(BreakoutState state, Ball ball)
    {
        livesLeft--;

        if ( livesLeft == 0 )
        {
            state.getBrickGrid().removeBrick(this);
        }
    }

    /**
     * Specifies what should happen when this brick is hit by a strong ball.
     * In the case of this class, the lives should go straight to zero and the brick should disappear.
     * @pre | state!= null
     * @pre | ball != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     * @mutates_properties| state.getBricks()
     * @mutates_properties| getLivesLeft()
     * @post | getLivesLeft()==0
     * @post | old(state.getBricks()).contains(this) 
     *       | && !state.getBricks().contains(this)
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == this)).allMatch(state.getBricks()::contains)
     *       | && state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null
     * @post | result == false
     * 
     */
    @Override
    public boolean strongHit(BreakoutState state, Ball ball)
    {
        livesLeft = 0;
        state.getBrickGrid().removeBrick(this);

        return false;
    }
    /**
     * @post | result.equals(Integer.toString(getLivesLeft()))
     */
    @Override
    public String getLabel()
    {
        return Integer.toString(livesLeft);
    }
    /**
     * @post |result == LABEL_COLOR
     */
    @Override
    public Color getLabelColor()
    {
        return SturdyBrick.LABEL_COLOR;
    }
}
