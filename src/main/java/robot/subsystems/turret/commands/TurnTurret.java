package robot.subsystems.turret.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;
import robot.subsystems.turret.Turret.Direction;

import static robot.Robot.turret;

/**
 *
 */
public class TurnTurret extends Command {

    private Direction direction;
    private double angle;

    public TurnTurret(double angle) {
        this(angle, Direction.LEFT);
    }

    public TurnTurret(double angle, Direction direction) {
        requires(turret);
        this.angle = angle;
        this.direction = direction;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        turret.setTargetAngle(angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return turret.getAngle() == angle;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }
}