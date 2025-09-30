package other;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import breakout.BreakoutState;
import breakout.BrickCollision;
import breakout.BrickGrid;
import breakout.Collision;
import breakout.balls.Ball;
import breakout.balls.BallBehavior;
import breakout.balls.StandardBehavior;
import breakout.balls.StrongBallBehavior;
import breakout.balls.TemporaryBehavior;
import breakout.balls.WeakBallBehavior;
import breakout.bricks.BallModifierBrick;
import breakout.bricks.Brick;
import breakout.bricks.GrowPaddleBrick;
import breakout.bricks.LabeledBrick;
import breakout.bricks.PaddleModifierBrick;
import breakout.bricks.ShrinkPaddleBrick;
import breakout.bricks.SlowDownBrick;
import breakout.bricks.SpeedUpBrick;
import breakout.bricks.StandardBrick;
import breakout.bricks.StrengtheningBrick;
import breakout.bricks.SturdyBrick;
import breakout.bricks.WeakeningBrick;
import breakout.bricks.lock.LockedBrick;
import breakout.bricks.lock.MasterBrick;
import breakout.math.Circle;
import breakout.math.CoordinateMapper;
import breakout.math.Interval;
import breakout.math.IntervalMapper;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;
import breakout.paddles.Paddle;
import breakout.paddles.PaddleMotionDirection;
import breakout.ui.Canvas;
import breakout.walls.EastWall;
import breakout.walls.HorizontalWall;
import breakout.walls.NorthWall;
import breakout.walls.VerticalWall;
import breakout.walls.Wall;
import breakout.walls.WestWall;

/**
 * Submission tests check that all expected methods exist
 * and conform to a certain signature.
 */
@Timeout(5)
public class SubmissionTestSuite
{
    private static void assertIsSubtypeOf(Class<?> subtype, Class<?> supertype)
    {
        assertSame(supertype, subtype.getSuperclass());
    }

    private static void assertHasConstructor(Class<?> c, Class<?>... parameterTypes)
    {
        try
        {
            c.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e)
        {
            fail(String.format("Class %s should have constructor with correct parameter types", c.getName()));
        }
    }

    private static void assertHasMethod(Class<?> c, Class<?> returnType, String methodName, Class<?>... parameterTypes)
    {
        try
        {
            var method = c.getMethod(methodName, parameterTypes);

            assertSame(returnType, method.getReturnType());
        } catch (NoSuchMethodException e)
        {
            fail(String.format("Class %s should have method %s with the correct parameter types", c.getName(), methodName));
        }
    }

    private static void assertClassHasSameMembersAsInterface(Class<?> c, Class<?> i)
    {
        for ( var method : i.getMethods() )
        {
            if ( method.getName().startsWith("$constructor") )
            {
                assertHasConstructor(c, method.getParameterTypes());
            }
            else
            {
                assertHasMethod(c, method.getReturnType(), method.getName(), method.getParameterTypes());
            }
        }
    }

    @Nested
    class WallTests
    {
        @Test
        void subtyping()
        {
            assertIsSubtypeOf(EastWall.class, VerticalWall.class);
            assertIsSubtypeOf(WestWall.class, VerticalWall.class);
            assertIsSubtypeOf(VerticalWall.class, Wall.class);
            assertIsSubtypeOf(NorthWall.class, HorizontalWall.class);
            assertIsSubtypeOf(HorizontalWall.class, Wall.class);
        }

        private interface WallTarget
        {
            Collision findCollision(Ball ball);

            Vector getNormal();
        }

        @Test
        void wallMethods()
        {
            assertClassHasSameMembersAsInterface(Wall.class, WallTarget.class);
        }

        private interface HorizontalWallTarget
        {
            long getYCoordinate();
        }

        @Test
        void horizontalWallMethods()
        {
            assertClassHasSameMembersAsInterface(HorizontalWall.class, HorizontalWallTarget.class);
        }

        private interface VerticalWallTarget
        {
            long getXCoordinate();
        }

        @Test
        void verticalWallMethods()
        {
            assertClassHasSameMembersAsInterface(VerticalWall.class, VerticalWallTarget.class);
        }

        private interface EastWallTarget
        {
            void $constructor(long xCoordinate);
        }

        private interface WestWallTarget
        {
            void $constructor(long xCoordinate);
        }

        private interface NorthWallTarget
        {
            void $constructor(long xCoordinate);
        }

        @Test
        void eastWallMethods()
        {
            assertClassHasSameMembersAsInterface(EastWall.class, EastWallTarget.class);
        }

