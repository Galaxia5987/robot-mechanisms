package robot.subsystems.turret.commands;

import edu.wpi.first.wpilibj.command.Command;

import static robot.Constants.Turret.ANGLE_THRESHOLD;
import static robot.Robot.turret;

/**
 *
 */
public class TurnTurret extends Command {

    private double angle;

    public TurnTurret(double angle) {
        requires(turret);
        this.angle = angle;
    }


    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
            turret.setTargetAngle(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Math.abs(turret.getAngle() - angle) <= ANGLE_THRESHOLD;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }
}