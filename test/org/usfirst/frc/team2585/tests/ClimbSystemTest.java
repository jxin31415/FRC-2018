package org.usfirst.frc.team2585.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.systems.ClimbSystem;

/**
 * Unit Tests for the climb system
 */
public class ClimbSystemTest {
	private TestInput input;
	private TestClimbSystem climbSystem;
	
	boolean shouldClimb;
	boolean shouldRewind;
	boolean shouldExtendHook;
	boolean shouldRetractHook;
		
	double motorSpeedOutput;
	double hookExtenderOutput;
	
	/**
	 * Create new input and climb system test
	 */
	@Before
	public void setUp() {
		input = new TestInput();
		climbSystem = new TestClimbSystem();
		climbSystem.setInput(input);
	}
	
	/**
	 * Tests that the speed of the motors start as zero when the user gives no input
	 */
	@Test
	public void defaultsToZero() {
		shouldClimb = false;
		shouldRewind = false;
		shouldExtendHook = false;
		shouldRetractHook = false;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput == 0);
		Assert.assertTrue(hookExtenderOutput == 0);
	}
	
	/**
	 * Tests that the speed of the climb motor is more than 0 when climbing
	 */
	@Test
	public void climbMotorRuns() {
		shouldClimb = true;
		shouldRewind = false;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput > 0);
	}
	
	/**
	 * Tests that the speed of the climb motor is less than 0 when rewinding
	 */
	@Test
	public void climbMotorRewinds() {
		shouldClimb = false;
		shouldRewind = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput < 0);
	}
	
	/**
	 * Tests that the speed of the extender motor is more than 0 when extending hook
	 */
	@Test
	public void extendMotorExtends() {
		shouldExtendHook = true;
		shouldRetractHook = false;
		climbSystem.run();
		Assert.assertTrue(hookExtenderOutput > 0);
	}
	
	
	/**
	 * Tests that the speed of the extender motor is less than 0 when retracting hook
	 */
	@Test
	public void extendMotorRetracts() {
		shouldExtendHook = false;
		shouldRetractHook = true;
		climbSystem.run();
		Assert.assertTrue(hookExtenderOutput < 0);
	}
	
	/**
	 * Tests that the speed of the extender motor is 0 when both extend and retract are pressed
	 */
	@Test
	public void extendMotorBothPressed() {
		shouldExtendHook = true;
		shouldRetractHook = true;
		climbSystem.run();
		Assert.assertTrue(hookExtenderOutput == 0);
	}
	
	/**
	 * Tests that the speed of the climb motor is 0 when both climb and rewind are pressed
	 */
	@Test
	public void climbMotorBothPressed() {
		shouldClimb = true;
		shouldRewind = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput == 0);
	}
	
	/**
	 * Tests that the speed of the motors is more than zero when climbing and extending
	 */
	@Test
	public void extendAndClimb() {
		shouldClimb = true;
		shouldExtendHook = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput > 0);
		Assert.assertTrue(hookExtenderOutput > 0);
	}
	
	/**
	 * Tests that the speed of the motors is more than zero when climbing and retracting
	 */
	@Test
	public void retractAndClimb() {
		shouldClimb = true;
		shouldRetractHook = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput > 0);
		Assert.assertTrue(hookExtenderOutput < 0);
	}
	
	/**
	 * Tests that the speed of the motors is more than zero when rewinding and extending
	 */
	@Test
	public void extendAndRewind() {
		shouldRewind = true;
		shouldExtendHook = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput < 0);
		Assert.assertTrue(hookExtenderOutput > 0);
	}
	
	/**
	 * Tests that the speed of the motors is more than zero when rewinding and retracting
	 */
	@Test
	public void retractAndRewind() {
		shouldRewind = true;
		shouldRetractHook = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput < 0);
		Assert.assertTrue(hookExtenderOutput < 0);
	}
	
	/**
	 * Tests that the motors returns to 0 after extending
	 */
	@Test
	public void stationaryAfterExtending() {
		shouldExtendHook = true;
		climbSystem.run();
		shouldExtendHook = false;
		climbSystem.run();
		Assert.assertTrue(hookExtenderOutput == 0);
	}
	
	/**
	 * Tests that the motors returns to 0 after retracting
	 */
	@Test
	public void stationaryAfterRetracting() {
		shouldRetractHook = true;
		climbSystem.run();
		shouldRetractHook = false;
		climbSystem.run();
		Assert.assertTrue(hookExtenderOutput == 0);
	}
	
	/**
	 * Tests that the motors returns to 0 after climbing
	 */
	@Test
	public void stationaryAfterClimbing() {
		shouldClimb = true;
		climbSystem.run();
		shouldClimb = false;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput == 0);
	}
	
	/**
	 * Tests that the motors returns to 0 after rewinding
	 */
	@Test
	public void stationaryAfterRewinding() {
		shouldRewind = true;
		climbSystem.run();
		shouldRewind = false;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput == 0);
	}
	
	/**
	 * Tests that robot still climbs when both hook buttons are pressed
	 */
	@Test
	public void stillClimbs() {
		shouldClimb = true;
		shouldRewind = false;
		shouldExtendHook = true;
		shouldRetractHook = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput > 0);
		Assert.assertTrue(hookExtenderOutput == 0);
	}
	
	/**
	 * Tests that robot still rewinds when both hook buttons are pressed
	 */
	@Test
	public void stillRewinds() {
		shouldClimb = false;
		shouldRewind = true;
		shouldExtendHook = true;
		shouldRetractHook = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput < 0);
		Assert.assertTrue(hookExtenderOutput == 0);
	}
	
	/**
	 * Tests that robot still extends hook when both climb buttons are pressed
	 */
	@Test
	public void stillExtends() {
		shouldClimb = true;
		shouldRewind = true;
		shouldExtendHook = true;
		shouldRetractHook = false;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput == 0);
		Assert.assertTrue(hookExtenderOutput > 0);
	}
	
	/**
	 * Tests that robot still retracts hook when both climb buttons are pressed
	 */
	@Test
	public void stillRetracts() {
		shouldClimb = true;
		shouldRewind = true;
		shouldExtendHook = false;
		shouldRetractHook = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput == 0);
		Assert.assertTrue(hookExtenderOutput < 0);
	}
	
	/**
	 * Tests that robot does nothing when all buttons are pressed
	 */
	@Test
	public void allFourPressed() {
		shouldClimb = true;
		shouldRewind = true;
		shouldExtendHook = true;
		shouldRetractHook = true;
		climbSystem.run();
		Assert.assertTrue(motorSpeedOutput == 0);
		Assert.assertTrue(hookExtenderOutput == 0);
	}
	
	/**
	 * Testable input for the climb system tests
	 */
	private class TestInput extends InputMethod {
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#shouldClimb()
		 * @see org.usfirst.frc.team2585.input.InputMethod#shouldRewind()
		 * @see org.usfirst.frc.team2585.input.InputMethod#shouldRetractHook()
		 * @see org.usfirst.frc.team2585.input.InputMethod#shouldExtendHook()
		 */
		@Override
		public boolean shouldClimb() {
			return shouldClimb;
		}
		
		@Override
		public boolean shouldRewind() {
			return shouldRewind;
		}
		
		@Override
		public boolean shouldExtendHook() {
			return shouldExtendHook;
		}
		
		@Override
		public boolean shouldRetractHook() {
			return shouldRetractHook;
		}
	}
	
	/**
	 * A testable climb system
	 */
	private class TestClimbSystem extends ClimbSystem {

		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.ClimbSystem#setClimbMotorSpeed(double)
		 * @see org.usfirst.frc.team2585.systems.ClimbSystem#setHookExtenderSpeed(double)
		 */
		@Override
		public void setClimbMotorSpeed(double speed) {
			motorSpeedOutput = speed;
		}
		
		@Override 
		public void setHookExtenderSpeed(double speed) {
			hookExtenderOutput = speed;
		}
	}
}
