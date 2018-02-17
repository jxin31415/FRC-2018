package org.usfirst.frc.team2585.systems;

import org.impact2585.lib2585.RampedSpeedController;
import org.usfirst.frc.team2585.robot.Environment;
import org.usfirst.frc.team2585.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;

/**
 * This system grabs onto a bar and lifts the robot up.
 */
public class ClimbSystem extends RobotSystem {
	
	RampedSpeedController climbMotorLeft;
	RampedSpeedController climbMotorRight;
	
	RampedSpeedController hookExtender;
	
	static double motorSpeed = 0.65;
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.RobotSystem#init(org.usfirst.frc.team2585.robot.Environment)
	 */
	@Override
	public void init(Environment environ) {
		super.init(environ);
		// new RampedSpeedController(new Spark(RobotMap.LEFT_DRIVE_MOTOR));
		climbMotorLeft = new RampedSpeedController (new Talon(RobotMap.CLIMB_MOTOR_LEFT));
		climbMotorRight = new RampedSpeedController (new Talon(RobotMap.CLIMB_MOTOR_RIGHT));
		
		hookExtender = new RampedSpeedController (new Talon(RobotMap.CLIMB_MOTOR_RIGHT));
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		climbMotorLeft.destroy();
		climbMotorRight.destroy();
		hookExtender.destroy();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(input.shouldClimb()){
			setClimbMotorSpeed(motorSpeed);
		} else {
			setClimbMotorSpeed(0);
		}
		
		if (input.shouldExtendHook() && input.shouldRetractHook()) {
			setHookExtenderSpeed(0);
		} else if (input.shouldExtendHook()) {
			setHookExtenderSpeed(motorSpeed);
		} else if (input.shouldRetractHook()){
			setHookExtenderSpeed(-motorSpeed);
		} else {
			setHookExtenderSpeed(0);
		}
	}
	
	/**
	 * @param speed the speed to ramp the motor to
	 */
	public void setClimbMotorSpeed(double speed) {
		climbMotorLeft.updateWithSpeed(speed);
		climbMotorRight.updateWithSpeed(speed);
	}
	
	/**
	 * @param speed the speed to set the hook extending motor to
	 */
	public void setHookExtenderSpeed(double speed) {
		hookExtender.updateWithSpeed(speed);
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.RobotSystem#stop()
	 */
	@Override
	public void stop() {
		climbMotorLeft.updateWithSpeed(0);
		climbMotorRight.updateWithSpeed(0);
	}
}