        @Test
        void westWallMethods()
        {
            assertClassHasSameMembersAsInterface(WestWall.class, WestWallTarget.class);
        }

        @Test
        void northWallMethods()
        {
            assertClassHasSameMembersAsInterface(NorthWall.class, NorthWallTarget.class);
        }
    }

    @Nested
    class PaddleTests
    {
        @Test
        void constant()
        {
            assertEquals(1000, Paddle.HEIGHT);
            assertEquals(1100, Paddle.GROW_FACTOR);
            assertEquals(900, Paddle.SHRINK_FACTOR);
        }

        @Test
        void constructor()
        {
            var allowedInterval = Interval.createMaximalInterval();
            var topCenter = new Point(0, 0);
            var halfWidth = 100;
            var speed = 100;

            new Paddle(allowedInterval, topCenter, halfWidth, speed);
        }

        interface PaddleTarget
        {
            PaddleMotionDirection getMotionDirection();

            void setMotionDirection(PaddleMotionDirection direction);

            Point getTopCenter();

            Interval getAllowedInterval();

            long getHalfWidth();

            long getWidth();

            long getHeight();

            long getSpeed();

            Rectangle getGeometry();

            void setTopCenterX(long x);

            void move(long distance);

            void tick(BreakoutState state, long elapsedMilliseconds);

            long computeMovementDistance(long elapsedMilliseconds);

            long clamp(long x);

            Point clamp(Point position);

            Collision findCollision(Ball ball);

            void paint(Canvas canvas);

            void scale(long kilofactor);

            void grow();

            void shrink();

