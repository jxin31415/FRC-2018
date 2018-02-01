package org.usfirst.frc.team2585.robot;

import org.usfirst.frc.team2585.systems.IntakeSystem;
import org.usfirst.frc.team2585.systems.WheelSystem;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * A container class for the different commands that can be passed to the autonomous executor
 */
public class Commands {
	
	private static Environment environ;
	private static WheelSystem drivetrain;
	private static IntakeSystem intake;
	
	private String gameData;
	private int location;
	
	/**
	 * Constructor that sets the environment and the required systems
	 * @param e the environment of the robot
	 */
	public Commands(Environment env) {
		environ = env;

		drivetrain = (WheelSystem) environ.getSystem(Environment.WHEEL_SYSTEM);
		
		// Location of the driverStation: 1, 2, 3; L, M, R
		location = DriverStation.getInstance().getLocation(); 
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	}
	
	/**
	 * Move the robot forward with no rotation
	 */
	private static void driveForward() {
		drivetrain.driveWithGyro(0.25, 0.0);
	}
	
	/**
	 * Turn the robot left with no forward movement
	 */
	private static double turnLeft(double rotation) {
		return drivetrain.rotateToAngle(-rotation);
	}
	
	/**
	 * Turn the robot right with no forward movement
	 */
	private static double turnRight(double rotation) {
		return drivetrain.rotateToAngle(rotation);
	}
	
	/**
	 * Set the forward movement and rotation to 0
	 */
	private static void stop() {
		drivetrain.driveWithGyro(0, 0);
	}
	
	/**
	 * Use the intake system to deposit a cube
	 */
	private static void depositCube() {
		intake.depositCube();
	}
	
	
	/**
	 * Autonomous command that drives according to its position and the side of its switch
	 */
	public class Main implements AutonomousCommand {
		private int timeToDriveStraight = 2000;
		private int timeToDepositCube = 2000;
		private int timeInToSwitchFromSide = 200;
		
		private int tasksComplete = 0;
		private boolean shouldResetTime = false;
		
		boolean onLeftWithSwitch = gameData.charAt(0) == 'L' && location == 1;
		boolean onRightWithSwitch = gameData.charAt(0) == 'R' && location == 3;
		
		/**
		 * Mark that a task has been complete
		 */
		private void markTaskComplete() {
			tasksComplete++;
			shouldResetTime = true;
		}
		
		/**
		 * Run straight the distance to the auton line
		 * @param timeElapsed the time elapsed since the last task was completed
		 */
		private void runStraight(long timeElapsed) {
			if (timeElapsed < timeToDriveStraight) {
				driveForward();
			} else {
				stop();
			}
		}
		
		/**
		 * Run the auton starting from the middle driverstation
		 * @param timeElapsed the time elapsed since the last task was completed
		 */
		private void runFromMiddle(long timeElapsed){
			int delayTime = 2000;
			int timeToSwitch = 2000;
			int moveLeftTime = 1000;
			int moveRightTime = 1100;
			int secondForwardTime = 1000;
			
			if(gameData.length() < 1){
				runStraight(timeElapsed);
			} else {
				switch (tasksComplete) {
				case 0: // DELAY
					if (timeElapsed > delayTime) {
						markTaskComplete();
					} 	
					break;
					
				case 1: // MOVE FORWARD 
					if (timeElapsed < timeToSwitch/2) {
						driveForward();
					} else {
						markTaskComplete();
					}
					break;
					
				case 2: // ROTATE 
					if (gameData.charAt(0) == 'L') { // Switch on left side
						
					}
					break;
				default:
					stop();
					break;
				}
			}				
		}

		/**
		 * Run the auton from the left or right driverstation
		 * @param timeElapsed the time since the last task was completed
		 */
		private void runFromSide(long timeElapsed){
			if (onLeftWithSwitch || onRightWithSwitch) {
				switch(tasksComplete) {
				case 0: // MOVE FORWARD
					if(timeElapsed < timeToDriveStraight){
						driveForward();
					} else {
						markTaskComplete();
					}
					break;
				case 1: // TURN INWARDS
					if (onLeftWithSwitch) {
						if(Math.abs(turnRight(90.0)) < 0.5){
							markTaskComplete();
						}
					} else if (onRightWithSwitch) {
						if(Math.abs(turnLeft(90.0)) < 0.5){
							markTaskComplete();
						}
					}
					break;
				case 2: // MOVE INWARDS TOWARDS SWITCH
					if(timeElapsed < timeInToSwitchFromSide){
						driveForward();
					} else {
						markTaskComplete();
					}
					break;
				case 3: // DEPOSIT CUBE
					if(timeElapsed < timeToDepositCube) {
						depositCube();
					} else {
						markTaskComplete();
					}
				default:
					stop();
					break;
				}
			} else { // Switch is on the other side
				runStraight(timeElapsed);
			}
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(long)
		 */
		@Override
		public boolean execute(long timeElapsed) {
			shouldResetTime = false;
			if (location == 1 || location == 3) {
				runFromSide(timeElapsed);
			} else if (location == 2) {
				runFromMiddle(timeElapsed);
			} else {
				runStraight(timeElapsed);
			}
			return shouldResetTime;
		}
	}
	
	/**
	 * Autonomous command that drives straight no matter what
	 */
	public class Straight implements AutonomousCommand {
		private static final int timeToDriveStraight = 2000;
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(long)
		 */
		@Override
		public boolean execute(long timeElapsed) {
			if (timeElapsed < timeToDriveStraight) {
				driveForward();
			} else {
				stop();
			}
			return false;
		}
	}
	
	/**
	 * Autonomous command that does nothing
	 */
	public class None implements AutonomousCommand {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.robot.AutonomousCommand#execute(long)
		 */
		@Override
		public boolean execute(long timeElapsed) {
			stop();
			return false;
		}
	}
}
