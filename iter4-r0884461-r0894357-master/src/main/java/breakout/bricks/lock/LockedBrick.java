package breakout.bricks.lock;

import java.awt.Color;
import java.util.ArrayList;

import breakout.BreakoutState;
import breakout.balls.Ball;
import breakout.balls.StandardBehavior;
import breakout.bricks.Brick;
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;
import breakout.ui.Canvas;
import breakout.util.SpecUtil;

/**
 * OGP ONLY CLASS
 * @invar | getMasterBricks() != null 
 * @invar | getMasterBricks().stream().allMatch(m -> m != null && m.getLockedBricks().contains(this))
 */
public class LockedBrick extends Brick
{
    public static final Color COLOR = new Color(255, 0, 0);
    
    // never null and no null elements
    // check every masterBrick should refer to this
    /**
     * linked masterBricks (can be >=0)
     * @peerObjects
     * @representationObject
     * @invar | masterBricks != null	// phase 1
     * @invar | masterBricks.stream().allMatch(m -> m != null && m.lockedBricks.contains(this))		// phase 2
     */
    final ArrayList<MasterBrick> masterBricks;

    // 0 or 1 kbb: can be null!
    KeyBallBehavior keyBallBehavior;

    /**
     * Constructs a LockedBrick with no peers
     * @throws IllegalArgumentException | geometry == null || gridPosition == null
     * @post | getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     * @post | getMasterBricks().isEmpty()
     */
    public LockedBrick(Rectangle geometry, Point gridPosition)
    {
        super(geometry, gridPosition);
        this.masterBricks = new ArrayList<MasterBrick>();
    }

    @Override
    /**
     * @post | result.equals(LockedBrick.COLOR)
     */
    public Color getColor()
    {
        return COLOR;
    }
    
    // only destroyed when hit by linked keyball
    // when not destroyed: bounce back (even if strong)
    // when destroyed: keyball becomes normal ball and passes through (no bounce)
    /**
     * @pre | ball != null && state != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     * @mutates_properties | ball.getBehavior(), state.getBricks(), getMasterBricks(), getKeyBallBehavior()
     * @post | SpecUtil.implies(old(getKeyBallBehavior()) != null && old(ball.getBehavior()) == getKeyBallBehavior(),
     * 		 | old(state.getBricks()).contains(this) 
     *       | && !state.getBricks().contains(this)
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == this)).allMatch(state.getBricks()::contains)
     *       | && state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null)
     * @post | SpecUtil.implies(!(old(getKeyBallBehavior()) != null && old(ball.getBehavior()) == old(getKeyBallBehavior())),
     * 		 | old(state.getBricks()).equals(state.getBricks()))
     * 
     * @post | SpecUtil.implies(old(getKeyBallBehavior()) != null && old(ball.getBehavior()) == old(getKeyBallBehavior()),
     * 		 | ball.getBehavior() != getKeyBallBehavior())
     * @post | SpecUtil.implies(!(old(getKeyBallBehavior()) != null && old(ball.getBehavior()) == old(getKeyBallBehavior())),
     * 		 | ball.getBehavior() == old(ball.getBehavior()))
     * 
     * @post | SpecUtil.implies(old(getKeyBallBehavior()) != null && old(ball.getBehavior()) == old(getKeyBallBehavior()),
     * 		 | getMasterBricks().isEmpty())
     * @post | SpecUtil.implies(!(old(getKeyBallBehavior()) != null && old(ball.getBehavior()) == old(getKeyBallBehavior())),
     * 		 | getMasterBricks().equals(old(getMasterBricks())))
     * 
     * @post | SpecUtil.implies(old(getKeyBallBehavior()) != null && old(ball.getBehavior()) == old(getKeyBallBehavior()),
     * 		 | getKeyBallBehavior() == null)
     * @post | SpecUtil.implies(!(old(getKeyBallBehavior()) != null && old(ball.getBehavior()) == old(getKeyBallBehavior())),
     * 		 | old(getKeyBallBehavior()) == getKeyBallBehavior())
     */
    @Override
    public void hit(BreakoutState state, Ball ball)
    {
        //hint: if this LockedBrick is destroyed this can affect the speedMofidier field of several KeyBallBehavior's.
    	if(getKeyBallBehavior() != null && ball.getBehavior() == keyBallBehavior) {
    		state.getBrickGrid().removeBrick(this);		// destroy the lock brick
    		ball.setBehavior(new StandardBehavior());
    		keyBallBehavior = null;
    		for(MasterBrick m : getMasterBricks()) {
    			m.unlinkLock(this); 					// sever link with each masterBrick, also updates the speedModifiers!
    		}
    	}
    }

    @Override
    /**
     * @pre | state!= null
     * @pre | ball != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     * @inspects | ball
     * @mutates_properties | state.getBricks()
     * @post | result == true
     * @post | SpecUtil.implies(result == true, old(state.getBricks()).equals(state.getBricks()))
     * @post | SpecUtil.implies(result == false,
     * 		 | old(state.getBricks()).contains(this) 
     *       | && !state.getBricks().contains(this)
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == this)).allMatch(state.getBricks()::contains)
     *       | && state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null)
     */
    public boolean strongHit(BreakoutState state, Ball ball)
    {
    	// a keyBall doesnt use strongHit and a strongBall cant break the LockedBrick: do nothing
        return true;
    }



    @Override
    /**
     * LEGIT
     */
    public void paint(Canvas canvas)
    {
        super.paint(canvas);

        String label = "🔒";
        canvas.drawLabel(getColor(), label, getGeometry().getCenter());
    }
    
    // repr exp!
    /**
     * @creates | result
     * @peerObjects
     */
    public ArrayList<MasterBrick> getMasterBricks() {
    	return new ArrayList<MasterBrick>(masterBricks);
    }
    
    // can return null
    /**
     * @post | result == null || result.getLockedBrick() == this
     */
    public KeyBallBehavior getKeyBallBehavior() {
    	return keyBallBehavior;
    }
    
    /**
     * LEGIT
     * If getKeyBallBehavior == null, creates a new one
     * The behavior corresponds to a key ball spawned at the bottom right corner
     * of the game field (within it).
     */
    public void spawnKeyBall(Rectangle gameField) {
    	Point pos = gameField.getBottomRight().add(new Vector(-1,-1));
    	keyBallBehavior = new KeyBallBehavior(this);
    	Ball bal = new Ball(gameField,new Circle(pos, 500), new Vector(0,0),keyBallBehavior);
    }
    
    
    
    
    
}
