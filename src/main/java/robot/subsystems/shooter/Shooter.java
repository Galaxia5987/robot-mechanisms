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
        shooterMotor.config_kP(TALON_PID_SLOT, KP, TIMEOUT_MS);
        shooterMotor.config_kI(TALON_PID_SLOT, KP, TIMEOUT_MS);
        shooterMotor.config_kD(TALON_PID_SLOT, KP, TIMEOUT_MS);
    }

    public void setSpeed(){

    }

    public void getSpeed(){

    }

    @Override
    protected void initDefaultCommand() {

    }


}
