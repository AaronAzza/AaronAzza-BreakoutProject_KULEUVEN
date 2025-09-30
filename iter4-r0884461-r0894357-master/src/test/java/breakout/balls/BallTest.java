package breakout.balls;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import breakout.math.Circle;
import breakout.math.Point;
import breakout.math.Rectangle;
import breakout.math.Vector;
import breakout.balls.BallBehavior;


class BallTest {
		Ball testBall;
		Circle geometry;
		Vector velocity;
		BallBehavior behavior;
		BallBehavior behavior1;
		BallBehavior behavior2;
		Rectangle allowedArea;
		Point center;
		int radius;
		Color COLOR;
		long milli;
		
		
		@BeforeEach
		void initEach() {
			allowedArea= new Rectangle (100,20,100,20); 
			center = new Point(0,0);
			radius = 3;
			geometry = new Circle(center, radius);
			velocity = new Vector (2,2);
			behavior = new StandardBehavior();
			behavior1= new StrongBallBehavior();
			behavior2 = new WeakBallBehavior();
			testBall = new Ball(allowedArea, geometry, velocity, behavior);
			COLOR = Color.WHITE;
			milli = 271;
			
		}
			
		@Test
		void TestCosntructor() {
			assertThrows(IllegalArgumentException.class, () -> new Ball(allowedArea, geometry, null, behavior));
			assertThrows(IllegalArgumentException.class, () -> new Ball(null, geometry, velocity, behavior));
			assertThrows(IllegalArgumentException.class, () -> new Ball(allowedArea, null, velocity, behavior));
			assertThrows(IllegalArgumentException.class, () -> new Ball(allowedArea, geometry, velocity, null));
		}
		@Test	
		void TestGetGeometry() {
			assertEquals(geometry , testBall.getGeometry());
			}
		@Test	
		void TestGetAllowedArea() {
			assertEquals(allowedArea , testBall.getAllowedArea());
		}
		@Test	
		void TestGetVelocity() {
			assertEquals(velocity , testBall.getVelocity());
		}
		@Test	
		void TestGetBehavior() {
			assertEquals(behavior , testBall.getBehavior());
		}
		@Test	
		void TestGetColor() {
			assertEquals(COLOR , testBall.getColor());
		}
		@Test	
		void TestGetCenter() {
			assertEquals(center , testBall.getCenter());
		}
		@Test
		void testMove() {
		    // Save the original geometry before moving the ball
		    Point originalCenter = testBall.getGeometry().getCenter();
		    long originalRadius = testBall.getGeometry().getRadius();

		    // Move the ball
		    testBall.move(milli);

		    // Check if the geometry has changed
		    assertFalse(testBall.getGeometry().getCenter().equals(originalCenter));
		    assertTrue(testBall.getGeometry().getRadius()==originalRadius );
		
		}
		@Test
		void testSetGeometry() {
			Circle newGeometry;
			newGeometry = new Circle(new Point(10, 10), 3);
			// Set the new geometry
	        testBall.setGeometry(newGeometry);
	        

	        // Verify that the geometry was updated
	        assertEquals(newGeometry, testBall.getGeometry());
		}
		@Test
		void testSetVelocity() {
		// Create a new velocity to set
	    Vector newVelocity = new Vector(5, 5);

	    // Set the new velocity
	    testBall.setVelocity(newVelocity);

	    // Verify that the velocity was updated
	    assertEquals(newVelocity, testBall.getVelocity());
		}
		
		@Test
		void testIsValidScaledVelocity() {
		    // Test with a velocity within the valid range
		    Vector validVelocity = new Vector(6, 7); // This should have a squared length between 25 and 6400
		    assertTrue(testBall.isValidScaledVelocity(validVelocity));

		    // Test with a velocity below the minimum speed
		    Vector slowVelocity = new Vector(1, 1); // This should have a squared length less than 25 (e.g., 1^2 + 2^2 = 5)
		    assertFalse(testBall.isValidScaledVelocity(slowVelocity));

		    // Test with a velocity above the maximum speed
		    Vector fastVelocity = new Vector(90, 50); // This should have a squared length greater than 6400 (e.g., 90^2 + 50^2 = 10600)
		    assertFalse(testBall.isValidScaledVelocity(fastVelocity));
		}

		@Test
		void testgetBehavior() {
			behavior1= new StrongBallBehavior();
			testBall.setBehavior(behavior1);
			assertEquals(behavior1,testBall.getBehavior());
			
			
		}
		
		
		
		
		
			
		
			
			
			
			
		} 
		

		
		
		
		
	


