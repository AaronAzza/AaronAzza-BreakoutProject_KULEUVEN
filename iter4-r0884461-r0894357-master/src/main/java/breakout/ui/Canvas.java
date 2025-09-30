package breakout.ui;

import java.awt.Color;
import java.awt.Graphics;

import breakout.math.Circle;
import breakout.math.CoordinateMapper;
import breakout.math.Point;
import breakout.math.Rectangle;

/**
 * LEGIT
 */
public class Canvas
{
    private final Graphics graphics;
    
    private CoordinateMapper pointMapper;
    
    public Canvas(Graphics graphics, CoordinateMapper pointMapper)
    {
        if ( graphics == null )
        {
            throw new IllegalArgumentException();
        }
        
        if ( pointMapper == null )
        {
            throw new IllegalArgumentException();
        }
        
        this.graphics = graphics;
        this.pointMapper = pointMapper;
    }
    
    public void drawFilledRectangle(Color color, Rectangle rectangle)
    {
        var topLeft = transform(rectangle.getTopLeft());
        var bottomRight = transform(rectangle.getBottomRight());
        var left = topLeft.x();
        var top = topLeft.y();
        var width = bottomRight.x() - topLeft.x();
        var height = bottomRight.y() - topLeft.y();
        
        this.graphics.setColor(color);
        this.graphics.fillRect((int) left, (int) top, (int) width, (int) height);
    }
    
    public void drawRectangle(Color color, Rectangle rectangle)
    {
        var topLeft = transform(rectangle.getTopLeft());
        var bottomRight = transform(rectangle.getBottomRight());
        var left = topLeft.x();
        var top = topLeft.y();
        var width = bottomRight.x() - topLeft.x();
        var height = bottomRight.y() - topLeft.y();
        
        this.graphics.setColor(color);
        this.graphics.drawRect((int) left, (int) top, (int) width, (int) height);
    }
    
    public void drawFilledCircle(Color color, Circle circle)
    {
        var boundingRectangle = circle.getBoundingRectangle();
        var topLeft = transform(boundingRectangle.getTopLeft());
        var bottomRight = transform(boundingRectangle.getBottomRight());
        var x = topLeft.x();
        var y = topLeft.y();
        var width = bottomRight.x() - topLeft.x();
        var height = bottomRight.y() - topLeft.y();
        
        this.graphics.setColor(color);
        this.graphics.fillOval((int) x, (int) y, (int) width, (int) height);
    }
    
    public void drawLabel(Color color, String label, Point position)
    {
        var transformedPosition = transform(position);
        var fontMetrics = this.graphics.getFontMetrics();
        var bounds = fontMetrics.getStringBounds(label, this.graphics);
        var width = (int) bounds.getWidth();
        var height = (int) bounds.getHeight();
        
        this.graphics.drawString(label, (int) transformedPosition.x() - width / 2, (int) transformedPosition.y() + height / 2);
    }
    
    public void drawLine(Color color, Point startPoint, Point endPoint)
    {
        var transformedStartPoint = transform(startPoint);
        var transformedEndPoint = transform(endPoint);
        
        this.graphics.setColor(color);
        this.graphics.drawLine((int) transformedStartPoint.x(), (int) transformedStartPoint.y(), (int) transformedEndPoint.x(), (int) transformedEndPoint.y());
    }
    
    private Point transform(Point p)
    {
        return pointMapper.map(p);
    }
}
