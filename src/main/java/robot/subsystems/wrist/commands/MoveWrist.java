package robot.subsystems.wrist.commands;

import edu.wpi.first.wpilibj.command.Command;

import static robot.Constants.Wrist.*;
import static robot.Robot.wrist;

/**
 *
 */
public class MoveWrist extends Command {
    private double angle;

    public MoveWrist(double angle) {
        requires(wrist);
        this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        wrist.setAngle(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(angle - wrist.getAngle()) < THERSHOLD;
    }

    // Called once after isFinished returns true
    protected void end() {
    }
}