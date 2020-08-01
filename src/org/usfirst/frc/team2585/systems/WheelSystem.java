package org.usfirst.frc.team2585.systems;

import org.impact2585.lib2585.RampedSpeedController;
import org.usfirst.frc.team2585.robot.Environment;
import org.usfirst.frc.team2585.robot.RobotMap;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This system controls the drivetrain of the robot
 */
public class WheelSystem extends RobotSystem {
	private RampedSpeedController rightDrive;
	private RampedSpeedController leftDrive;

	private ADXRS450_Gyro gyro;
	private Ultrasonic ultra;
	
	private double targetAngle = 0.0;
	
	private final double DEADZONE = 0.2;
	
	private final double FORWARD_MULTIPLIER = 0.75;
	private final double ROTATION_RATE = 4.0;
	private final double DERIVATIVE_MULTIPLIER = 0.08; // originally 0.1
	private final double CORRECTION_MULTIPLIER = 0.020;
	private final double MAX_CORRECTION = 0.6;
	
	public boolean isUsingGyro = false;
	public boolean pastToggle = false;

	public static boolean IS_TEST_SYSTEM = false;
		
//	private PowerDistributionPanel pdp;
		
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		super.init(environ);

		rightDrive = new RampedSpeedController(new Spark(RobotMap.RIGHT_DRIVE_MOTOR));
		leftDrive = new RampedSpeedController(new Spark(RobotMap.LEFT_DRIVE_MOTOR));
		
