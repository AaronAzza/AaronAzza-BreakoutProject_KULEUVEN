package breakout.bricks.lock;

import java.awt.Color;

import breakout.BreakoutState;
import breakout.BrickCollision;
import breakout.Collision;
import breakout.balls.Ball;
import breakout.balls.BallBehavior;
import breakout.balls.StandardBehavior;
import breakout.math.Vector;
import breakout.ui.Canvas;
import breakout.util.SpecUtil;

/**
 * OGP ONLY CLASS
 * @invar | -3 <= getSpeedModifier() && getSpeedModifier() <= 2
 */
public class KeyBallBehavior extends BallBehavior
{
	// == 1 lockedBrick (never null!)
	/**
	 * @invar | lockedBrick != null
	 * there should be no invar stating that lockedBrick.getKeyBallBehav == this
	 * because when this is lost a new keyBall might be made and the lockedBrick wont point to this
	 * but at the same time this will still point to lockedBrick
	 * so this is no bidir association!
	 */
    LockedBrick lockedBrick;

    // depends on lockedBrick and linked masterBrick
    // value -3 <= x <= 2!
    // if keyball hits wall: do n times ball.speedUp/.slowDown
    /**
     * @invar | lockedBrick.keyBallBehavior != this || speedModifier == computeSpeedModifPkg()
     */
    int speedModifier;

    /**
     * @throws IllegalArgumentException | brick == null
     * @throws IllegalArgumentException | brick.getKeyBallBehavior() != null
     * @post | getLockedBrick() == brick
     * @post | brick.getKeyBallBehavior() == this
     */
    public KeyBallBehavior(LockedBrick brick)
    {
    	if(brick == null || brick.getKeyBallBehavior() != null)
    		throw new IllegalArgumentException();
        this.lockedBrick = brick;
        lockedBrick.keyBallBehavior = this;
        this.speedModifier = computeSpeedModifPkg();
    }

    @Override
    /**
     * @pre | state != null && ball != null && collision != null
     * @pre | state.getBalls().contains(ball)
     * @mutates_properties | ball.getGeometry(), ball.getVelocity(), state.getBricks() , getLockedBrick().getMasterBricks()
     * de bal is sws gemoved!
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     * de bal is onder specifieke omstandigheden veranderd van velocity
     * @post | SpecUtil.implies(getLockedBrick() != collision.getBrick(),
     * 		 | ball.getVelocity().equals(old(ball.getVelocity()).kiloBounce(collision.getKiloNormal())))
     * @post | SpecUtil.implies(getLockedBrick() == collision.getBrick(),
     * 		 | old(ball.getVelocity()).equals(ball.getVelocity()))
     * 
     * @post | SpecUtil.implies(getLockedBrick() != collision.getBrick(), old(state.getBricks()).equals(state.getBricks()))
     * @post | SpecUtil.implies(getLockedBrick() == collision.getBrick(), 
     *       | old(state.getBricks()).contains(getLockedBrick()) 
     *       | && !state.getBricks().contains(getLockedBrick())
     *       | && old(state.getBricks()).size() == state.getBricks().size() + 1
     *       | && old(state.getBricks()).stream().filter(e -> !(e == getLockedBrick())).allMatch(state.getBricks()::contains))
     * we already established that these lists dont contain duplicates through the invariants!
     * 
     * @post | SpecUtil.implies(getLockedBrick() != collision.getBrick(), getLockedBrick().getMasterBricks().equals(old(getLockedBrick().getMasterBricks())))
     * @post | SpecUtil.implies(getLockedBrick() == collision.getBrick(), getLockedBrick().getMasterBricks().isEmpty())
     * 
     * @post | SpecUtil.implies(getLockedBrick() != collision.getBrick(), ball.getBehavior() == this)
     * @post | SpecUtil.implies(getLockedBrick() == collision.getBrick(), ball.getBehavior() != this)
     * if the hit method was called, the ball also changed type to the standard type, however i cant say more that != this because instanceOf
     * is not allowed
     */
    public void bounceOffBrick(BreakoutState state, Ball ball, BrickCollision collision)
    {
        ball.move(collision.getMillisecondsUntilCollision());
        if(lockedBrick == collision.getBrick()) {
        	collision.getBrick().hit(state, ball);
        	// dont change velocity!
        }
        else { // keyBall can only destroy its lockedBrick!
        	var newVelocity = ball.getVelocity().kiloBounce(collision.getKiloNormal());
        	ball.setVelocity(newVelocity);
        }
    }

