package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
public class Robot {

    private DcMotor driveFrontLeft;
    private DcMotor driveFrontRight;
    private DcMotor driveBackRight;
    private DcMotor driveBackLeft;
    private DcMotor intakeSlide1;
    private DcMotor intakeSlide2;
    private DcMotor intakeArm;

    private Servo pincher1;
    private Servo pincher2;

    private static final double TICKS_PER_CM = 17.83; // 17.83 tics/cm traveled(Strafer)

    private static final double TICKS_PER_INCH = 45.2847909135; //17.83 cm / 2.54 = inches per cm

    private static final double ROTATION_CORRECTION = 1.2; //(62/90);

    private static final double TURN_CONSTANT = 50.5d/90d; // distance per deg

    public static final double
            PINCHER_1_CLOSED = 0,
            PINCHER_1_OPEN = 0,
            PINCHER_2_CLOSED = 0,
            PINCHER_2_OPEN = 0;


    // Linear Slide Position Constants
    private static final int
            SLIDE_LOADING_POSITION = 0,
            SLIDE_PICKING_POSITION = 0,
            SLIDE_TRAVELING_POSITION = 0,
            SLIDE_LINE01_POSITION = 0,
            SLIDE_LINE1_POSITION = 0,
            SLIDE_LINE12_POSITION = 0,
            SLIDE_LINE2_POSITION = 0,
            SLIDE_LINE23_POSITION = 0,
            SLIDE_LINE3_POSITION = 0,
            SLIDE_TOP_POSITION = 0,

            ARM_LOADING_POSITION = 0,
            ARM_PICKING_POSITION = 0,
            ARM_TRAVELING_POSITION = 0,
            ARM_LINE01_POSITION = 0,
            ARM_LINE1_POSITION = 0,
            ARM_LINE12_POSITION = 0,
            ARM_LINE2_POSITION = 0,
            ARM_LINE23_POSITION = 0,
            ARM_LINE3_POSITION = 0,
            ARM_TOP_POSITION = 0;

    public Robot(
            DcMotor driveFrontLeft,
            DcMotor driveBackLeft,
            DcMotor driveBackRight,
            DcMotor driveFrontRight,
            DcMotor intakeSlide1,
            DcMotor intakeSlide2,
            DcMotor intakeArm,
            Servo pincher1,
            Servo pincher2
    ) {

        this.driveFrontLeft = driveFrontLeft;
        this.driveFrontRight = driveFrontRight;
        this.driveBackRight = driveBackRight;
        this.driveBackLeft = driveBackLeft;
        this.intakeSlide1 = intakeSlide1;
        this.intakeSlide2 = intakeSlide2;
        this.intakeArm = intakeArm;
        this.pincher1 = pincher1;
        this.pincher2 = pincher2;
    }

    public void forward(double distanceInches, double speed){
        setup();
        int distanceTicks = inchesToTicks(distanceInches);

        // Move Forward
        driveFrontLeft.setDirection(REVERSE);
        driveFrontRight.setDirection(FORWARD);
        driveBackLeft.setDirection(REVERSE);
        driveBackRight.setDirection(FORWARD);

        driveTargetPositions(distanceTicks);

        driveMotors(speed);
    }

    public void strafe(int distanceInches, double speed, Direction direction) {
        setup();
        int distanceTicks = inchesToTicks(distanceInches);


        switch(direction){
            case LEFT:
                // Strafe Left
                driveFrontLeft.setDirection(FORWARD);
                driveFrontRight.setDirection(FORWARD);
                driveBackLeft.setDirection(REVERSE);
                driveBackRight.setDirection(REVERSE);

                // Set distance or tick variable to each motor
                driveTargetPositions(distanceTicks);
                break;
            case RIGHT:
                // Strafe Right
                driveFrontLeft.setDirection(REVERSE);
                driveFrontRight.setDirection(REVERSE);
                driveBackLeft.setDirection(FORWARD);
                driveBackRight.setDirection(FORWARD);

                driveTargetPositions(distanceTicks);
                break;
        }

        driveMotors(speed);
    }

    public void turn(int degrees, double speed, Direction direction){
        setup();
        int distanceTicks = degreesToDistance(degrees);
        switch(direction){
            case LEFT:
                driveFrontLeft.setDirection(FORWARD);
                driveFrontRight.setDirection(FORWARD);
                driveBackLeft.setDirection(FORWARD);
                driveBackRight.setDirection(FORWARD);

                driveTargetPositions(distanceTicks);
                break;
            case RIGHT:
                driveFrontLeft.setDirection(REVERSE);
                driveFrontRight.setDirection(REVERSE);
                driveBackLeft.setDirection(REVERSE);
                driveBackRight.setDirection(REVERSE);

                driveTargetPositions(distanceTicks);
                break;
        }
        driveMotors(speed);
    }

