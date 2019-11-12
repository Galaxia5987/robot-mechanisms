package robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

import static robot.Constants.Shooter.*;
import static robot.Ports.Shooter.*;

public class Shooter extends Subsystem {
    private TalonSRX shooterMotor = new TalonSRX(TALON);
    private VictorSP shooterSlave = new VictorSP(SLAVE);

    public Shooter() {
        shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, TIMEOUT_MS);
        shooterMaster.configSelectedFeedbackCoefficient(TICKS_PER_METER);
        shooterMotor.config_kD(TALON_PID_SLOT, KP, TIMEOUT_MS);
        shooterMotor.setInverted(IS_MASTER_INVERTED);
        shooterSlave.setInverted(IS_SLAVE_INVERTED);
    }

    public void setSpeed(){

    }

    public void getSpeed(){

    }

    @Override
    protected void initDefaultCommand() {

    }


}
