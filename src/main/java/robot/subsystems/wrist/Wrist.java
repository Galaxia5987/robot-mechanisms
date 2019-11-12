package robot.subsystems.wrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static robot.Constants.Wrist.*;
import static robot.Ports.Wrist.MOTOR;

/**
 * Wrist subsystem, currently built for mechanics prototype
 */
public class Wrist extends Subsystem {
    private TalonSRX armMotor = new TalonSRX(MOTOR);
    private double angle;

    public Wrist() {
        armMotor.config_kP(TALON_PID_SLOT, KP, TALON_TIMEOUT_MS);
        armMotor.config_kI(TALON_PID_SLOT, KI, TALON_TIMEOUT_MS);
        armMotor.config_kD(TALON_PID_SLOT, KD, TALON_TIMEOUT_MS);
    }

    /**
     * @return the encoder angle of the wrist in degrees, clockwise.
     */
    public double getAngle() {
        return convertTicksToDegrees(armMotor.getSelectedSensorPosition());
    }

    /**
     * Set the target angle of the wrist
     *
     * @param angle angle in degrees, clockwise.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Update the talon to power the motor based on the target angle and current angle
     */
    public void update() {
        armMotor.set(ControlMode.MotionMagic, convertDegreesToTicks(angle), DemandType.ArbitraryFeedForward, getArbitraryPercent(angle));
    }

    /**
     * Reset the angle of the wrist, in case it jumps or becomes inaccurate.
     */
    public void reset() {
        setAngle(0);
        update();
    }

    /**
     * Set the percent output of the motor, without control.
     */
    public void setPercent(double angle) {
        armMotor.set(ControlMode.PercentOutput, convertDegreesToTicks(angle));
    }

    /**
     * The arbitrary percent output needed to hold the wrist in place
     *
     * @param angle
     * @return
     */
    private double getArbitraryPercent(double angle){return 0;}

    public double convertTicksToDegrees(double ticks) {
        return ticks / TICKS_PER_DEGREE;
    }

    public double convertDegreesToTicks(double angle) {
        return angle * TICKS_PER_DEGREE;
    }


    @Override
    protected void initDefaultCommand() {

    }
}
