package org.usfirst.frc.team2585.input;

/**
 * Operator controls for the robot
 */
public abstract class InputMethod {
	/**
	 * @return a boolean denoting whether or not the robot should intake
	 */
	public boolean shouldIntake() {
		return false;
	}

	/**
	 * @return a boolean denoting whether or not the robot should outtake
	 */
	public boolean shouldOuttake() {
		return false;
	}
	
	/**
	 * @return a boolean denoting whether or not the robot should "throw" the cube
	 */
	public boolean shouldRotateUp() {
		return false;
	}
	
	/**
	 * @return a boolean denoting whether or not the robot should turn the cubeCollector
	 */
	public boolean shouldRotateDown() {
		return false;
	}
	
	/**
	 * @return a boolean denoting whether or not the robot should start climbing
	 */
	public boolean shouldClimb() {
		return false;
	}
	
	/**
	 * @return a boolean denoting whether or not the robot should rewind the blue thing
	 */
	public boolean shouldRewind() {
		return false;
	}
	
	
	/**
	 * @return the amount that the robot should drive forward
	 */
	public double forwardAmount() {
		return 0;
	}

	/**
	 * @return the amount that the robot should rotate. +1 is counter-clockwise. -1 is clockwise
	 */
	public double rotationAmount() {
		return 0;
	}
	
	/**
	 * @return whether the hook should extend
	 */
	public boolean shouldExtendHook() {
		return false;
	}
	
	/**
	 * @return whether the hook should retract
	 */
	public boolean shouldRetractHook() {
		return false;
	}
	
	/**
	 * @return whether the robot turn on/off using the gyro
	 */
	public boolean shouldToggleGyro() {
	 		return false;
	}
}
