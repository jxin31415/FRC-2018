package org.usfirst.frc.team2585.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.systems.WheelSystem;

/**
 * Unit Tests for the wheel system
 */

public class WheelSystemTest {
	private TestInput input;
	private TestWheelSystem wheelSystem;
	
	private double forwardInput;
	private double rotationInput;
	private boolean toggleGyroInput;
	
	private double forwardOutput;
	private double rotationOutput;
	
	private double leftOutput;
	private double rightOutput;
	
	private double gyroAngle;
	private double gyroRate;
	
	/**
	 * Set up the wheel system for testing and initialize inputs to 0
	 */
	@Before
	public void setUp() {
		input = new TestInput();
		newWheelSystem();
		resetInput();
		gyroAngle = 0;
		gyroRate = 0;
	}
	
	/**
	 * Create a new wheel system with reset values of movement and rotation
	 */
	public void newWheelSystem() {
		wheelSystem = new TestWheelSystem();
		TestWheelSystem.IS_TEST_SYSTEM = true;
		wheelSystem.setInput(input);
	}
	
	/**
	 * Set the inputs to their default values
	 */
	public void resetInput() {
		forwardInput = 0;
		rotationInput = 0;
		toggleGyroInput = false;
		forwardOutput = 0;
		rotationOutput = 0;
	}
	
