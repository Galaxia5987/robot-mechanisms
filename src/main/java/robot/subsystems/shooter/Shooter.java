package robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static robot.Constants.Shooter.*;
import static robot.Ports.Shooter.*;

public class Shooter extends Subsystem {
    private TalonSRX shooterMaster = new TalonSRX(MASTER);
    private VictorSPX shooterSlave = new VictorSPX(SLAVE);
    private VictorSPX inputMotor = new VictorSPX(INPUT_MOTOR);


    public Shooter() {
        shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT_MS);
        shooterMaster.configSelectedFeedbackCoefficient(TICKS_PER_METER / TICKS_PER_METER); // TODO: Revert
        shooterSlave.follow(shooterMaster);
        shooterMaster.config_kP(TALON_PID_SLOT, KP, TIMEOUT_MS);
        shooterMaster.config_kI(TALON_PID_SLOT, KI, TIMEOUT_MS);
        shooterMaster.config_kD(TALON_PID_SLOT, KD, TIMEOUT_MS);
        shooterMaster.config_kF(TALON_PID_SLOT, KF, TIMEOUT_MS);
        shooterMaster.setInverted(IS_MASTER_INVERTED);
        shooterSlave.setInverted(IS_SLAVE_INVERTED);
        shooterMaster.configPeakCurrentLimit(MAX_CURRENT);
        shooterMaster.setSelectedSensorPosition(0);
        // TODO: Configure peak and nominal outputs, if needed
    }

    public double getSpeed() {
        return shooterMaster.getSelectedSensorVelocity();
    }

    /**
     * @param speed
     */
    public void setSpeed(double speed) {
        shooterMaster.set(ControlMode.Velocity, speed); // TODO: Convert between m/s to native sensor units/100ms
    }

    public void setInputSpeed(double inputSpeed){
        inputMotor.set(ControlMode.Velocity, inputSpeed);
    }

    public double getPosition() {
        return shooterMaster.getSelectedSensorPosition();
    }


    @Override
    protected void initDefaultCommand() {

    }


}
