package breakout.bricks;

import java.awt.Color;

import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.paddles.Paddle;

/**
 * When a ball hits a brick of this type, the brick immediately disappears
 * and the paddle grows wider.
 */
public class GrowPaddleBrick extends PaddleModifierBrick
{
	
    public static final Color COLOR = new Color(0, 255, 0);

    public static final String LABEL = "<>";
    /**
     * @throws IllegalArgumentException |  geometry== null || gridPosition ==null
     * @post| getGeometry() == geometry
     * @post | getGridPosition() == gridPosition
     */
    public GrowPaddleBrick(Rectangle geometry, Point gridPosition)
    {
        super(geometry, gridPosition);
    }
    /**
     * @post | result == COLOR
     */
    @Override
    public Color getColor()
    {
        return GrowPaddleBrick.COLOR;
    }

    /**
     * This method specifies what must happen to the paddle when a ball hits this brick.
     * In the case of this class, the paddle grows. See {@link breakout.paddles.Paddle#grow()}.
     * @pre | paddle != null
	 *@mutates_properties | paddle.getHalfWidth(), paddle.getTopCenter()
	 * @post | paddle.getTopCenter().equals(paddle.clamp(old(paddle.getTopCenter())))
     *  @post | paddle.getHalfWidth() >= old(paddle.getHalfWidth())
     */
    @Override
    public void modifyPaddle(Paddle paddle) {
    	paddle.grow();    }
    /**
     * @post | result == COLOR
     */
    @Override
    public Color getLabelColor()
    {
        return GrowPaddleBrick.COLOR;
    }
    /**
     * @post | result == LABEL
     */
    @Override
    public String getLabel()
    {
        return LABEL;
    }
}
