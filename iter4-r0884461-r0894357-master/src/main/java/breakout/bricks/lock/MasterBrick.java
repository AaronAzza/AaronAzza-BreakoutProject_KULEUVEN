package breakout.bricks.lock;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import breakout.BreakoutState;
import breakout.balls.Ball;
import breakout.bricks.Brick;
import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;
import breakout.ui.Canvas;
import breakout.util.SpecUtil;

/**
 * OGP ONLY CLASS
 * @invar | getLockedBricks() != null
 * @invar | getLockedBricks().stream().allMatch(l -> l != null && l.getMasterBricks().contains(this))
 */
public class MasterBrick extends Brick
{
    public static final Color COLOR = new Color(0, 255, 0);

    // never null and no null elements
    // check every lockedBrick should refer to this
    /**
     * linked lockedBricks (can be >=0)
     * @peerObjects
     * @representationObject
     * @invar | lockedBricks != null	// phase 1
     * @invar | lockedBricks.stream().allMatch(l -> l != null && l.masterBricks.contains(this)) 	// phase 2
     */
    final ArrayList<LockedBrick> lockedBricks;

    /**
     * Constructs a MasterBrick linked to the bricks in lockedBricks
     * @throws IllegalArgumentException | geometry == null || gridPosition == null
     * @throws IllegalArgumentException | lockedBricks == null
     * @throws IllegalArgumentException | lockedBricks.contains(null)
     * the getMasterBricks() property of all the lockedBricks will change, but I'm not
     * sure how to say this in @ mutates_properties or put this in a post condition, but I tried below
     * @post | getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     * @post | getLockedBricks().equals(lockedBricks)
     * the last post condition also implies through the invariants that "this" was added to the getMasterBricks() of each given lockedBrick
     * @post | getLockedBricks().size() == lockedBricks.size()
     */
    public MasterBrick(Rectangle geometry, Point gridPosition, ArrayList<LockedBrick> lockedBricks)
    {
        super(geometry, gridPosition);
        if (lockedBricks == null) {
        	throw new IllegalArgumentException();
        }
        if (lockedBricks.contains(null)) {
        	throw new IllegalArgumentException();
        }
        this.lockedBricks = new ArrayList<LockedBrick>();
        // link all lockedBricks to this!
        for(LockedBrick l : lockedBricks) {
        	linkLock(l);
        }
    }

    /**
     * @post | result.equals(MasterBrick.COLOR)
     */
    @Override
    public Color getColor()
    {
        return COLOR;
    }
    
