package robot.subsystems.turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.subsystems.turret.commands.TurnTurret;

import static robot.Constants.Turret.*;
import static robot.Ports.Turret.MASTER;

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

    /**
     * configures the encoder and PID constants.
     */
    public Turret() {
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
     * change the angle to the desired angle,
     * if you would like to use the same Direction.
     * the value can be between -180 to 180 degrees.
     *
     * @param targetAngle the desired angle.
     */
    public void setTargetAngle(double targetAngle) {
        targetAngle = (targetAngle + 720) % 360; //To insure that the targetAngle is between 0-360, we add 720 to prevent negative modulo operations.
        targetAngle = constrain(MAXIMUM_ANGLE, targetAngle, MINIMUM_ANGLE);
        moveTurret(targetAngle);
    }

    /**
     * applying power to the controller for moving the turret.
     *
     * @param angle the desired angle
     */
    private void moveTurret(double angle) {
        master.set(ControlMode.MotionMagic, convertDegreesToTicks(angle));
    }

    /**
     * @return return if the state of the the Hall Effect sensor is Closed.
     */
    private boolean getHallEffect() {
        return master.getSensorCollection().isRevLimitSwitchClosed();
    }

    /**
     * set encoder position to the Hall Effect position.
     */
    public void adjustEncoderPosition() {
        if (getHallEffect()) {
            master.setSelectedSensorPosition((int) convertDegreesToTicks(HALL_EFFECT_POSITION), 0, TIMEOUT_MS);
        }
    }

    /**
     * reset the turret so it will be at the same position every run.
     */
    public void reset() {
        if (!getHallEffect()) {
            moveTurret(HALL_EFFECT_POSITION);
        }
    }

    private double constrain(double maximum, double angle, double minimum) { //TODO: flip min and max
        return Math.min(maximum, Math.max(minimum, angle));
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
}