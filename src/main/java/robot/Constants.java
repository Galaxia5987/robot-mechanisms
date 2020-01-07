package robot;

import javax.xml.xpath.XPathConstants;

/**
 * A class holding all of the constants of every mechanism on the robot.
 * Place global constants in this class, and mechanism-specific constants inside their respective mechanism subclass.
 * When accessing a mechanism-specific port, call Constants.[MECHANISM].[CONSTANT]
 */
public class Constants {
    //All general constants go here
    //public static final double TIME_STEP = 0.02;


    public static class Drivetrain {
        public static final double TICKS_PER_METER = 256 / (2 * 0.0254 * Math.PI);
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
    }

    public static class Turret {
        public static final int TALON_TIMEOUT = 10;
        public static final int TALON_PID_SLOT = 0;
        public static final int MAX_CURRENT = 35;
        public static final double TICKS_PER_DEGREE = 260.7/180;
        public static double KP = 15;
        public static double KI = 0;
        public static double KD = 0.5;
        public static double KF = 0;
        public static final int CRUISE_VELOCITY= (int) (200 * TICKS_PER_DEGREE) / 10;
        public static final int CRUISE_ACCELERATION = (int) (250 * TICKS_PER_DEGREE) / 10;
        public static final double HALL_EFFECT_POSITION = 180; // in degrees
        public static final double ANGLE_THRESHOLD = 5;
        public static final double MAXIMUM_ANGLE = 350;
        public static final double MINIMUM_ANGLE = 0;
    }
    public static class VisionTurret {

        public static final double VISION_ANGLE_THRESHOLD = 0.5;
        // TODO: Change vision PID values
        public static double VISION_KP = 0.03;
        public static double VISION_KI = 0;
        public static double VISION_KD = 0.04;
    }
}