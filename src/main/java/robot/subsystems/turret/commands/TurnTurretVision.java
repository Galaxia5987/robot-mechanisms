package robot.subsystems.turret.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.turret.ControlMode;

import static robot.Constants.Turret.ANGLE_THRESHOLD;
import static robot.Robot.turret;

/**
 *
 */
public class TurnTurretVision extends Command {

    private double angle;

    public TurnTurretVision() {
        requires(turret);
    }


    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        turret.setControlMode(ControlMode.ANGLE_CONTROL);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        this.angle = turret.getAngle() + turret.getVisionAngle();
        turret.setTargetAngle(this.angle);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Math.abs(turret.getAngle() - angle) <= ANGLE_THRESHOLD;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        //turret.stop();
    }
}