package robot.subsystems.wrist;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static robot.Constants.Wrist.*;
import static robot.Ports.Wrist.MOTOR;

public class Wrist extends Subsystem {
    private TalonSRX armMotor = new TalonSRX(MOTOR);

    public Wrist() {
        armMotor.config_kP(TALON_PID_SLOT, KP, TALON_TIMEOUT_MS);
        armMotor.config_kI(TALON_PID_SLOT, KI, TALON_TIMEOUT_MS);
        armMotor.config_kD(TALON_PID_SLOT, KD, TALON_TIMEOUT_MS);
    }

    /**
     * @return the current angle of the mechanism.
     */
    public double getAngle() {
        return convertTicksToDegrees(armMotor.getSelectedSensorPosition());
    }

    public void setAngle(double angle) {
        armMotor.set(ControlMode.MotionMagic, convertDegreesToTicks(angle), DemandType.ArbitraryFeedForward, getArbPercent());
    }

    /**
     * Update the angle of the mechanism to the desired angle.
     */
    public void update() {
    }

    /**
     * Reset the mechanism angle to 0.
     */
    public void reset() {
    }

    /**
     * Set the percent output of the motor.
     */
    public void setPercent() {
    }

    public double ConvertTicksToDegrees(double ticks) {
        return ticks / TICKS_PER_DEGREE;
    }

    public double ConvertDegreesToTicks(double angle) {
        return angle * TICKS_PER_DEGREE;
    }


    @Override
    protected void initDefaultCommand() {

    }
}
