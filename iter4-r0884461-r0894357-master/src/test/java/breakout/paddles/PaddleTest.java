package breakout.paddles;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.BreakoutState;
import breakout.math.Interval;
import breakout.math.Point;
import breakout.math.Rectangle;

public class PaddleTest {
    private Paddle paddle;
    private Interval allowedInterval;
    private Point topCenter;
    private long halfWidth;
    private long speed;

    @BeforeEach
    public void setUp() {
        allowedInterval = new Interval(0, 10000);
        topCenter = new Point(5000, 0);
        halfWidth = 1000;
        speed = 200;
        paddle = new Paddle(allowedInterval, topCenter, halfWidth, speed);
    }

    @Test
    public void testConstructorValidInputs() {
        // Test the constructor with valid inputs
        assertDoesNotThrow(() -> new Paddle(allowedInterval, topCenter, halfWidth, speed));
        assertEquals(topCenter, paddle.getTopCenter());
        assertEquals(halfWidth, paddle.getHalfWidth());
        assertEquals(PaddleMotionDirection.STATIONARY, paddle.getMotionDirection());
        assertEquals(speed, paddle.getSpeed());
        assertEquals(allowedInterval, paddle.getAllowedInterval());
        assertEquals(halfWidth * 2, paddle.getWidth());
        assertTrue(paddle.getHeight() >= 0);
        assertEquals(new Rectangle(topCenter.x()-halfWidth, topCenter.y(), 2 * halfWidth, paddle.getHeight()), paddle.getGeometry());
    }

    @Test
    public void testConstructorInvalidInputs() {
        // Test the constructor with various invalid inputs
        assertThrows(IllegalArgumentException.class, () -> new Paddle(null, topCenter, halfWidth, speed));
        assertThrows(IllegalArgumentException.class, () -> new Paddle(allowedInterval, null, halfWidth, speed));
        assertThrows(IllegalArgumentException.class, () -> new Paddle(allowedInterval, topCenter, -1, speed));
        assertThrows(IllegalArgumentException.class, () -> new Paddle(allowedInterval, topCenter, 0, speed));
        assertThrows(IllegalArgumentException.class, () -> new Paddle(allowedInterval, topCenter, halfWidth, -1));
        assertThrows(IllegalArgumentException.class, () -> new Paddle(allowedInterval, topCenter, halfWidth, 0));
        assertThrows(IllegalArgumentException.class, () -> new Paddle(new Interval(0, 100), new Point(150, 0), 50, speed));
        assertThrows(IllegalArgumentException.class, () -> new Paddle(new Interval(0, 100), new Point(70, 0), 50, speed));
        assertThrows(IllegalArgumentException.class, () -> new Paddle(new Interval(0, 100), new Point(20, 0), 50, speed));
    }

    @Test
    public void testSetMotionDirection() {
        // Test setting the motion direction
        paddle.setMotionDirection(PaddleMotionDirection.LEFT);
        assertEquals(PaddleMotionDirection.LEFT, paddle.getMotionDirection());

        paddle.setMotionDirection(PaddleMotionDirection.RIGHT);
        assertEquals(PaddleMotionDirection.RIGHT, paddle.getMotionDirection());
    }

    @Test
    public void testSetTopCenterXAndClamp() {
        // Test moving the paddle within the allowed interval
        paddle.setTopCenterX(3000);
        assertEquals(3000, paddle.getTopCenter().x());

        // Test clamping behavior when setting x outside allowed interval
        paddle.setTopCenterX(-1000);
        assertEquals(halfWidth, paddle.getTopCenter().x());
        assertEquals(new Point(1000, 0), paddle.clamp(new Point(-1000, 0)));
        
        paddle.setTopCenterX(11000);
        assertEquals(allowedInterval.getUpperBound() - halfWidth, paddle.getTopCenter().x());
        assertEquals(new Point(9000, 0), paddle.clamp(new Point(11000, 0)));
    }

    @Test
    public void testMove() {
        // Test moving the paddle to the right
        paddle.move(500);
        assertEquals(5500, paddle.getTopCenter().x());

        // Test moving the paddle outside the allowed interval (should clamp)
        paddle.move(10000);
        assertEquals(allowedInterval.getUpperBound() - halfWidth, paddle.getTopCenter().x());
    }

//    TODO!
//    @Test
//    public void testTick() {
//        // Test the tick method which moves the paddle according to the elapsed time and motion direction
//    	BreakoutState state = new BreakoutState();
//    	paddle.setMotionDirection(PaddleMotionDirection.RIGHT);
//        paddle.tick(BreakoutState state, 1000); // assuming 1000ms has passed
//        assertEquals(5000 + 200 * 1000, paddle.getTopCenter().x()); // assuming no clamping needed
//    }

    @Test
    public void testComputeMovementDistance() {
        // Test the computation of movement distance
    	long distance = paddle.computeMovementDistance(1000);
        assertEquals(0, distance); // speed * elapsedMilliseconds * factor (which is 0 for STATIONARY)
        
        paddle.setMotionDirection(PaddleMotionDirection.LEFT);
        long distance2 = paddle.computeMovementDistance(1000);
        assertEquals(-200000, distance2); // speed * elapsedMilliseconds * factor (which is -1 for LEFT)
        
        paddle.setMotionDirection(PaddleMotionDirection.RIGHT);
        long distance3 = paddle.computeMovementDistance(1000);
        assertEquals(200000, distance3); // speed * elapsedMilliseconds * factor (which is 1 for RIGHT)
    }

    @Test
    public void testScale() {
        // Test scaling the paddle by a factor
        paddle.scale(1100); // Grow by 10%
        assertEquals(1100, paddle.getHalfWidth());

        paddle.scale(900); // Shrink by 10%
        assertEquals(990, paddle.getHalfWidth());

        // Test scaling that would exceed the allowed interval
        paddle.scale(50000);
        assertEquals(allowedInterval.getWidth() / 2, paddle.getHalfWidth());
    }

}