    // when hit and not linked: destroyed
    // -> by standard ball: bounce
    // -> by strong ball: pass through
    // when hit and linked: not destroyed!
    // -> spawn 1 key ball per linked locked brick (if not already exists)
    // -> spawn balls near hitting ball + spawn red line to linked brick + hitting ball bounce (even if strong)!
    @Override
    /**
     * @pre | ball != null && state != null
     * @pre | state.getBalls().contains(ball)
     * @pre | state.getBricks().contains(this)
     * @inspects | ball
     * @mutates_properties | state.getBricks()
     * @post | SpecUtil.implies(old(getLockedBricks()).isEmpty(),
     * 		 | old(state.getBricks()).contains(this) 
     *       | && !state.getBricks().contains(this)
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == this)).allMatch(state.getBricks()::contains)
     *       | && state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null)
     * @post | SpecUtil.implies(old(getLockedBricks()).isEmpty(), state.getBalls().equals(old(state.getBalls())))
     *
     * the amount off balls might have changed depending on the whether the keyBall had already
     * been spawned in the state, but this uses the private method computePeerBalls()
     */
    public void hit(BreakoutState state, Ball ball)
    {
        if ( this.lockedBricks.isEmpty() )
        {
            state.getBrickGrid().removeBrick(this);
            // ball now bounces (if normal) or proceeds (if strong)
        }
        else
        {
        	//new keyballs are generated at ball's position, with these speeds (you can cycle through this list if more than 4 need
        	//to be generated)
        	Vector[] speeds = { new Vector(-5 , 30) , new Vector( -2 , 30) , new Vector (2,30) , new Vector(5,30) };
        	int i = 0;
        	for(LockedBrick l : lockedBricks) {
        		if(!computePeerBalls().contains(l.keyBallBehavior)) {
        	    	l.keyBallBehavior = new KeyBallBehavior(l);
        	    	state.addBall(ball.getGeometry(), speeds[i%4], l.keyBallBehavior);
        	    	i++;
        		}
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
     * @mutates_properties | state.getBricks(), state.getBalls()
     * @post | SpecUtil.implies(old(getLockedBricks()).isEmpty(), result == false)
     * @post | SpecUtil.implies(!old(getLockedBricks()).isEmpty(), result == true)
     * 
     * @post | SpecUtil.implies(old(getLockedBricks()).isEmpty(), state.getBalls().equals(old(state.getBalls())))
     * the amount off balls might have changed depending on the whether the keyBall had already
     * been spawned in the state, but this uses the private method computePeerBalls()
     * 
     * @post | SpecUtil.implies(result == true, old(state.getBricks()).equals(state.getBricks()))
     * @post | SpecUtil.implies(result == false,
     * 		 | old(state.getBricks()).contains(this) 
     *       | && !state.getBricks().contains(this)
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == this)).allMatch(state.getBricks()::contains)
     *       | && state.getBrickGrid().getBrickAt(this.getGridPosition()) ==  null)
     */
    public boolean strongHit(BreakoutState state, Ball ball) {
    	hit(state, ball);
    	if(lockedBricks.isEmpty())
    		return false;
    	else
    		return true; // true if survives
    }


    /**
     * LEGIT
     * @inspects | canvas
     * @pre | canvas != null
     */
    @Override
    public void paint(Canvas canvas)
    {
        super.paint(canvas);

        String label = "🔑";
        canvas.drawLabel(getColor(), label, getGeometry().getCenter());
    }
    
    // repr exp!
    /**
     * @creates | result
     * @peerObjects
     */
    public ArrayList<LockedBrick> getLockedBricks() {
    	return new ArrayList<LockedBrick>(lockedBricks);
    }
    
    /**
     * @pre | lbrick != null
     * @mutates_properties | getLockedBricks()
     * @post | SpecUtil.implies(!old(getLockedBricks()).contains(lbrick),
     * 		 | SpecUtil.sameListsWithElementRemoved(getLockedBricks(), old(getLockedBricks()), lbrick))
     * @post | SpecUtil.implies(old(getLockedBricks()).contains(lbrick),
     * 		 | old(getLockedBricks()).equals(getLockedBricks()))
     * the peerBalls will have a different speed now too according to computeSpeedModifPkg()
     * but this is a private method!
     */
    public void linkLock(LockedBrick lbrick) {
    	if(!lockedBricks.contains(lbrick)) {		// easier to work with if no duplicates inside lockedBricks
			lockedBricks.add(lbrick);
			lbrick.masterBricks.add(this);
			var peerBalls = computePeerBalls();
			for (var pbal : peerBalls) {
				pbal.speedModifier = pbal.computeSpeedModifPkg();
			}
    	}
    }
    
    /**
     * @pre | lbrick != null
     * @mutates_properties | getLockedBricks()
     * @post | SpecUtil.implies(old(getLockedBricks()).contains(lbrick),
     * 		 | SpecUtil.sameListsWithElementRemoved(old(getLockedBricks()), getLockedBricks(), lbrick))
     * @post | SpecUtil.implies(!old(getLockedBricks()).contains(lbrick),
     * 		 | old(getLockedBricks()).equals(getLockedBricks()))
     * the peerBalls will have a different speed now too according to computeSpeedModifPkg()
     * but this is a private method!
     */
    public void unlinkLock(LockedBrick lbrick) {
    	if(lockedBricks.contains(lbrick)) {		// since linkLock prevents double linking, we just have to remove any lbrick once for it to be gone
			lockedBricks.remove(lbrick);
			lbrick.masterBricks.remove(this);
			var peerBalls = computePeerBalls();
			for (var pbal : peerBalls) {
				pbal.speedModifier = pbal.computeSpeedModifPkg();
			}
    	}
    }
    
    /**
     * Returns the list of key balls that are peer to this MasterBrick
     */
    private ArrayList<KeyBallBehavior> computePeerBalls() {
//    	return new ArrayList<>(lockedBricks.stream().map(l -> l.keyBallBehavior).filter(k -> k != null).toList());
    	return lockedBricks.stream().map(l -> l.keyBallBehavior).filter(k -> k != null).collect(Collectors.toCollection(ArrayList::new));
    	// this should work but its a bit experimental
    }
    
    
    
    
    
    
}
