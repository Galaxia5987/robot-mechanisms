package robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static robot.Constants.Shooter.*;
import static robot.Ports.Shooter.*;

public class Shooter extends Subsystem {
    private TalonSRX shooterMaster = new TalonSRX(TALON);
    private VictorSPX shooterSlave = new VictorSPX(SLAVE);

    public Shooter() {
        shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT_MS);
        shooterMaster.configSelectedFeedbackCoefficient(TICKS_PER_METER);
        shooterSlave.follow(shooterMaster);
        shooterMaster.config_kP(TALON_PID_SLOT, KP, TIMEOUT_MS);
        shooterMaster.config_kI(TALON_PID_SLOT, KI, TIMEOUT_MS);
        shooterMaster.config_kD(TALON_PID_SLOT, KD, TIMEOUT_MS);
        shooterMaster.config_kF(TALON_PID_SLOT, KF, TIMEOUT_MS);
        shooterMaster.setInverted(IS_MASTER_INVERTED);
        shooterSlave.setInverted(IS_SLAVE_INVERTED);
        shooterMaster.configPeakCurrentLimit(MAX_CURRENT);
        // TODO: Configure peak and nominal outputs, if needed
    }

    /**
     * Set how far the bin/target is from the robot; The vertical distance the ball should travel
     * @param distance in meters
     */
    public void setShootingDistance(double distance) {

    }

    public double getSpeed() {
        return shooterMaster.getSelectedSensorVelocity();
    }

    /**
     *
     * @param speed
     */
    private void setSpeed(double speed) {
        shooterMaster.set(ControlMode.Velocity, speed); // TODO: Convert between m/s to native sensor units/100ms
    }

    @Override
    protected void initDefaultCommand() {

    }


}
