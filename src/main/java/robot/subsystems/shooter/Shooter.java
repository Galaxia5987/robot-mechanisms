package robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

import static robot.Ports.Shooter.*;

public class Shooter extends Subsystem {
    private TalonSRX shooterMotor = new TalonSRX(TALON);
    private VictorSP shooterSlave = new VictorSP(SLAVE);

    public Shooter() {
        shooterSlave.follow(shooterMotor);
    }

    @Override
    protected void initDefaultCommand() {

    }


}
