package org.usfirst.frc.team2585.robot;

/**
 * This interface contains the mappings of the output pins
 */
public interface RobotMap {
	public static final int LEFT_DRIVE_MOTOR = 1;
	public static final int RIGHT_DRIVE_MOTOR = 0;
	
	public static final int CUBE_LIFT_MOTOR = 2;
	
	public static final int INTAKE_MOTOR_LEFT = 3;
	public static final int INTAKE_MOTOR_RIGHT = 4;
	
	public static final int HOOK_EXTENDER_MOTOR = 5;
	
	public static final int CLIMB_MOTOR_LEFT = 6;
	public static final int CLIMB_MOTOR_RIGHT = 7;

	// SENSORS
	public static final int LIMIT_SWITCH_TOP = 0;
	public static final int LIMIT_SWITCH_BOTTOM = 1;

	// PDP
	public static final int PDP_RIGHT_DRIVE_MOTOR_1 = 14;
	public static final int PDP_RIGHT_DRIVE_MOTOR_2 = 15;
	
	public static final int PDP_LEFT_DRIVE_MOTOR_1 = 12;
	public static final int PDP_LEFT_DRIVE_MOTOR_2 = 13;
}
