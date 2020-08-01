package org.usfirst.frc.team2585.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * A command to be passed to the autonomous executor
 */
public abstract class AutonomousCommand {
	int location = 0;
	String gameData = "";
	
	/**
	 * @param timeElapsed time passed since the last task was completed
	 * @return whether time should be reset
	 */
	abstract boolean execute(long timeElapsed);
	
	/**
	 * Update the game data from the driver station 
	 */
	public void updateGameData() {
		// Location of the driverStation: 1, 2, 3; L, M, R
		// ==================================================
		// Edit here to change location
		location = 2;//DriverStation.getInstance().getLocation(); 
		// ==================================================
		
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
	
		SmartDashboard.putNumber("GAME DATA: LOCATION", location);
		SmartDashboard.putString("GAME DATA: Game Data", gameData);

	}
}
