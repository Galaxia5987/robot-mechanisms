package robot.subsystems.turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.subsystems.turret.commands.TurnTurret;

import static robot.Ports.Turret.*;
import static robot.Constants.Turret.*;

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
    private double angle;
    private Direction direction;

    /**
     * configures the encoder and PID constants.
     */
    public Turret() {
        direction = Direction.RIGHT;
        master.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT_MS);
        master.config_kP(TALON_PID_SLOT, KP, TIMEOUT_MS);
        master.config_kI(TALON_PID_SLOT, KI, TIMEOUT_MS);
        master.config_kD(TALON_PID_SLOT, KD, TIMEOUT_MS);
        master.config_kF(TALON_PID_SLOT, KF, TIMEOUT_MS);
        master.setInverted(IS_MASTER_INVERTED);
        master.configPeakCurrentLimit(MAX_CURRENT);
        master.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        reset();
        master.setSelectedSensorPosition(0);
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new TurnTurret(45));
    }

    /**
     * set the direction of the Turret,
     * either left or right.
     *
     * @param direction the direction that the Turret will move.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
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
     * change the angle to the desired angle in the <b>same</b> direction.
     * the value can be between 0 to 150 degrees.
     * {@link #setTargetAngle(double, Direction)}
     *
     * @param targetAngle the desired angle
     */
    public void setTargetAngle(double targetAngle) {
        setTargetAngle(targetAngle, direction);
    }

    /**
     * change the angle to the desired angle,
     * if you would like to use the same Direction, use {@link #setTargetAngle(double)}.
     * the value can be between 0 to 150 degrees.
     *
     * @param targetAngle the desired angle.
     * @param newDirection the direction you want the Turret to turn.
     * @throws IllegalArgumentException(String) if the angle is more than 150 degrees.
     */
    public void setTargetAngle(double targetAngle, Direction newDirection) {
        if (targetAngle > LIMIT_PER_SIDE)
            throw new IllegalArgumentException("angle cannot be more than 150");
        if (newDirection != direction) {
            //TODO: check which side is the positive one
//            changeAngle(-(targetAngle + angle));
            this.direction = newDirection;
            this.angle = targetAngle;
            return;
        }
        if (Double.compare(targetAngle, angle) == 0) return;
        //TODO: check which side is the positive one
        changeAngle((angle > targetAngle) ? -targetAngle : targetAngle);
        this.angle = targetAngle;
    }

    /**
     * applying power to the controller for moving the turret.
     *
     * @param angle the desired angle
     */
    private void changeAngle(double angle) {
        master.set(ControlMode.MotionMagic, convertDegreesToTicks(angle));
    }

    private boolean getHallEffect(){
        return master.getSensorCollection().isRevLimitSwitchClosed();
    }

    public void adjustEncoderPosition(){
        if (getHallEffect()){
            master.setSelectedSensorPosition((int)convertDegreesToTicks(HALL_EFFECT_POSITION),0,TIMEOUT_MS);
        }
    }

    /**
     * reset the turret so it will be at the same position every run.
     */
     public void reset() {
        //TODO: Use hall-effect to reset the angle
    }

    /**
     * convert the angle to ticks so the controller will apply the right amount of power on the turret.
     *
     * @param degrees the degrees to convert.
     * @return the degrees converted to ticks.
     */
    private double convertDegreesToTicks(double degrees) {
        return degrees / TICKS_PER_ANGLE;
    }

    /**
     * convert the ticks from the controller to angle so human can understand.
     *
     * @param ticks the ticks to convert.
     * @return the ticks converted to ticks
     */
    private double convertTicksToDegrees(double ticks) {
        return ticks * TICKS_PER_ANGLE;
    }

    /**
     * the direction of the Turret, left and right
     */
    public enum Direction {
        LEFT,
        RIGHT
    }
}