	/**
	 * Tests that motor outputs are 0 when user gives no input
	 */
	@Test
	public void defaultsToZero() {
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == 0);
		Assert.assertTrue(rotationOutput == 0);
	}
	
	/**
	 * Tests that forward ramps with positive difference
	 */
	@Test
	public void testPositiveRamp() {
		forwardInput = 1;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == 0.75);
	}
	
	/**
	 * Tests that forward ramps with negative difference
	 */
	@Test
	public void testNegativeRamp() {
		forwardInput = -1;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == -0.75);
	}
	
	/**
	 * Tests that the deadzone is dead
	 */
	@Test
	public void testDeadzone() {
		// Tests forward deadzone
		forwardInput = 0.1;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == 0);
		
		// Tests backward deadzone
		forwardInput = -0.1;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == 0);
		
		// Close to deadzone should still be able to move forward
		forwardInput = 0.3;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput > 0);
		
		// Close to deadzone should still be able to move backwards
		forwardInput = -0.3;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput < 0);
		
		// Tests right rotation deadzone
		rotationInput = 0.1;
		wheelSystem.run();
		Assert.assertTrue(rotationOutput == 0);
		
		// Tests left rotation deadzone
		rotationInput = -0.1;
		wheelSystem.run();
		Assert.assertTrue(rotationOutput == 0);
		
		// Close to deadzone should still be able to turn right
		rotationInput = 0.3;
		wheelSystem.run();
		Assert.assertTrue(rotationOutput > 0);
		
		// Close to deadzone should still be able to turn left
		rotationInput = -0.3;
		wheelSystem.run();
		wheelSystem.run();
		Assert.assertTrue(rotationOutput < 0);
	}
	
	/**
	 * Test that the robot can drive forward without the gyro
	 */
	@Test
	public void testDriveForward() {
		forwardInput = 1;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput > 0);
		Assert.assertTrue(rotationOutput == 0);
		
		Assert.assertTrue(leftOutput > 0);
		Assert.assertTrue(rightOutput > 0);
		
		Assert.assertTrue(leftOutput == rightOutput);
	}
	
	/**
	 * Test turning left without the gyro
	 */
	@Test
	public void testTurnLeftWithoutGyro() {
		wheelSystem.driveWithoutGyro(0, 1);
		
		Assert.assertTrue(leftOutput < 0);
		Assert.assertTrue(rightOutput > 0);
		Assert.assertTrue(rotationOutput > 0);
	}
	
	/**
	 * Test turning without the gyro
	 */
	@Test
	public void testTurnRightWithoutGyro() {
		wheelSystem.driveWithoutGyro(0, -1);
		
		Assert.assertTrue(leftOutput > 0);
		Assert.assertTrue(rightOutput < 0);
		Assert.assertTrue(rotationOutput < 0);
	}
	
	/**
	 * Test turning left with the gyro
	 */
	@Test
	public void testTurnLeftWithGyro() {
		wheelSystem.driveWithGyro(0, 1);
		
		Assert.assertTrue(leftOutput < 0);
		Assert.assertTrue(rightOutput > 0);
		Assert.assertTrue(rotationOutput > 0);
	}
	
	/**
	 * Test turning right with the gyro
	 */
	@Test
	public void testTurnRightWithGyro() {
		wheelSystem.driveWithGyro(0, -1);
		
		Assert.assertTrue(leftOutput > 0);
		Assert.assertTrue(rightOutput < 0);
		Assert.assertTrue(rotationOutput < 0);
	}
	
	public void testRotateToAngle() {
		wheelSystem.rotateToAngle(90.0);
		
		Assert.assertTrue(rotationOutput < 0);
		
		wheelSystem.rotateToAngle(-90.0);
		Assert.assertTrue(rotationOutput > 0);
	}
	
	/**
	 * Tests that forward returns to 0 after multiple runs
	 */
	@Test
	public void testForwardReturnsToZero() {
		forwardInput = 1;
		for(int i=0; i<10; i++) {
			wheelSystem.run();
		}
		Assert.assertTrue(forwardOutput > 0);
		forwardInput = 0;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == 0);
	}
	
	/**
	 * Tests that backwards returns to 0 after multiple runs
	 */
	@Test
	public void testBackwardsReturnsToZero() {
		forwardInput = -1;
		for(int i=0; i<10; i++) {
			wheelSystem.run();
		}
		Assert.assertTrue(forwardOutput < 0);
		forwardInput = 0;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == 0);
	}
	
	/**
	 * Tests that rotating right returns to 0 after multiple runs
	 */
	@Test
	public void testRotateRightReturnsToZero() {
		rotationInput = 1;
		for(int i=0; i<10; i++) {
			wheelSystem.run();
		}
		Assert.assertTrue(rotationOutput > 0);
		rotationInput = 0;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == 0);
	}
	
	/**
	 * Tests that rotating left returns to 0 after multiple runs
	 */
	@Test
	public void testRotateLeftReturnsToZero() {
		rotationInput = -1;
		for(int i=0; i<10; i++) {
			wheelSystem.run();
		}
		Assert.assertTrue(rotationOutput < 0);
		rotationInput = 0;
		wheelSystem.run();
		Assert.assertTrue(forwardOutput == 0);
	}

	/**
	 * Tests that robot can turn while driving
	 */
	@Test
	public void testRotateRightWhileForward() {
		// test forward and rotate right
		forwardInput = 1;
		rotationInput = 1;
		wheelSystem.run();
		
		Assert.assertTrue(forwardOutput > 0);
		Assert.assertTrue(rotationOutput > 0);
	}
	
	/**
	 * Test that the robot can turn left while driving forward
	 */
	@Test
	public void testRotateLeftWhileForward() {
		// test forward and rotate left
		forwardInput = 1;
		rotationInput = -1;
		wheelSystem.run();
		
		Assert.assertTrue(forwardOutput > 0);
		Assert.assertTrue(rotationOutput < 0);
	}
	
	/**
	 * Test that the robot can rotate left while driving backwards
	 */
	@Test
	public void testRotateLeftWhileBackwards() {
		
		// test backwards and rotate left
		forwardInput = -1;
		rotationInput = -1;
		wheelSystem.run();
		
		Assert.assertTrue(forwardOutput < 0);
		Assert.assertTrue(rotationOutput < 0);
	}
	
	/**
	 * Test that the robot can rotate right while moving backwards
	 */
	@Test
	public void testRotateRightWhileBackwards() {
		// test backwards and rotate right
		forwardInput = -1;
		rotationInput = 1;
		wheelSystem.run();
		
		Assert.assertTrue(forwardOutput < 0);
		Assert.assertTrue(rotationOutput > 0);
	}
	
	/**
	 * Tests the gyro
	 */
	@Test
	public void testGyro() {
		// testing the gyro for positive values
		gyroAngle = 90;
		wheelSystem.driveWithGyro(0, 0);
		Assert.assertTrue(rotationOutput < 0);
		
		// testing the gyro for negative values
		gyroAngle = -90;
		wheelSystem.driveWithGyro(0, 0);
		Assert.assertTrue(rotationOutput > 0);
		
		gyroAngle = 0;
		wheelSystem.driveWithGyro(0, 0);
		Assert.assertTrue(rotationOutput == 0);
	}

	/**
	 * Test the ability to disable the gyro
	 */
	@Test
	public void testGyroToggle() {
		wheelSystem.run();
		gyroAngle = 90.0;
		Assert.assertTrue(rotationOutput == 0);
		
		toggleGyroInput = true;
		wheelSystem.run();
		Assert.assertTrue(rotationOutput < 0);
	}
	
	/**
	 * Testable input for the wheel system tests
	 */
	private class TestInput extends InputMethod {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#forwardAmount()
		 */
		@Override
		public double forwardAmount() {
			return forwardInput;
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#rotationAmount()
		 */
		@Override
		public double rotationAmount() {
			return rotationInput;
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#shouldToggleGyro()
		 */
		@Override
		public boolean shouldToggleGyro() {
			return toggleGyroInput;
		}
	}
	
	/**
	 * A testable wheel system
	 */
	private class TestWheelSystem extends WheelSystem {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.WheelSystem#setSideSpeeds(double, double)
		 */
		@Override
		protected void setSideSpeeds(double leftSpeed, double rightSpeed) {
			leftOutput = leftSpeed;
			rightOutput = rightSpeed;
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.WheelSystem#arcadeDrive(double, double)
		 */
		@Override
		public void arcadeDrive(double forward, double rotation){
			super.arcadeDrive(forward, rotation);
			
			forwardOutput = forward;
			rotationOutput = rotation;
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.WheelSystem#getGyroAngle()
		 */
		@Override
		public double getGyroAngle() {
			return gyroAngle;
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.WheelSystem#getGyroRate()
		 */
		@Override
		public double getGyroRate() {
			return gyroRate;
		}
	}
}
