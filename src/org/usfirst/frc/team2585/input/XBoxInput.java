package org.usfirst.frc.team2585.input;

import org.impact2585.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Input from a single XBox controller
 */
public class XBoxInput extends InputMethod {
	private static double minTriggerActuation = 0.1;
	private Joystick controller;
	
	/**
	 * Initializes the controller
	 */
	public XBoxInput() {
		// 0 is the port # of the driver station the joystick is plugged into
		controller = new Joystick(0);
	}
	
	/* (non-Javadoc)
	 * @see input.InputMethod#shouldIntake()
	 */
	@Override
	public boolean shouldIntake() {
		return controller.getRawAxis(XboxConstants.RIGHT_TRIGGER) > minTriggerActuation;
	}
	
	/* (non-Javadoc)
	 * @see input.InputMethod#shouldOuttake()
	 */
	@Override
	public boolean shouldOuttake() {
		return controller.getRawAxis(XboxConstants.LEFT_TRIGGER) > minTriggerActuation;
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldThrowCube()
	 */
	@Override
	public boolean shouldRotateUp(){
		return controller.getRawButton(XboxConstants.X_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldCollectCube()
	 */
	@Override
	public boolean shouldRotateDown(){
		return controller.getRawButton(XboxConstants.A_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#forwardAmount()
	 */
	@Override
	public double forwardAmount() {
		return -controller.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#rotationAmount()
	 */
	@Override
	public double rotationAmount() {
		// +1 is left, -1 is right
		return -controller.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldClimb()
	 */
	@Override
	public boolean shouldClimb(){
		return controller.getRawButton(XboxConstants.Y_BUTTON);
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldRewind()
	 */
	@Override
	public boolean shouldRewind(){
		return controller.getRawButton(XboxConstants.B_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldExtendHook()
	 */
	public boolean shouldExtendHook() {
		return controller.getRawButton(XboxConstants.RIGHT_BUMPER);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldRetractHook()
	 */
	public boolean shouldRetractHook() {
		return controller.getRawButton(XboxConstants.LEFT_BUMPER);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldToggleGyro()
	 */
	@Override
	public boolean shouldToggleGyro() {
		return controller.getRawButton(XboxConstants.START_BUTTON);
	}
}