		gyro = new ADXRS450_Gyro();
		targetAngle = getGyroAngle();
		
//		pdp = new PowerDistributionPanel();
		ultra = new Ultrasonic(1,1);
		ultra.setAutomaticMode(true);
	}
	
	/**
	 * Pass the user inputs to the drive train to run the motors the appropriate amounts
	 */
	public void run() {
		double forwardInput = input.forwardAmount();
		forwardInput = (Math.abs(forwardInput) > DEADZONE)? forwardInput * FORWARD_MULTIPLIER : 0;
		
		double rotationInput = input.rotationAmount();
		rotationInput = (Math.abs(rotationInput) > DEADZONE)? rotationInput : 0;
		
		if (input.shouldToggleGyro() != pastToggle) {
			isUsingGyro = !isUsingGyro;
		} 
		pastToggle = input.shouldToggleGyro();
		
		if (isUsingGyro) {
			driveWithGyro(forwardInput, rotationInput);
		} else {
			driveWithoutGyro(forwardInput, rotationInput);
		}
		
		
		if (!IS_TEST_SYSTEM) {
			SmartDashboard.putBoolean("USING GYRO", isUsingGyro);
			
//			SmartDashboard.putNumber("PDP: WHEELS, L1", pdp.getCurrent(RobotMap.PDP_LEFT_DRIVE_MOTOR_1));
//			SmartDashboard.putNumber("PDP: WHEELS, L2", pdp.getCurrent(RobotMap.PDP_LEFT_DRIVE_MOTOR_2));
//			
//			SmartDashboard.putNumber("PDP: WHEELS, R1", pdp.getCurrent(RobotMap.PDP_RIGHT_DRIVE_MOTOR_1));
//			SmartDashboard.putNumber("PDP: WHEELS, R2", pdp.getCurrent(RobotMap.PDP_RIGHT_DRIVE_MOTOR_2));
		}
	}
	
	/**
	 * @return the difference between the target angle and the current angle from the gyroscope
	 */
	public double getAngleError() {
		return targetAngle - getGyroAngle();
	}
	
	/**
	 * @return the amount of rotational correction that should be applied to rotate towards the target angle
	 */
	public double getCorrection() {
		double rate = getGyroRate() * DERIVATIVE_MULTIPLIER;
		double correction = (getAngleError() - rate) * CORRECTION_MULTIPLIER;
		
		// Min corrections
		if (Math.abs(correction) < 0.001) correction = 0;
		// Max correction
		if (Math.abs(correction) > MAX_CORRECTION) {
			correction = Math.copySign(MAX_CORRECTION, correction);
		}
		
		if (!IS_TEST_SYSTEM) {
			SmartDashboard.putNumber("Gyro Angle",  getGyroAngle());
			SmartDashboard.putNumber("Gyro Target Angle",  targetAngle);
			SmartDashboard.putNumber("Gyro Rate",   getGyroRate());
		}
		
		return correction;
	}
	
	/**
	 * Drive using the gyro to keep track of the angle
	 * @param forwardInput the amount to drive forward
	 * @param rotationInput the amount to adjust the targetAngle
	 */
	public void driveWithGyro(double forwardInput, double rotationInput) {		
		// Adjust Target Angle
		targetAngle += rotationInput * ROTATION_RATE;
		
		arcadeDrive(forwardInput, getCorrection());
	}
	
	/**
	 * Drive the robot without using input from the gyro
	 * @param forwardInput the amount to drive forward. -1 is backwards, 1 is forwards
	 * @param rotationInput the amount to rotate. -1 is clockwise, 1 is counter-clockwise
	 */
	public void driveWithoutGyro(double forwardInput, double rotationInput) {
		resetGyro();
		arcadeDrive(forwardInput, rotationInput);
	}
	
	/**
	 * Rotate to a certain targetAngle and get the current error
	 * @param newTargetAngle the target angle to rotate towards
	 * @return the current difference between the target angle and current angle
	 */
	public double rotateToAngle(double newTargetAngle){
		targetAngle = newTargetAngle;
		//targetAngle = newTargetAngle;
		
		double correction = getCorrection();
		
		arcadeDrive(0.0, correction);
		
		return getAngleError();
	}
	
	public void setTargetAngle(double newTargetAngle) {
		targetAngle = newTargetAngle;
	}
	
	/**
	 * @param leftSpeed the speed to set the left side to
	 * @param rightSpeed the speed to set the right side to
	 */
	protected void setSideSpeeds(double leftSpeed, double rightSpeed) {
		if (Math.abs(leftSpeed) > 1) leftSpeed = Math.copySign(1, leftSpeed);
		if (Math.abs(rightSpeed) > 1) rightSpeed = Math.copySign(1, rightSpeed);
		
		// Both sides driving in same direction
		// If wiring doesn't explicitly compensate for different sides, must make rightSpeed negative		
		leftDrive.updateWithSpeed(leftSpeed);
		rightDrive.updateWithSpeed(-rightSpeed);
		
		if (!IS_TEST_SYSTEM) {
			SmartDashboard.putNumber("LEFT SPEED RAW", leftSpeed);
			SmartDashboard.putNumber("RIGHT SPEED RAW", rightSpeed);
			
			SmartDashboard.putBoolean("SPEEDS ARE EQUAl", leftSpeed == rightSpeed);
			SmartDashboard.putBoolean("SPEEDS ARE OPPOSITE", leftSpeed == -rightSpeed);
		}
	}
	
	/**
	 * Drive the robot with rotation. Positive rotation is counter-clockwise
	 * @param forward the amount to drive forward
	 * @param rotation the amount to rotate. Positive rotates counter-clockwise
	 */
	protected void arcadeDrive(double forward, double rotation) {
		double rightSpeed = forward + rotation;
		double leftSpeed = forward - rotation;

		setSideSpeeds(leftSpeed, rightSpeed);
	}
	
	/**
	 * @return the current angle that the robot is facing from the gyroscope
	 */
	protected double getGyroAngle() {
		return -gyro.getAngle(); // negated to make positive counter-clockwise
	}
	
	/**
	 * @return the rate that the robot is rotating from the gyroscope
	 */
	protected double getGyroRate() {
		return -gyro.getRate(); // negated to make positive counter-clockwise
	}
	
	/**
	 * Reset the angle of the gyroscope
	 */
	public void resetGyro() {
		if (!IS_TEST_SYSTEM) {
			gyro.reset();
		}
		
		targetAngle = getGyroAngle();
	}
	
	/**
	 * Get range from ultrasonic
	 */
	public double getRange() {
		return ultra.getRangeMM();
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.RobotSystem#stop()
	 */
	@Override
	public void stop() {
		setSideSpeeds(0.0, 0.0);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		stop();
		leftDrive.destroy();
		rightDrive.destroy();
	}
}
