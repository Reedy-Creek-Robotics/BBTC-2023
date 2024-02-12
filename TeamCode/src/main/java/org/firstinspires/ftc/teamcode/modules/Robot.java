package org.firstinspires.ftc.teamcode.modules;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.*;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.*;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.openftc.easyopencv.OpenCvCamera;

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

    private TouchSensor slideSwitch;

    private OpenCvCamera webcam1;

    private static final double TICKS_PER_CM = 17.83; // 17.83 tics/cm traveled(Strafer)

    private static final double TICKS_PER_INCH = 45.2847909135; //17.83 cm / 2.54 = inches per cm

    private static final double ROTATION_CORRECTION = 1.2; //(62/90);

    private static final double TURN_CONSTANT = 50.5d / 90d; // distance per deg b

    IntakePositions intakePositions[] = IntakePositions.values();

    Telemetry telemetry;

    LinearOpMode opMode;

    public static final double
            PINCHER_1_CLOSED = 0.55 ,
            PINCHER_1_OPEN = 0.44,
            PINCHER_2_CLOSED = 0.45,
            PINCHER_2_OPEN = 0.54;

    public Robot(
            DcMotor driveFrontLeft,
            DcMotor driveBackLeft,
            DcMotor driveBackRight,
            DcMotor driveFrontRight,
            DcMotor intakeSlide1,
            DcMotor intakeSlide2,
            DcMotor intakeArm,
            Servo pincher1,
            Servo pincher2,
            TouchSensor slideSwitch,
            OpenCvCamera webcam1,
            Telemetry telemetry,
            LinearOpMode opMode
    ) {

        this.driveFrontLeft = driveFrontLeft;
        this.driveBackLeft = driveBackLeft;
        this.driveBackRight = driveBackRight;
        this.driveFrontRight = driveFrontRight;
        this.intakeSlide1 = intakeSlide1;
        this.intakeSlide2 = intakeSlide2;
        this.intakeArm = intakeArm;
        this.pincher1 = pincher1;
        this.pincher2 = pincher2;
        this.slideSwitch = slideSwitch;
        this.webcam1 = webcam1;
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    public void forward(double distanceInches, double speed) {
        int distanceTicks = inchesToTicks(distanceInches);

        driveFrontLeft.setTargetPosition(distanceTicks);
        driveFrontRight.setTargetPosition(distanceTicks);
        driveBackLeft.setTargetPosition(distanceTicks);
        driveBackRight.setTargetPosition(distanceTicks);

        driveMotors(speed);
    }

    public void strafe(double distanceInches, double speed, Direction direction) {
        int distanceTicks = inchesToTicks(distanceInches);


        switch (direction) {
            case LEFT:
                driveFrontLeft.setTargetPosition(-distanceTicks);
                driveFrontRight.setTargetPosition(distanceTicks);
                driveBackLeft.setTargetPosition(distanceTicks);
                driveBackRight.setTargetPosition(-distanceTicks);
                break;
            case RIGHT:
                driveFrontLeft.setTargetPosition(distanceTicks);
                driveFrontRight.setTargetPosition(-distanceTicks);
                driveBackLeft.setTargetPosition(-distanceTicks);
                driveBackRight.setTargetPosition(distanceTicks);
                break;
        }
        driveMotors(speed);
    }

    public void turn(int degrees, double speed, Direction direction) {
        int distanceTicks = degreesToDistance(degrees);
        switch (direction) {
            case LEFT:
                driveFrontLeft.setTargetPosition(-distanceTicks);
                driveFrontRight.setTargetPosition(distanceTicks);
                driveBackLeft.setTargetPosition(-distanceTicks);
                driveBackRight.setTargetPosition(distanceTicks);
                break;
            case RIGHT:
                driveFrontLeft.setTargetPosition(distanceTicks);
                driveFrontRight.setTargetPosition(-distanceTicks);
                driveBackLeft.setTargetPosition(distanceTicks);
                driveBackRight.setTargetPosition(-distanceTicks);
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

    public void runIntake(IntakePositions IntakePositions, double speed) {
        int armDistance = 0;
        int slideDistance = 0;

        switch (IntakePositions) {
            case LOADING:
                slideDistance = LOADING.getSlidePosition();
                armDistance = LOADING.getArmPosition();
                break;
            case PICKING:
                slideDistance = PICKING.getSlidePosition();
                armDistance = PICKING.getArmPosition();
                break;
            case STAGE0:
                slideDistance = STAGE0.getSlidePosition();
                armDistance = STAGE0.getArmPosition();
                break;
            case STAGE1:
                slideDistance = STAGE1.getSlidePosition();
                armDistance = STAGE1.getArmPosition();
                break;
            case STAGE2:
                slideDistance = STAGE2.getSlidePosition();
                armDistance = STAGE2.getArmPosition();
                break;
            case STAGE3:
                slideDistance = STAGE3.getSlidePosition();
                armDistance = STAGE3.getArmPosition();
                break;
            case STAGE4:
                slideDistance = STAGE4.getSlidePosition();
                armDistance = STAGE4.getArmPosition();
                break;
            case PRIME_HANGING:
                slideDistance = PRIME_HANGING.getSlidePosition();
                armDistance = PRIME_HANGING.getArmPosition();
                break;
            case HANGING:
                slideDistance = HANGING.getSlidePosition();
                armDistance = HANGING.getArmPosition();
                break;
        }
        intakeMotors(armDistance, slideDistance, speed);
    }

    public void setup() {

        driveFrontLeft.setZeroPowerBehavior(BRAKE);
        driveFrontRight.setZeroPowerBehavior(BRAKE);
        driveBackLeft.setZeroPowerBehavior(BRAKE);
        driveBackRight.setZeroPowerBehavior(BRAKE);
        driveFrontLeft.setDirection(REVERSE);
        driveFrontRight.setDirection(FORWARD);
        driveBackLeft.setDirection(REVERSE);
        driveBackRight.setDirection(FORWARD);
        intakeArm.setZeroPowerBehavior(BRAKE);
        intakeSlide1.setZeroPowerBehavior(BRAKE);
        intakeSlide2.setZeroPowerBehavior(BRAKE);
        intakeSlide1.setDirection(REVERSE);

    }

    public String detectPropPosition(String colorToDetect) throws InterruptedException {
        String propPos = "Right";

        int count = 0;

        WebcamPipeline colorDetector = new WebcamPipeline(colorToDetect);
        webcam1.setPipeline(colorDetector);

        Thread.sleep(2000);

        propPos = colorDetector.getPropPos();

        while(propPos == "" && opMode.opModeIsActive()){

            propPos = colorDetector.getPropPos();

            count++;

            if (count > 5) {
                propPos = "Right";
                break;
            }
        }

        return propPos;
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

    private void driveMotors(double power) {
        driveFrontLeft.setMode(RUN_TO_POSITION);
        driveFrontRight.setMode(RUN_TO_POSITION);
        driveBackLeft.setMode(RUN_TO_POSITION);
        driveBackRight.setMode(RUN_TO_POSITION);

        driveFrontLeft.setPower(power);
        driveFrontRight.setPower(power);
        driveBackLeft.setPower(power);
        driveBackRight.setPower(power);

        waitDrive();
    }

    private void waitDrive() {
        while (driveFrontLeft.isBusy() && driveFrontRight.isBusy() && driveBackLeft.isBusy() && driveBackRight.isBusy() && opMode.opModeIsActive()) ;
    }

    private void intakeMotors(int armDistance, int slideDistance, double power) {
        intakeArm.setTargetPosition(armDistance);
        intakeSlide1.setTargetPosition(slideDistance);
        intakeSlide2.setTargetPosition(slideDistance);

        intakeArm.setMode(RUN_TO_POSITION);
        intakeSlide1.setMode(RUN_TO_POSITION);
        intakeSlide2.setMode(RUN_TO_POSITION);

        intakeArm.setPower(power);
        intakeSlide1.setPower(power);
        intakeSlide2.setPower(power);
    }
}