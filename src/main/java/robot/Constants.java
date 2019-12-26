package robot;

/**
 * A class holding all of the constants of every mechanism on the robot.
 * Place global constants in this class, and mechanism-specific constants inside their respective mechanism subclass.
 * When accessing a mechanism-specific port, call Constants.[MECHANISM].[CONSTANT]
 */
public class Constants {
    //All general constants go here
    //public static final double TIME_STEP = 0.02;


    public static class Drivetrain {
        public static final double TICKS_PER_METER = 256 / (2 *0.0254 * Math.PI);
        public static final double MAX_VEL = 3;// in m/s
        public static final double TIME_STEP = 0.02;
        public static final double MAX_ACCELERATION = 0.4;// in m/s^2 (currently not the correct number)
        public static final int MAX_CURRENT = 35;
    }

    public static class Shooter {
        public static final int TALON_PID_SLOT = 0;
        public static final double KP = .57;
        public static final double KI = 0;
        public static final double KD = 0.31;
        public static final double KF = 0;
        public static final int TIMEOUT_MS = 20;
        public static final boolean IS_MASTER_INVERTED = false;
        public static final boolean IS_SLAVE_INVERTED = true;
        public static final int MAX_CURRENT = 35; //[A]
        public static final double TICKS_PER_METER = 36 / (2 * 0.05 * Math.PI);
        public static final double TICKS_PER_ROTATION = 36;
        public static final double HEIGHT = 0; // [m]
        public static final double ANGLE = 45; // [deg]
        public static final double RADIUS = 0.05; // [m]
        public static final double g = 9.80665; // [m/sec^2]
        public static final double TARGET_DISTANCE = 2; // [m]
        public static final double SHOOTING_TIME = 3.5;
        public static final double THRESHOLD = 0.1;
    }

    public static class ExampleSubsystem1 {
        //All of the Subsystem specific constants go here,and need to be static.

        //public static final double TICKS_PER_METER = 256 / (4*0.0254*Math.PI);
        //public static final double MAX_VELOCITY = 5;
    }
}