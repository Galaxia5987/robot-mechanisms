package robot.subsystems.turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.Robot;

import static robot.Constants.Turret.*;
import static robot.Constants.VisionTurret.*;
import static robot.Ports.Turret.*;

/**
 * @author Adam & Barel
 * @version 1.0
 * <p>
 * this class includes methods for the Turret.
 * {@using TalonSRX}
 * {@using Relative Encoder}
 * {@using Hall Effect}
 */
public class Turret extends Subsystem {
    private TalonSRX master = new TalonSRX(MASTER);
    private NetworkTableEntry visionAngle = Robot.visionTable.getEntry("angle");
    private double targetAngle = 180;
    private double speed = 0;
    private robot.subsystems.turret.ControlMode controlMode;

    /**
     * configures the encoder and PID constants.
     */
    public Turret() {
        master.configFactoryDefault();
        master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
        master.config_kP(TALON_PID_SLOT, KP, TALON_TIMEOUT);
        master.config_kI(TALON_PID_SLOT, KI, TALON_TIMEOUT);
        master.config_kD(TALON_PID_SLOT, KD, TALON_TIMEOUT);
        master.config_kF(TALON_PID_SLOT, KF, TALON_TIMEOUT);
        master.configMotionCruiseVelocity(CRUISE_VELOCITY);
        master.configMotionAcceleration(CRUISE_ACCELERATION);
        master.setInverted(IS_MASTER_INVERTED);
        master.setSensorPhase(IS_SENSOR_PHASED);
        master.configPeakCurrentLimit(MAX_CURRENT);
//        master.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyClosed);
        master.setSelectedSensorPosition((int) HALL_EFFECT_POSITION, 0, TALON_TIMEOUT);
    }

    @Override
    public void initDefaultCommand() {

    }

    private double getConstant(String key, double value) {
        SmartDashboard.putNumber(key, SmartDashboard.getNumber(key, value));
        return SmartDashboard.getNumber(key, value);
    }

    public void updateConstants() {
        if (KP != getConstant("kp", KP) || KI != getConstant("kI", KI) || getConstant("kD", KD) != KD || getConstant("kF", KF) != KF) {
            KP = getConstant("kp", KP);
            KI = getConstant("kI", KI);
            KD = getConstant("kD", KD);
            KF = getConstant("kF", KF);
            master.config_kP(TALON_PID_SLOT, KP, TALON_TIMEOUT);
            master.config_kI(TALON_PID_SLOT, KI, TALON_TIMEOUT);
            master.config_kD(TALON_PID_SLOT, KD, TALON_TIMEOUT);
            master.config_kF(TALON_PID_SLOT, KF, TALON_TIMEOUT);
        }
    }

    public void updateVisionConstants() {
        if (VISION_KP != getConstant("visionKP", VISION_KP) || VISION_KI != getConstant("visionKI", VISION_KI) || VISION_KD != getConstant("visionKD", VISION_KD))
        {
            VISION_KP = getConstant("visionKP", VISION_KP);
            VISION_KI = getConstant("visionKI", VISION_KI);
            VISION_KD = getConstant("visionKD", VISION_KD);
        }
    }

    @Override
    public void periodic() {
        System.out.println("the current angle is " + getAngle());
//        if (getHallEffect())
//            adjustEncoderPosition();
//        updateConstants();
        SmartDashboard.putNumber("ANGLE", getAngle());
        updateVisionConstants();
        if(controlMode == robot.subsystems.turret.ControlMode.ANGLE_CONTROL) {
            moveTurret(targetAngle);
        }
        else if(controlMode == robot.subsystems.turret.ControlMode.SPEED_CONTROL) {
            updateTurretSpeed();
        }
    }

    /**
     * get the current angle from the controller
     *
     * @return the angle of the turret
     */
    public double getAngle() {
        return convertTicksToDegrees(master.getSelectedSensorPosition());
    }

    /**
     *
     */
    public double getVisionAngle(){
        return visionAngle.getDouble(0);
    }

    /**
     * change the angle to the desired angle,
     * if you would like to use the same Direction.
     * the value can be between 0 to 360 degrees.
     *
     * @param newTargetAngle the desired angle.
     */
    public void setTargetAngle(double newTargetAngle) {
        newTargetAngle = (newTargetAngle + 720) % 360; //To insure that the targetAngle is between 0-360, we add 720 to prevent negative modulo operations.
        this.targetAngle = constrain(MINIMUM_ANGLE, newTargetAngle, MAXIMUM_ANGLE);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * applying power to the controller for moving the turret.
     *
     * @param angle the desired angle
     */
    private void moveTurret(double angle) {
        master.set(ControlMode.MotionMagic, convertDegreesToTicks(angle));
    }

    public void updateTurretSpeed() {
        master.set(ControlMode.PercentOutput, this.speed);
    }

    public void stop() {
        master.set(ControlMode.PercentOutput, 0);
    }

    /**
     * @return return if the state of the Hall Effect sensor is Closed.
     */
    public boolean getHallEffect() {
        return !master.getSensorCollection().isRevLimitSwitchClosed();
    }

    /**
     * set encoder position to the Hall Effect position.
     */
    public void adjustEncoderPosition() {
        master.setSelectedSensorPosition(convertDegreesToTicks(HALL_EFFECT_POSITION), 0, TALON_TIMEOUT);
    }

    private double constrain(double minimum, double angle, double maximum) {
        return Math.min(maximum, Math.max(minimum, angle));
    }

    /**
     * convert the angle to ticks so the controller will apply the right amount of power on the turret.
     *
     * @param degrees the degrees to convert.
     * @return the degrees converted to ticks.
     */
    private int convertDegreesToTicks(double degrees) {
        return (int) (degrees * TICKS_PER_DEGREE);
    }

    /**
     * convert the ticks from the controller to angle so human can understand.
     *
     * @param ticks the ticks to convert.
     * @return the ticks converted to ticks
     */
    private double convertTicksToDegrees(int ticks) {
        return ticks / TICKS_PER_DEGREE;
    }

    public void reset() {
        master.setSelectedSensorPosition(0);
    }

    public void setControlMode(robot.subsystems.turret.ControlMode controlMode) {
        this.controlMode = controlMode;
    }
}