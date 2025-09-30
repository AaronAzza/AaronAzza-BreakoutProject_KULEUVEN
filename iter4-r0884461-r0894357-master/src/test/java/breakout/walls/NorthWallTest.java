package breakout.walls;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.math.Vector;

class NorthWallTest {

	NorthWall wall;
	NorthWall wall1;
    long TEST_Y_COORDINATE;
    long TEST_Y_COORDINATE1;
    

    @BeforeEach
    void setUp() {
        
    	TEST_Y_COORDINATE = 10;
        wall = new NorthWall(TEST_Y_COORDINATE);
    }

    @Test
    void testGetYCoordinate() {
        
        assertEquals(TEST_Y_COORDINATE, wall.getYCoordinate());
    }

    @Test
    void testGetNormal() {
        
        Vector normal = wall.getNormal();
        
        
        assertNotNull(normal);
        assertTrue(normal.isUnitVector());
        assertEquals(Vector.DOWN, normal);
        //negative y-coordinate
        TEST_Y_COORDINATE1 = -10;
        wall1= new NorthWall(TEST_Y_COORDINATE1);
        Vector normal1 = wall1.getNormal();
        assertNotNull(normal1);
        assertTrue(normal1.isUnitVector());
        assertEquals(Vector.DOWN, normal);
        

}
}