            Vector getKiloNormal(long x);
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Paddle.class, PaddleTarget.class);
        }
    }

    @Nested
    class PaddleMotionDirectionTests
    {
        @Test
        void constants()
        {
            assertNotSame(PaddleMotionDirection.LEFT, PaddleMotionDirection.RIGHT);
            assertNotSame(PaddleMotionDirection.LEFT, PaddleMotionDirection.STATIONARY);
            assertNotSame(PaddleMotionDirection.STATIONARY, PaddleMotionDirection.RIGHT);
        }

        interface PaddleMotionDirectionTarget
        {
            int getFactor();
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(PaddleMotionDirection.class, PaddleMotionDirectionTarget.class);
        }
    }

    @Nested
    class CircleTests
    {
        private interface CircleTarget
        {
            void $constructor(Point center, long radius);

            Point getCenter();

            long getRadius();

            Point getRightmostPoint();

            Point getLeftmostPoint();

            Point getTopmostPoint();

            Point getBottommostPoint();

            Point getPointInDirection(Vector direction);

            long getLeft();

            long getTop();

            long getRight();

            long getBottom();

            Rectangle getBoundingRectangle();

            Circle move(Vector vector);

            Circle moveTo(Point newCenter);
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Circle.class, CircleTarget.class);
        }
    }

    @Nested
    class CoordinateMapperTests
    {
        private interface CoordinateMapperTarget
        {
            void $constructor(IntervalMapper xCoordinateMapper, IntervalMapper yCoordinateMapper);

            Point map(Point p);
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(CoordinateMapper.class, CoordinateMapperTarget.class);
        }
    }

    @Nested
    class IntervalTests
    {
        private interface IntervalTarget
        {
            void $constructor(long lowerBound, long upperBound);

            long getLowerBound();

            long getUpperBound();

            long toRelative(long x);

            long fromRelative(long x);

            long getWidth();

            boolean isInside(long x);
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Interval.class, IntervalTarget.class);
        }

        @Test
        void constants()
        {
            assertEquals(1000, Interval.PRECISION_FACTOR);
        }

        @Test
        void createMaximalInterval()
        {
            Interval i = Interval.createMaximalInterval();
            assertNotNull(i);
        }
    }

    @Nested
    class IntervalMapperTests
    {
        private interface IntervalMapperTarget
        {
            void $constructor(Interval sourceInterval, Interval targetInterval);

            Interval getSourceInterval();

            Interval getTargetInterval();

            long map(long x);
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(IntervalMapper.class, IntervalMapperTarget.class);
        }
    }

    @Nested
    class PointTests
    {
        private interface PointTarget
        {
            void $constructor(long x, long y);
            long x();
            long y();
            Point add(Vector v);
            Point subtract(Vector v);
            Point moveDown(int dy);
            Point moveUp(int dy);
            Point moveLeft(int dx);
            Point moveRight(int dx);
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Point.class, PointTarget.class);
        }
    }

    @Nested
    class RectangleTests
    {
        private interface RectangleTarget
        {
            void $constructor1(Point topLeft, Point bottomRight);

            void $constructor2(long left, long top, long width, long height);

            long getLeft();

            long getTop();

            long getWidth();

            long getHeight();

            long getRight();

            long getBottom();

            void setLeft(long left);

            void setTop(long top);

            void setWidth(long width);

            void setHeight(long height);

            Point getTopLeft();

            Point getBottomRight();

            boolean contains(Point point);

            boolean contains(Circle circle);

            boolean contains(Rectangle other);

            Point getBottomCenter();

            Point getCenter();

            Rectangle copy();

            Rectangle growHeight(long extraHeight);
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Rectangle.class, RectangleTarget.class);
        }
    }

    @Nested
    class VectorTests
    {
        @Test
        void constants()
        {
            assertEquals(new Vector(0, 1), Vector.DOWN);
            assertEquals(new Vector(0, -1), Vector.UP);
            assertEquals(new Vector(1, 0), Vector.RIGHT);
            assertEquals(new Vector(-1, 0), Vector.LEFT);
            assertEquals(new Vector(0, 1000), Vector.KILO_DOWN);
            assertEquals(new Vector(0, -1000), Vector.KILO_UP);
            assertEquals(new Vector(1000, 0), Vector.KILO_RIGHT);
            assertEquals(new Vector(-1000, 0), Vector.KILO_LEFT);
            assertEquals(new Vector(-707, -707), Vector.KILO_UP_LEFT);
            assertEquals(new Vector(707, -707), Vector.KILO_UP_RIGHT);
            assertEquals(new Vector(-707, 707), Vector.KILO_DOWN_LEFT);
            assertEquals(new Vector(707, 707), Vector.KILO_DOWN_RIGHT);
        }
        
        private interface VectorTarget
        {
            void $constructor(long x, long y);
            long x();
            long y();
            Vector multiply(long factor);
            Vector add(Vector other);
            Vector subtract(Vector other);
            Vector divide(long d);
            long dotProduct(Vector v);
            long getSquaredLength();
            long getLength();
            Vector rescale(long newLength);
            Vector kiloBounce(Vector kiloNormal);
            boolean isUnitVector();
            boolean isKiloUnitVector();
        }
        
        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Vector.class, VectorTarget.class);
        }
    }
    
    @Nested
    class BallTests
    {
        @Test
        void constants()
        {
            assertEquals(1050, Ball.SPEED_UP_FACTOR);
            assertEquals(950, Ball.SLOW_DOWN_FACTOR);
            assertEquals(5 * 5, Ball.MINIMUM_SLOWDOWN_SQUARED_SPEED);
            assertEquals(80 * 80, Ball.MAXIMUM_SPEEDUP_SQUARED_SPEED);
        }
        
        private interface BallTarget
        {
            void $constructor(Rectangle allowedArea, Circle geometry, Vector velocity, BallBehavior behavior);
            Circle getGeometry();
            Vector getVelocity();
            Rectangle getAllowedArea();
            BallBehavior getBehavior();
            Color getColor();
            Point getCenter();
            void tick(BreakoutState state, long elapsedMilliseconds);
            void move(long elapsedMilliseconds);
            Circle computeDestination(long elapsedMilliseconds);
            void setGeometry(Circle geometry);
            void setVelocity(Vector velocity);
            boolean isValidScaledVelocity(Vector velocity);
            void paint(Canvas canvas);
            void setBehavior(BallBehavior behavior);
            void speedUp();
            void slowDown();
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Ball.class, BallTarget.class);
        }
    }
    
    @Nested
    class BallBehaviorTests
    {
        @Test
        void subtyping()
        {
            assertIsSubtypeOf(StandardBehavior.class, BallBehavior.class);
            assertIsSubtypeOf(TemporaryBehavior.class, BallBehavior.class);
            assertIsSubtypeOf(StrongBallBehavior.class, TemporaryBehavior.class);
            assertIsSubtypeOf(WeakBallBehavior.class, TemporaryBehavior.class);
        }
        
        private interface BallBehaviorTarget
        {
            void update(BreakoutState state, Ball ball, long elapsedMilliseconds);
            void bounceOffWall(BreakoutState state, Ball ball, Collision collision);
            void bounceOffPaddle(BreakoutState state, Ball ball, Collision collision);
            void bounceOffBrick(BreakoutState state, Ball ball, BrickCollision collision);
            void ballLost(BreakoutState state, Ball ball);
            void paint(Canvas canvas, Ball ball);
            Color getColor();
        }
        
        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(BallBehavior.class, BallBehaviorTarget.class);
        }
        
        @Nested
        class StandardBehaviorTests
        {
            private interface StandardBehaviorTarget
            {
                void $constructor();
            }
            
            @Test
            void members()
            {
                assertClassHasSameMembersAsInterface(StandardBehavior.class, StandardBehaviorTarget.class);
            }
        }
        
        @Nested
        class TemporaryBehaviorTests
        {
            private interface TemporaryBehaviorTarget
            {
                void $constructor(int duration);
                int getTimeLeft();
            }
            
            @Test
            void members()
            {
                assertClassHasSameMembersAsInterface(TemporaryBehavior.class, TemporaryBehaviorTarget.class);
            }
        }
        
        @Nested
        class StrongBallBehaviorTests
        {
            private interface StrongBallBehaviorTarget
            {
                void $constructor();
            }
            
            @Test
            void members()
            {
                assertClassHasSameMembersAsInterface(StrongBallBehavior.class, StrongBallBehaviorTarget.class);
            }
        }
        
        @Nested
        class WeakBallBehaviorTests
        {
            private interface WeakBallBehaviorTarget
            {
                void $constructor();
            }
            
            @Test
            void members()
            {
                assertClassHasSameMembersAsInterface(WeakBallBehavior.class, WeakBallBehaviorTarget.class);
            }
        }
    }
    
    @Nested
    class BrickGridTests
    {
        private interface BrickGridTarget
        {
            void $constructor(int columnCount, int rowCount, int brickWidth, int brickHeight);
            int getBrickWidth();
            int getBrickHeight();
            int getColumnCount();
            int getRowCount();
            int getWidth();
            int getHeight();
            Brick getBrickAt(Point gridPosition);
            boolean isValidGridPosition(Point gridPosition);
            Brick getBrickAtGridPositionOrNull(Point gridPosition);
            BrickCollision findEarliestCollision(Ball ball);
            boolean containsBrickAt(Point gridPosition);
            StandardBrick addStandardBrick(Point gridPosition);
            SturdyBrick addSturdyBrick(Point gridPosition, int lives);
            GrowPaddleBrick addGrowPaddleBrick(Point gridPosition);
            ShrinkPaddleBrick addShrinkPaddleBrick(Point gridPosition);
            WeakeningBrick addWeakeningBrick(Point gridPosition);
            StrengtheningBrick addStrengtheningBrick(Point gridPosition);
            SpeedUpBrick addSpeedUpBrick(Point gridPosition);
            SlowDownBrick addSlowDownBrick(Point gridPosition);
            MasterBrick addMasterBrick(Point gridPosition, ArrayList<LockedBrick> lockedBricks);
            LockedBrick addLockedBrick(Point gridPosition);
            Rectangle getBrickRectangle(Point gridCoordinates);
            boolean isEmpty();
            ArrayList<Brick> getBricks();
            Rectangle getBoundingRectangle();
            void removeBrickAt(Point gridPosition);
            void removeBrick(Brick brick);
            Stream<Point> enumerateGridPositions();
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(BrickGrid.class, BrickGridTarget.class);
        }
    }
    
    @Nested
    class CollisionTests
    {
        private interface CollisionTarget
        {
            void $constructor(long millisecondsUntilCollision, Vector kiloNormal);
            long getMillisecondsUntilCollision();
            Vector getKiloNormal();
        }
        
        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Collision.class, CollisionTarget.class);
        }
        
        @Test
        void earliestCollision()
        {
            assertNull(Collision.getEarliestCollision(null, null));
        }
        
        @Nested
        class BrickCollisionTests
        {
            private interface BrickCollisionTarget
            {
                void $constructor(long millisecondsUntilCollision, Vector kiloNormal, Brick brick);
                long getMillisecondsUntilCollision();
                Vector getKiloNormal();
                Brick getBrick();
            }
            
            @Test
            void members()
            {
                assertClassHasSameMembersAsInterface(BrickCollision.class, BrickCollisionTarget.class);
            }
        }
        
        @Test
        void subtyping()
        {
            assertIsSubtypeOf(BrickCollision.class, Collision.class);
        }
    }
    
    @Nested
    class BrickTests
    {
        private interface BrickTarget
        {
            void $constructor(Rectangle geometry, Point gridPosition);
            Point getGridPosition();
            Rectangle getGeometry();
            void paint(Canvas canvas);
            Color getColor();
            abstract void hit(BreakoutState state, Ball ball);
            boolean strongHit(BreakoutState state, Ball ball);
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(Brick.class, BrickTarget.class);            
        }
        
        @Test
        void subtyping()
        {
            assertIsSubtypeOf(StandardBrick.class, Brick.class);
            assertIsSubtypeOf(LabeledBrick.class, Brick.class);
            assertIsSubtypeOf(PaddleModifierBrick.class, LabeledBrick.class);
            assertIsSubtypeOf(BallModifierBrick.class, LabeledBrick.class);
            assertIsSubtypeOf(ShrinkPaddleBrick.class, PaddleModifierBrick.class);
            assertIsSubtypeOf(GrowPaddleBrick.class, PaddleModifierBrick.class);
            assertIsSubtypeOf(SlowDownBrick.class, BallModifierBrick.class);
            assertIsSubtypeOf(SpeedUpBrick.class, BallModifierBrick.class);
            assertIsSubtypeOf(StrengtheningBrick.class, BallModifierBrick.class);
            assertIsSubtypeOf(WeakeningBrick.class, BallModifierBrick.class);
        }
    }
    
    @Nested
    class BreakoutStateTests
    {
        private interface BreakoutStateTarget
        {
            void $constructor(BrickGrid brickGrid, long initialPaddleHalfWidth, long paddleSpeed);
            ArrayList<Ball> getBalls();
            Paddle getPaddle();
            Rectangle getBoundingRectangle();
            void tick(long elapsedMilliseconds);
            boolean isGameOver();
            boolean isGameWon();
            boolean isGameLost();
            ArrayList<Wall> getWalls();
            ArrayList<Brick> getBricks();
            void removeBall(Ball ball);
            boolean isBallLost(Ball ball);
            Ball addBall(Circle geometry, Vector velocity, BallBehavior behavior);
            BrickGrid getBrickGrid();
        }

        @Test
        void members()
        {
            assertClassHasSameMembersAsInterface(BreakoutState.class, BreakoutStateTarget.class);
        }
    }
    
    @Nested 
    class SelectionOfCorrectionTests
    {
        @Test
        void movingBall()
        {
            var brickGrid = new BrickGrid(10, 10, 100, 20);
            var paddleHalfWidth = 50;
            var paddleSpeed = 10;
            var state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);
            
            var ballPosition = new Point(50, 25);
            var ballGeometry = new Circle(ballPosition, 5);
            var ballVelocity = new Vector(0, -5);
            var ballBehavior = new StandardBehavior();
            var ball = state.addBall(ballGeometry, ballVelocity, ballBehavior);
            
            state.tick(1);
            
            assertEquals(new Point(50, 20), ball.getCenter());
        }
        
        @Test
        void movingPaddleLeft()
        {
            var brickGrid = new BrickGrid(10, 10, 100, 20);
            var paddleHalfWidth = 50;
            var paddleSpeed = 10;
            var state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);
            var paddle = state.getPaddle();
            var originalPaddlePosition = paddle.getTopCenter().x();
            
            paddle.setMotionDirection(PaddleMotionDirection.LEFT);
            state.tick(2);
            
            assertEquals(originalPaddlePosition - 20, paddle.getTopCenter().x());
        }
        
        @Test
        void ballBouncingOffEastWall()
        {
            var brickGrid = new BrickGrid(10, 10, 100, 20);
            var paddleHalfWidth = 50;
            var paddleSpeed = 10;
            var state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);
            
            var ballPosition = new Point(900, 30);
            var ballGeometry = new Circle(ballPosition, 25);
            var ballVelocity = new Vector(25, 10);
            var ballBehavior = new StandardBehavior();
            var ball = state.addBall(ballGeometry, ballVelocity, ballBehavior);
            
            state.tick(4);
            
            assertEquals(new Point(950, 70), ball.getCenter());
            assertEquals(new Vector(-25, 10), ball.getVelocity());
        }
        
        @Nested
        class BallHittingBrickTests
        {
            private BrickGrid brickGrid;
            private BreakoutState state;
            
            @BeforeEach
            void beforeEach()
            {
                brickGrid = new BrickGrid(10, 10, 10000, 2000);
                
                var paddleHalfWidth = 50;
                var paddleSpeed = 10;
                state = new BreakoutState(brickGrid, paddleHalfWidth, paddleSpeed);
            }
            
            private Ball addBallToState(Point ballPosition, int ballRadius, Vector ballVelocity)
            {
                var ballGeometry = new Circle(ballPosition, ballRadius);
                var ballBehavior = new StandardBehavior();
                return state.addBall(ballGeometry, ballVelocity, ballBehavior);
            }
            
            @Test
            void ballHittingTwoStandardBricks()
            {
                brickGrid.addStandardBrick(new Point(0, 0));
                brickGrid.addStandardBrick(new Point(0, 2));

                var ballRadius = 100;
                var ball = addBallToState(new Point(1000, brickGrid.getBrickHeight() * 3 / 2), ballRadius, new Vector(0, -100));
                
                state.tick(36);
                
                assertEquals(new Point(1000, brickGrid.getBrickHeight() * 3 / 2), ball.getCenter());
                assertEquals(new Vector(0, -100), ball.getVelocity());
                assertNull(brickGrid.getBrickAt(new Point(0, 0)));
                assertNull(brickGrid.getBrickAt(new Point(0, 2)));
            }
            
            @Test
            void ballHittingSpeedUpBrick()
            {
                brickGrid.addSpeedUpBrick(new Point(0, 0));            
                var ballRadius = 100;
                var initialSpeed = 60;
                var higherSpeed = initialSpeed * 105 / 100;
                var ball = addBallToState(new Point(1000, brickGrid.getBrickHeight() + ballRadius + initialSpeed), ballRadius, new Vector(0, -initialSpeed));
                
                state.tick(2);
                
                assertEquals(new Point(1000, brickGrid.getBrickHeight() + ballRadius + higherSpeed), ball.getCenter());
                assertEquals(new Vector(0, higherSpeed), ball.getVelocity());
                assertNull(brickGrid.getBrickAt(new Point(0, 0)));
            }
            
            @Test
            void ballHittingStrengtheningBrick2()
            {
                brickGrid.addStrengtheningBrick(new Point(0, 0));
                brickGrid.addStandardBrick(new Point(0, 2)); 
                brickGrid.addStandardBrick(new Point(0, 3));
                var ballRadius = 50;
                var speed = 50;
                var ball = addBallToState(new Point(1000, brickGrid.getBrickHeight() + ballRadius + speed), ballRadius, new Vector(0, -speed));
                
                var dt = 1 + 1900 / 50 + 2000 / 50;
                state.tick(dt);
                // 5950  1950
                assertEquals(new Point(1000, brickGrid.getBrickHeight() + dt * speed), ball.getCenter());
                assertEquals(new Vector(0, speed), ball.getVelocity());
                assertNull(brickGrid.getBrickAt(new Point(0, 0)));
                assertNull(brickGrid.getBrickAt(new Point(0, 2)));
                assertNull(brickGrid.getBrickAt(new Point(0, 3)));
            }
            
            @Test
            void ballHittingWeakeningBrick2()
            {
                brickGrid.addWeakeningBrick(new Point(0, 0));            
                brickGrid.addWeakeningBrick(new Point(0, 2));
                var ballRadius = 50;
                var speed = 50;
                var ball = addBallToState(new Point(1000, brickGrid.getBrickHeight() + ballRadius + speed), ballRadius, new Vector(0, -speed));
                
                var dt = 1 + 1900 / 50 + 1;
                state.tick(dt);
                
                assertEquals(new Point(1000, 3900), ball.getCenter());
                assertEquals(new Vector(0, -speed), ball.getVelocity());
                assertNull(brickGrid.getBrickAt(new Point(0, 0)));
                assertNotNull(brickGrid.getBrickAt(new Point(0, 2)));
            }
            
            @Test
            void winningGame()
            {
                brickGrid.addStandardBrick(new Point(0, 0));            
                var ballRadius = 50;
                var speed = 50;
                var ball = addBallToState(new Point(1000, brickGrid.getBrickHeight() + ballRadius + speed), ballRadius, new Vector(0, -speed));
                
                state.tick(3);
                
                assertEquals(new Point(1000, brickGrid.getBrickHeight() + ballRadius + 2 * speed), ball.getCenter());
                assertEquals(new Vector(0, speed), ball.getVelocity());
                assertNull(brickGrid.getBrickAt(new Point(0, 0)));
                assertTrue(state.isGameWon());
                assertTrue(state.isGameOver());
                assertFalse(state.isGameLost());
            }
        }
    }
}
