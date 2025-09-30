package breakout.bricks;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.bricks.*;

class BrickTest {

	Rectangle geometry;
    Point gridPosition;
    Brick brick;
    
    @BeforeEach
    void setUp() {
        geometry = new Rectangle(new Point(10, 10), new Point(10, 10));
        gridPosition = new Point(2, 3);
        brick = new StandardBrick(geometry, gridPosition);
    }
    
    @Test
    void testConstructorAndProperties() {
        assertEquals(geometry, brick.getGeometry());
        assertEquals(gridPosition, brick.getGridPosition());
    }
     
    @Test
    void testConstructorWithInvalidArguments() {
      
        assertThrows(IllegalArgumentException.class, () -> {
            new StandardBrick(null, gridPosition);
        });
        
        
        assertThrows(IllegalArgumentException.class, () -> {
            new StandardBrick(geometry, null);
        });
    }
    
    @Test
    void testGetGeometry() {
        assertEquals(geometry, brick.getGeometry());
    }
    
    @Test
    void testGetGridPosition() {
        assertEquals(gridPosition, brick.getGridPosition());
    }
    @Test
    void testGetColor() {
        Color expectedColor = new Color(128, 128, 128);
        assertEquals(expectedColor, brick.getColor());
    }
}



