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
        shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TALON_TIMEOUT);
//        shooterMaster.configSelectedFeedbackCoefficient(TICKS_PER_METER / TICKS_PER_METER); // TODO: Revert
        shooterSlave.follow(shooterMaster);
        shooterMaster.config_kP(TALON_PID_SLOT, KP, TALON_TIMEOUT);
        shooterMaster.config_kI(TALON_PID_SLOT, KI, TALON_TIMEOUT);
        shooterMaster.config_kD(TALON_PID_SLOT, KD, TALON_TIMEOUT);
        shooterMaster.config_kF(TALON_PID_SLOT, KF, TALON_TIMEOUT);
        shooterMaster.setInverted(IS_MASTER_INVERTED);
        shooterMaster.setSensorPhase(MASTER_SENSOR_PHASED);
        shooterMaster.configClosedloopRamp(RAMP_RATE);
        shooterMaster.configVoltageCompSaturation(12);
        shooterMaster.enableVoltageCompensation(true);
        shooterSlave.setInverted(IS_SLAVE_INVERTED);
        shooterSlave.setSensorPhase(SLAVE_SENSOR_PHASED);
        shooterSlave.configVoltageCompSaturation(12);
        shooterSlave.enableVoltageCompensation(true);
//        shooterMaster.configPeakCurrentLimit(MAX_CURRENT);
        shooterMaster.setSelectedSensorPosition(0);
        // TODO: Configure peak and nominal outputs, if needed
    }

    public double getSpeed() {
        return ticksToRPM(shooterMaster.getSelectedSensorVelocity());
    }

    /**
     * @param rpm
     */
    public void setSpeedRPM(double rpm) {
        shooterMaster.set(ControlMode.Velocity, ticksToRPM(rpm));
    }

    public void setInputSpeed(double inputSpeed){
        inputMotor.set(ControlMode.PercentOutput, inputSpeed);
    }

    public double getPosition() {
        return shooterMaster.getSelectedSensorPosition();
    }

    public double ticksToRPM(double ticks) {
        return (ticks * TICKS_PER_ROTATION * 10) / 60;
    }

    public double rpmToTicks(double rpm) {
        return TICKS_PER_ROTATION * 10 * (rpm / 60);
    }

    @Override
    protected void initDefaultCommand() {

    }


}
