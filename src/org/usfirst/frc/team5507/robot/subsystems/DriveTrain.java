package org.usfirst.frc.team5507.robot.subsystems;

import org.usfirst.frc.team5507.robot.Constants;
import org.usfirst.frc.team5507.robot.RobotMap;
import org.usfirst.frc.team5507.robot.commands.DriveWithJoystick;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private WPI_TalonSRX frontLeft = new WPI_TalonSRX(RobotMap.driveFrontLeft);
	private WPI_TalonSRX frontRight = new WPI_TalonSRX(RobotMap.driveFrontRight);
	private WPI_TalonSRX backLeft = new WPI_TalonSRX(RobotMap.driveBackLeft);
	private WPI_TalonSRX backRight = new WPI_TalonSRX(RobotMap.driveBackRight);
	//ADD ENCODERS
	
	private MecanumDrive m_drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

	public DriveTrain() {
		configTalon(frontLeft);
		//configTalon(frontRight);
		//configTalon(backRight);
		//configTalon(backLeft);
	}
	
	 // config a talon motor controller with an Encoder
	public void configTalon(WPI_TalonSRX talon) {
		talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
		talon.setSensorPhase(true);
		talon.setInverted(false);
		
		/* Set relevant frame periods to be at least as fast as periodic rate*/
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
		talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

		/* set the peak and nominal outputs */
		talon.configNominalOutputForward(0, Constants.kTimeoutMs);
		talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
		talon.configPeakOutputForward(1, Constants.kTimeoutMs);
		talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);
		
		/* set closed loop gains in slot0 - see documentation */
		talon.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
		talon.config_kF(0, 0.2, Constants.kTimeoutMs);
		talon.config_kP(0, 0.2, Constants.kTimeoutMs);
		talon.config_kI(0, 0, Constants.kTimeoutMs);
		talon.config_kD(0, 0, Constants.kTimeoutMs);
		/* set acceleration and vcruise velocity - see documentation */
		talon.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
		talon.configMotionAcceleration(6000, Constants.kTimeoutMs);
		/* zero the sensor */
		talon.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveWithJoystick());
   	
    }
    
    public void drive(double ySpeed, double xSpeed, double zRotation)
    {
    	m_drive.driveCartesian(ySpeed, xSpeed, zRotation);
    	SmartDashboard.putNumber("frontleftpos", frontLeft.getSelectedSensorPosition(0));
    	SmartDashboard.putNumber("frontleftspd", frontLeft.getSelectedSensorVelocity(0));
    }
    
    public void drive(Joystick stick)
    {
    	drive(-stick.getRawAxis(0), stick.getRawAxis(1), stick.getRawAxis(3) - stick.getRawAxis(2));
 
    }
}
