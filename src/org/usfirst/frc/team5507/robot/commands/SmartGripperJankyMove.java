package org.usfirst.frc.team5507.robot.commands;

import org.usfirst.frc.team5507.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SmartGripperJankyMove extends Command {

	private int state;

	public double angleToTicks(double angle) {
		return (4096 * angle) / 360;
	}

	public SmartGripperJankyMove() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.m_smartGripper);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		state = Robot.m_smartGripper.getToggledState();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(state == 1)
		{
			Robot.m_smartGripper.setDesiredAngleForward(170);
		}
		if(state == 2)
		{
			Robot.m_smartGripper.setDesiredAngleForward(182);
		}
		if(state == 3) 
		{
			Robot.m_smartGripper.setDesiredAngleBackward(170);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
