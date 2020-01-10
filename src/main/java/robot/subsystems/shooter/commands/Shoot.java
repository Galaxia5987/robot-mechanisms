package robot.subsystems.shooter.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static robot.Robot.shooter;
import static robot.Robot.shooterTable;
import static robot.Constants.Shooter.*;

/**
 *
 */
public class Shoot extends Command {
    private double distance;
    private double timeout;
    private NetworkTableEntry velocityEntry = shooterTable.getEntry("velocity");
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
        // shooter.setSpeed((calculateInitialVelocity(distance) / RADIUS) * TICKS_PER_METER); // Convert from linear velocity to radial velocity: v = wr.
        SmartDashboard.putNumber("shooterRPM", SmartDashboard.getNumber("shooterRPM", TARGET_RPM));
        shooter.setSpeedRPM(SmartDashboard.getNumber("shooterRPM", TARGET_RPM));
        System.out.println(shooter.getSpeed() + ", calculated speed " + calculateVelocity(distance));
        setNetworkTable();
        if (100 - ((shooter.getSpeed() / TARGET_RPM) * 100) <= PERCENT_THRESHOLD){
            shooter.setInputSpeed(0.5);
        }
        else {
            shooter.setInputSpeed(0);
        }
//        System.out.println(shooter.getPosition());
    }

    private void setNetworkTable() {
        velocityEntry.setDouble(shooter.getSpeed());
    }

    private double calculateVelocity(double distance) {
        return (458.22 * Math.exp(0.2559 * distance)) * (4.0 / 5);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return timeout > 0 && timer.get() >= timeout;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        timer.stop();
        shooter.setSpeedRPM(0);
        shooter.setInputSpeed(0);
    }

    // Called when another command which requires one or more of the same
// subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
