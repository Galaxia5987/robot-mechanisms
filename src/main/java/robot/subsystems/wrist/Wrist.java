package robot.subsystems.wrist;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

import static robot.Constants.Wrist.*;
import static robot.Ports.Wrist.MOTOR;

public class Wrist extends Subsystem {
    private TalonSRX armMotor = new TalonSRX(MOTOR);

    public Wrist() {
        armMotor.config_kP(TALON_PID_SLOT, KP, TALON_TIMEOUT_MS);
        armMotor.config_kI(TALON_PID_SLOT, KI, TALON_TIMEOUT_MS);
        armMotor.config_kD(TALON_PID_SLOT, KD, TALON_TIMEOUT_MS);
    }

    public void setAngle(double degrees) {

    }

    public void getAngle(){}

    public void update(){}

    public void reset(){}

    public void setPercent(){}

    public double ConvertTicksToDegrees(double degrees) {
        return Math.toRadians(degrees) * TICKS_PER_DEGREE;
    }

    public double ConvertDegreesToTicks(double degrees){
        return 0;
    }


    @Override
    protected void initDefaultCommand() {

    }
}
