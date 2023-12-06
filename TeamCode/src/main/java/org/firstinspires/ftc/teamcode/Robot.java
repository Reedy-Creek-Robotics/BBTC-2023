package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

    // Linear Slide Position Constants

    private static final int LOADING_POSITION = 0;
    private static final int PICKING_POSITION = 0;
    private static final int TRAVELING_POSITION = 0;
    private static final int LINE01_POSITION = 0;
    private static final int LINE1_POSITION = 0;
    private static final int LINE12_POSITION = 0;
    private static final int LINE2_POSITION = 0;
    private static final int LINE23_POSITION = 0;
    private static final int LINE3_POSITION = 0;

    public Robot(DcMotor driveFrontLeft, DcMotor driveBackLeft, DcMotor driveBackRight, DcMotor driveFrontRight, DcMotor intakeSlide1, DcMotor intakeSlide2, DcMotor intakeArm, Servo pincher1, Servo pincher2){

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
        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        driveTargetPositions(distanceTicks);

        driveMotors(speed);
    }

    public void strafe(int distanceInches, double speed, Direction direction) {
        setup();
        int distanceTicks = inchesToTicks(distanceInches);


        switch(direction){
            case LEFT:
                // Strafe Left
                driveFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
                driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                driveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

                // Set distance or tick variable to each motor
                driveTargetPositions(distanceTicks);
                break;
            case RIGHT:
                // Strafe Right
                driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
                driveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

                driveTargetPositions(distanceTicks);
                break;
        }

        driveMotors(speed);

        // Run motors for
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void turn(int degrees, double speed, Direction direction){
        setup();
        int distanceTicks = degreesToDistance(degrees);
        switch(direction){
            case LEFT:
                driveFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
                driveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

                driveTargetPositions(distanceTicks);
                break;
            case RIGHT:
                driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
                driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                driveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

                driveTargetPositions(distanceTicks);
                break;
        }
        driveMotors(speed);
    }

    public void runLinearSlides(LinearSlidePositions linearSlidePositions, double speed){
        setup();
        switch (linearSlidePositions){
            case LOADING:
                intakeSlide1.setTargetPosition(LOADING_POSITION);
                intakeSlide2.setTargetPosition(LOADING_POSITION);
                break;
            case TRAVELING:
                break;
            case PICKING:
                break;
            case LINE01:
                break;
            case LINE1:
                break;
            case LINE12:
                break;
            case LINE2:
                break;
            case LINE23:
                break;
            case LINE3:
                break;
        }
    }

    private void setup(){
        // Reset the motor encoder so that it reads zero ticks
        driveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Behavior when motor stops
        driveFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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

        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


}