    public void runPincher1(double position) {
        pincher1.setPosition(position);
    }

    public void runPincher2(double position) {
        pincher2.setPosition(position);
    }

    public void runIntake(IntakePositions IntakePositions, double speed){
        setup();
        int armDistance = 0;
        int slideDistance = 0;
        switch (IntakePositions){
            case LOADING:
                slideDistance = SLIDE_LOADING_POSITION;
                armDistance = ARM_LOADING_POSITION;
                break;
            case PICKING:
                slideDistance = SLIDE_PICKING_POSITION;
                armDistance = ARM_PICKING_POSITION;
                break;
            case TRAVELING:
                slideDistance = SLIDE_TRAVELING_POSITION;
                armDistance = ARM_TRAVELING_POSITION;
                break;
            case LINE01:
                slideDistance = SLIDE_LINE01_POSITION;
                armDistance = ARM_LINE01_POSITION;
                break;
            case LINE1:
                slideDistance = SLIDE_LINE1_POSITION;
                armDistance = ARM_LINE1_POSITION;
                break;
            case LINE12:
                slideDistance = SLIDE_LINE12_POSITION;
                armDistance = ARM_LINE12_POSITION;
                break;
            case LINE2:
                slideDistance = SLIDE_LINE2_POSITION;
                armDistance = ARM_LINE2_POSITION;
                break;
            case LINE23:
                slideDistance = SLIDE_LINE23_POSITION;
                armDistance = ARM_LINE23_POSITION;
                break;
            case LINE3:
                slideDistance = SLIDE_LINE3_POSITION;
                armDistance = ARM_LINE3_POSITION;
                break;
            case TOP:
                slideDistance = SLIDE_TOP_POSITION;
                armDistance = ARM_TOP_POSITION;
                break;
        }

        intakeTargetPositions(armDistance, slideDistance);
        intakeMotors(speed);
    }

    private void setup(){
        // Reset the motor encoder so that it reads zero ticks
        driveFrontLeft.setMode(STOP_AND_RESET_ENCODER);
        driveFrontRight.setMode(STOP_AND_RESET_ENCODER);
        driveBackLeft.setMode(STOP_AND_RESET_ENCODER);
        driveBackRight.setMode(STOP_AND_RESET_ENCODER);

        // Behavior when motor stops
        driveFrontLeft.setZeroPowerBehavior(BRAKE);
        driveFrontRight.setZeroPowerBehavior(BRAKE);
        driveBackLeft.setZeroPowerBehavior(BRAKE);
        driveBackRight.setZeroPowerBehavior(BRAKE);
        intakeArm.setZeroPowerBehavior(BRAKE);
        intakeSlide1.setZeroPowerBehavior(BRAKE);
        intakeSlide2.setZeroPowerBehavior(BRAKE);
        intakeSlide1.setDirection(REVERSE);

    }

    private int degreesToDistance(int degrees) {
        int distance = ((int) ((degrees * TURN_CONSTANT) * TICKS_PER_CM * ROTATION_CORRECTION));

        return distance;
    }

    private int inchesToTicks(double distanceInches) {
        int distanceTicks;
        distanceTicks = ((int) (distanceInches * TICKS_PER_INCH));
        return distanceTicks;
    }

    private void driveMotors(double power){
        driveFrontLeft.setPower(power);
        driveFrontRight.setPower(power);
        driveBackLeft.setPower(power);
        driveBackRight.setPower(power);
    }

    private void driveTargetPositions(int distanceTicks){
        driveFrontLeft.setTargetPosition(distanceTicks);
        driveFrontRight.setTargetPosition(distanceTicks);
        driveBackLeft.setTargetPosition(distanceTicks);
        driveBackRight.setTargetPosition(distanceTicks);

        driveFrontLeft.setMode(RUN_TO_POSITION);
        driveFrontRight.setMode(RUN_TO_POSITION);
        driveBackLeft.setMode(RUN_TO_POSITION);
        driveBackRight.setMode(RUN_TO_POSITION);
    }

    private void intakeMotors(double power){
        intakeArm.setPower(power);
        intakeSlide1.setPower(power);
        intakeSlide2.setPower(power);
    }

    private void intakeTargetPositions(int armDistance, int slideDistance){
        intakeArm.setTargetPosition(armDistance);
        intakeSlide1.setTargetPosition(slideDistance);
        intakeSlide2.setTargetPosition(slideDistance);

        intakeArm.setMode(RUN_TO_POSITION);
        intakeSlide1.setMode(RUN_TO_POSITION);
        intakeSlide2.setMode(RUN_TO_POSITION);
    }
}