    @Override
    /**
     * @pre | state != null && ball != null && collision != null
     * @pre | state.getBalls().contains(ball)
     * @mutates_properties | ball.getVelocity(), ball.getGeometry()
     * @inspects | state
     * @mutates_properties | ball.getGeometry(), ball.getVelocity()
     * @post | old(ball.computeDestination(collision.getMillisecondsUntilCollision())).equals(ball.getGeometry())
     * @post | SpecUtil.implies(getSpeedModifier() == 0, ball.getVelocity().equals(old(ball.getVelocity()).kiloBounce(collision.getKiloNormal())))
     * we slow down if speedModifier is negative
     * @post | SpecUtil.implies(getSpeedModifier() < 0, old(ball.getVelocity()).getLength() >= ball.getVelocity().getLength())
     * we speed up if speedModifier is positive
     * @post | SpecUtil.implies(getSpeedModifier() > 0, old(ball.getVelocity()).getLength() <= ball.getVelocity().getLength())
     */
    public void bounceOffWall(BreakoutState state, Ball ball, Collision collision)
    {
    	ball.move(collision.getMillisecondsUntilCollision());
        var newVelocity = ball.getVelocity().kiloBounce(collision.getKiloNormal());
        ball.setVelocity(newVelocity);
        // speed up n times (n = speedModifier)
        for(int i = 0; i < Math.abs(speedModifier); i++) {
        	if(speedModifier > 0)
        		ball.speedUp();
        	else
        		ball.slowDown();
        }
    }

    @Override
    /**
     * LEGIT
     */
    public void paint(Canvas canvas, Ball ball)
    {
        super.paint(canvas, ball);

        canvas.drawLine(Color.RED, lockedBrick.getGeometry().getCenter(), ball.getCenter());
        canvas.drawLabel(Color.WHITE, ""+speedModifier, ball.getCenter().add(new Vector(0,1000)));
    }

    @Override
    /**
     * @post | result == Color.RED
     */
    public Color getColor()
    {
        return Color.RED;
    }

    @Override
    /**
     * @pre | state != null && ball != null
     * @pre | state.getBalls().contains(ball)
     * @ pre | ball.getBehavior() == this
     * I assume a ball would never still be in getBalls() if it doesnt also have this as its ballBehavior!
     * @mutates_properties | state.getBalls(), getLockedBrick().getKeyBallBehavior()
     * @inspects | ball
     * @post | SpecUtil.sameListsWithElementRemoved(old(state.getBalls()), state.getBalls(), ball) 
     * @post | getLockedBrick().getKeyBallBehavior() == null
     */
    public void ballLost(BreakoutState state, Ball ball)
    {
        super.ballLost(state, ball);
        lockedBrick.keyBallBehavior = null;
    }
    
    /**
     * see formula in assignment
     * this formula will give a different value if double linking is allowed, because the amount of masterBricks/lockedBricks will increase!
     * this is actually paradoxical if double linking shouldnt do anything extra compared to doing it once: 
     * the assignment also states that we dont necessarily prohibit duplicates in the arrayLists using an invariant, but these two statements clash
     * In our implementation, we do prohibit duplicates to make sure that double linking/unlinking doesnt change *anything*
     */
    int computeSpeedModifPkg() {
    	//computing sign.
    	int sign = 0;
    	if (lockedBrick.masterBricks.size()%2 == 0)
    		{ sign = -1; }
    	else
    		{sign = 1; }
    	//computing abs
    	int abs = 0;
    	for (MasterBrick mbrick : lockedBrick.masterBricks) {
    		if (mbrick.lockedBricks.size() > abs) { abs = mbrick.lockedBricks.size(); } 
    	}
    	int result = sign * abs;
    	return result >= 0 ? Math.min(result, 2) : Math.max(result, -3);
    	
    }
    
    
    /**
     * @post | result != null
     */
    public LockedBrick getLockedBrick() {
    	return lockedBrick;
    }
    
    /**
     * LEGIT
     */
    public int getSpeedModifier() {
    	return speedModifier;
    }
    
    
    
}
