package robot.subsystems.turret.commands;

import com.stormbots.MiniPID;
import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.turret.ControlMode;

import static robot.Constants.VisionTurret.VISION_ANGLE_THRESHOLD;
import static robot.Constants.VisionTurret.*;
import static robot.Robot.turret;

public class TurnTurretVisionPID extends Command {
    private MiniPID anglePid = new MiniPID(VISION_KP, VISION_KI, VISION_KD);

    public TurnTurretVisionPID() {
        requires(turret);
    }

    @Override
    protected void initialize() {
        turret.setControlMode(ControlMode.SPEED_CONTROL);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        anglePid.setPID(VISION_KP, VISION_KI, VISION_KD);
        turret.setSpeed(anglePid.getOutput(turret.getVisionAngle(), 0));
    }

    @Override
    protected void end() {
        turret.setTargetAngle(turret.getAngle());
        turret.setControlMode(ControlMode.ANGLE_CONTROL);
        turret.setSpeed(0);
    }

    @Override
    protected boolean isFinished() {
//        return Math.abs(turret.getVisionAngle()) <= VISION_ANGLE_THRESHOLD;
        return false;
    }
}
