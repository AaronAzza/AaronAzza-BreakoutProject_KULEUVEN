package breakout.walls;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.math.Vector;

class EastWallTest {

	EastWall wall;
	EastWall wall1;
    long TEST_X_COORDINATE;
    long TEST_X_COORDINATE1;

    @BeforeEach
    void setUp() {
        
    	TEST_X_COORDINATE = 200;
        wall = new EastWall(TEST_X_COORDINATE);
    }

    @Test
    void testGetXCoordinate() {
        
        assertEquals(TEST_X_COORDINATE, wall.getXCoordinate(), "X-coordinate should match the one used in the constructor");
    }

    @Test
    void testGetNormal() {
        
        Vector normal = wall.getNormal();
        
        
        assertNotNull(normal);
        assertTrue(normal.isUnitVector());
        assertEquals(Vector.LEFT, normal);
        //negative coordinate
        TEST_X_COORDINATE1 = -200;
        wall1= new EastWall(TEST_X_COORDINATE1);
        Vector normal1 = wall1.getNormal();
        assertNotNull(normal1);
        assertTrue(normal1.isUnitVector());
        assertEquals(Vector.LEFT, normal1);
        
    }
}
