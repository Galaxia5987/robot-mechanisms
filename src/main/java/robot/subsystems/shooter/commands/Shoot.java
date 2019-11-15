package robot.subsystems.shooter.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import static robot.Robot.shooter;

/**
 *
 */
public class Shoot extends Command {
    private double distance;
    private double timeout;
    private Timer timer = new Timer();

    public Shoot(double distance, double timeout) {
        requires(shooter);
        this.distance = distance;
        this.timeout = timeout;
    }

    public Shoot(double distance) {
        this(distance, 0);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        timer.reset();
        timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        shooter.setSpeed();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return timer.get() >= timeout;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        timer.stop();
    }

    // Called when another command which requires one or more of the same
// subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}