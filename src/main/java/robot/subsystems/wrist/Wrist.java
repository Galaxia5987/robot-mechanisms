package robot.subsystems.wrist;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static robot.Ports.Wrist.MOTOR;

public class Wrist extends Subsystem {
    private TalonSRX armMotor = new TalonSRX(MOTOR);

    public Wrist() {
        
    }

    @Override
    protected void initDefaultCommand() {

    }
}
