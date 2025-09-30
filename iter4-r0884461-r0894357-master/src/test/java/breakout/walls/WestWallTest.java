package breakout.walls;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.math.Vector;

class WestWallTest {

	WestWall wall;
	WestWall wall1;
    long TEST_X_COORDINATE;
    long TEST_X_COORDINATE1;

    @BeforeEach
    void setUp() {
        
    	TEST_X_COORDINATE = 200;
        wall = new WestWall(TEST_X_COORDINATE);
    }

    @Test
    void testGetXCoordinate() {
        
        assertEquals(TEST_X_COORDINATE, wall.getXCoordinate());
    }

    @Test
    void testGetNormal() {
        
        Vector normal = wall.getNormal();
        
        
        assertNotNull(normal);
        assertTrue(normal.isUnitVector());
        assertEquals(Vector.RIGHT, normal);
        //negative coordinate
        TEST_X_COORDINATE1 = -200;
        wall1= new WestWall(TEST_X_COORDINATE1);
        Vector normal1 = wall1.getNormal();
        assertNotNull(normal1);
        assertTrue(normal1.isUnitVector());
        assertEquals(Vector.RIGHT, normal1);
        
}
}
