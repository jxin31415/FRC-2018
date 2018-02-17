package org.usfirst.frc.team2585.systems;

import org.impact2585.lib2585.RampedSpeedController;
import org.usfirst.frc.team2585.robot.Environment;
import org.usfirst.frc.team2585.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;


/**
 * This system intakes the power cubes and loads them on the robot
 */
public class IntakeSystem extends RobotSystem implements Runnable {

	RampedSpeedController intakeMotorRight;
	RampedSpeedController intakeMotorLeft;

	// These numbers need to be adjusted after testing
	static double motorSpeed = 0.65;


	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.RobotSystem#init(org.usfirst.frc.team2585.robot.Environment)
	 */
	@Override
	public void init(Environment environ) {
		super.init(environ);
		intakeMotorRight = new RampedSpeedController(new Spark(RobotMap.INTAKE_MOTOR_RIGHT));
		intakeMotorLeft = new RampedSpeedController(new Spark(RobotMap.INTAKE_MOTOR_LEFT));
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// return if both triggers are pressed
		if (input.shouldIntake() && input.shouldOuttake()) {
			return;
		}
		// intake if left trigger is pressed
		if (input.shouldIntake()) {
			intakeCube();
		}
		// outtake and reverse motors if right trigger is pressed
		else if (input.shouldOuttake()) {
			depositCube();
		}
		else {
			setMotorSpeed(0);
		}
	}
	
	/**
	 * Intake a cube by running the motors in the intake direction
	 */
	public void intakeCube() {
		setMotorSpeed(motorSpeed);
	}
	
	/**
	 * Deposit a cube by running the motors in the outtake direction
	 */
	public void depositCube() {
		setMotorSpeed(-motorSpeed);
	}

	/**
	 * @param intakeSpeed is the speed to set the motor to
	 */
	public void setMotorSpeed(double intakeSpeed) {
		intakeMotorRight.updateWithSpeed(-intakeSpeed);
		intakeMotorLeft.updateWithSpeed(intakeSpeed);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		intakeMotorRight.destroy();
		intakeMotorLeft.destroy();
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.RobotSystem#stop()
	 */
	@Override
	public void stop() {
		intakeMotorRight.updateWithSpeed(0);
		intakeMotorLeft.updateWithSpeed(0);
	}

}