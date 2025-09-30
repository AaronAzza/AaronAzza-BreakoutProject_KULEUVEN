package breakout.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import breakout.BreakoutState;
import breakout.balls.Ball;
import breakout.bricks.Brick;
import breakout.math.CoordinateMapper;
import breakout.math.Interval;
import breakout.math.IntervalMapper;
import breakout.paddles.Paddle;

/**
 * LEGIT
 */
@SuppressWarnings("serial")
public class GameView extends JPanel
{
    public BreakoutState breakoutState;

    private final PlayerInput playerInput;

    private final Chronometer chronometer;

    private final int scaleFactor;

    private CoordinateMapper coordinateMapper;

    /**
     * Create a new GameView for playing breakout starting from a given
     * breakoutState.
     *
     * @param breakoutState initial state for the game.
     */
    public GameView(BreakoutState breakoutState, int scaleFactor)
    {
        this.breakoutState = breakoutState;
        this.playerInput = new PlayerInput(this);
        this.chronometer = new Chronometer();
        this.scaleFactor = scaleFactor;
        this.coordinateMapper = null;

        setBackground(Color.black);
    }

    private CoordinateMapper getCoordinateMapper()
    {
        if ( coordinateMapper == null )
        {
            coordinateMapper = createCoordinateMapper();
        }

        return coordinateMapper;
    }

    private CoordinateMapper createCoordinateMapper()
    {
        var insets = getInsets();

        var boundingRectangle = breakoutState.getBoundingRectangle();
        var xSourceInterval = new Interval(0, boundingRectangle.getWidth());
        var xTargetInterval = new Interval(insets.left, insets.left + boundingRectangle.getWidth() / scaleFactor);
        var ySourceInterval = new Interval(0, boundingRectangle.getHeight());
        var yTargetInterval = new Interval(insets.top, insets.top + boundingRectangle.getHeight() / scaleFactor);

        var xIntervalMapper = new IntervalMapper(xSourceInterval, xTargetInterval);
        var yIntervalMapper = new IntervalMapper(ySourceInterval, yTargetInterval);

        return new CoordinateMapper(xIntervalMapper, yIntervalMapper);
    }

    private void tick(int elapsedMilliseconds)
    {
        breakoutState.getPaddle().setMotionDirection(playerInput.getPaddleMovement());;

        breakoutState.tick(elapsedMilliseconds);

        if ( breakoutState.isGameOver() )
        {
            exitApplication();
        }
    }

    private void exitApplication()
    {
        var exitMessage = getExitMessage();
        JOptionPane.showMessageDialog(this, exitMessage);
        System.exit(0);
    }

    private String getExitMessage()
    {
        if ( breakoutState.isGameLost() )
        {
            return "Game over :-(";
        }

        if ( breakoutState.isGameWon() )
        {
            return "You won!";
        }

        throw new IllegalStateException();
    }

    @Override
    public Dimension getPreferredSize()
    {
        var insets = getInsets();
        var fieldSize = computeGameFieldSizeInPixels();
        var width = insets.left + insets.right + fieldSize.width;
        var height = insets.top + insets.bottom + fieldSize.height;

        return new Dimension(width, height);
    }

    private Dimension computeGameFieldSizeInPixels()
    {
        var boundingRectangle = this.breakoutState.getBoundingRectangle();
        var width = boundingRectangle.getWidth() / scaleFactor;
        var height = boundingRectangle.getHeight() / scaleFactor;

        return new Dimension((int) width, (int) height);
    }

    @Override
    public boolean isFocusable()
    {
        return true;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        updateGameState();
        renderGameState(g);
        pauseForBreath();
        repaint();
    }

    /**
     * Needed to make animation fluent on linux.
     */
    private void pauseForBreath()
    {
//        try
//        {
//            Thread.sleep(1);
//        } catch (InterruptedException e)
//        {
//        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void updateGameState()
    {
        var elapsedMilliseconds = chronometer.millisecondsSinceLastMeasurement();
        tick(elapsedMilliseconds);
    }

    private void renderGameState(Graphics g)
    {
        var canvas = new Canvas(g, this.getCoordinateMapper());

        clearScreen(canvas);
        paintBricks(canvas);
        paintBalls(canvas);
        paintPaddle(canvas);
    }

    private void clearScreen(Canvas canvas)
    {
        canvas.drawFilledRectangle(Color.BLACK, breakoutState.getBoundingRectangle());
    }

    private void paintPaddle(Canvas canvas)
    {
        Paddle paddle = breakoutState.getPaddle();

        paddle.paint(canvas);
    }

    private void paintBalls(Canvas canvas)
    {
        for ( Ball ball : breakoutState.getBalls() )
        {
            ball.paint(canvas);
        }
    }

    private void paintBricks(Canvas canvas)
    {
        for ( Brick brick : breakoutState.getBricks() )
        {
            brick.paint(canvas);
        }
    }
}
