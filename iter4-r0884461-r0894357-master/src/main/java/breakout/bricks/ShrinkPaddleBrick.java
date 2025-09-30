package breakout.bricks;

import java.awt.Color;

import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.paddles.Paddle;

/**
 * This class represents bricks that disappear after a single hit
 * and cause the paddle to shrink.
 */
public class ShrinkPaddleBrick extends PaddleModifierBrick
{
    public static final Color COLOR = new Color(255, 0, 0);

    public static final String LABEL = "><";
    /**
     * @throws IllegalArgumentException |  geometry== null || gridPosition ==null
     * @post| getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     */
    public ShrinkPaddleBrick(Rectangle geometry, Point gridPosition)
    {
        super(geometry, gridPosition);
    }
    /**
     *
     * @post | result == COLOR
     */
    @Override
    public Color getColor()
    {
        return ShrinkPaddleBrick.COLOR;
    }

    /**
     * This method specifies what must happen to the paddle when a ball hits this brick.
     * In the case of this class, the paddle shrinks. See {@link breakout.paddles.Paddle#shrink()}.
     * @pre | paddle != null
	 *@mutates_properties | paddle.getHalfWidth(), paddle.getTopCenter()
	 *@post | paddle.getHalfWidth() <= old(paddle.getHalfWidth())
	 * @post | paddle.getTopCenter().equals(paddle.clamp(old(paddle.getTopCenter())))
     *  
     */
    @Override
    public void modifyPaddle(Paddle paddle) {
    	paddle.shrink();
    }

    /**
     *
     * @post | result== COLOR
     */
    @Override
    public Color getLabelColor()
    {
        return ShrinkPaddleBrick.COLOR;
    }

    /**
     * 
     * @post | result== LABEL
     */
    @Override
    public String getLabel()
    {
        return ShrinkPaddleBrick.LABEL;
    }
}
