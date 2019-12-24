package robot.subsystems.turret;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.subsystems.turret.commands.TurnTurret;

import static robot.Ports.Turret.*;
import static robot.Constants.Turret.*;

//TODO: Add documentation
/**
 * @author Adam & Barel
 * @version 1.0
 * <p>
 *
 * {@using TalonSRX}
 * {@using Relative Encoder}
 */
public class Turret extends Subsystem {
    private TalonSRX master = new TalonSRX(MASTER);
    private double angle;
    private Direction direction;

    public Turret() {
        reset();
        direction = Direction.RIGHT;
        //TODO: configure controller
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new TurnTurret());
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
     * if you use the same Direction, use {@link #setTargetAngle(double)}.
     * the value can be between 0 to 150 degrees.
     *
     * @param targetAngle  the desired angle
     * @param newDirection the direction you want to be.
     * @throws IllegalArgumentException(String) if the angle is more than 150
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
     * reset the turret so it will be at the same position every run.
     */
    public void reset() {
        //TODO: Use hall-effect to reset the angle
    }

    public enum Direction {
        LEFT,
        RIGHT
    }
}