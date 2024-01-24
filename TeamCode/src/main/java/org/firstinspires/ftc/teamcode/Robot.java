package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.IntakePositions.*;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
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

    private static final double TURN_CONSTANT = 50.5d / 90d; // distance per deg

    boolean switchPressed;
    boolean previousSwitchPressed;

    IntakePositions intakePositions[] = IntakePositions.values();

    Telemetry telemetry;

    LinearOpMode opMode;

    public static final double
            PINCHER_1_CLOSED = 0.6,
            PINCHER_1_OPEN = 0.44,
            PINCHER_2_CLOSED = 0.4,
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


        switch (direction) {
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

    public void turn(int degrees, double speed, Direction direction) {
        setup();
        int distanceTicks = degreesToDistance(degrees);
        switch (direction) {
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

    public void runIntake(IntakePositions IntakePositions, double speed) {
        //setup();
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
            case TRAVELING:
                slideDistance = TRAVELING.getSlidePosition();
                armDistance = TRAVELING.getArmPosition();
                break;
            case BOTTOM:
                slideDistance = BOTTOM.getSlidePosition();
                armDistance = BOTTOM.getArmPosition();
                break;
            case LINE1:
                slideDistance = LINE1.getSlidePosition();
                armDistance = LINE1.getArmPosition();
                break;
            case LINE2:
                slideDistance = LINE2.getSlidePosition();
                armDistance = LINE2.getArmPosition();
                break;
            case LINE3:
                slideDistance = LINE3.getSlidePosition();
                armDistance = LINE3.getArmPosition();
                break;
            case TOP:
                slideDistance = TOP.getSlidePosition();
                armDistance = TOP.getArmPosition();
                break;
        }

        intakeTargetPositions(armDistance, slideDistance);
        intakeMotors(speed);
    }

    public String detectPropPosition(String colorToDetect) {
        String propPos = "Left";

        WebcamPipeline colorDetector = new WebcamPipeline(colorToDetect);
        webcam1.setPipeline(colorDetector);

        int count = 0;

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        propPos = colorDetector.getPropPos();

        while(propPos == "" && !opMode.isStopRequested()) {
            telemetry.addData("waiting for contours", count++);

            propPos = colorDetector.getPropPos();

            if (count > 5) {
                telemetry.addData("waiting for contours failed after 5 retries, move on", count++);
                propPos = "Left";
                break;
            }
        }

        return propPos;
    }

    private void setup() {

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

    private void driveMotors(double power) {
        driveFrontLeft.setPower(power);
        driveFrontRight.setPower(power);
        driveBackLeft.setPower(power);
        driveBackRight.setPower(power);
    }

    private void driveTargetPositions(int distanceTicks) {
        driveFrontLeft.setTargetPosition(distanceTicks);
        driveFrontRight.setTargetPosition(distanceTicks);
        driveBackLeft.setTargetPosition(distanceTicks);
        driveBackRight.setTargetPosition(distanceTicks);

        driveFrontLeft.setMode(RUN_TO_POSITION);
        driveFrontRight.setMode(RUN_TO_POSITION);
        driveBackLeft.setMode(RUN_TO_POSITION);
        driveBackRight.setMode(RUN_TO_POSITION);
    }

    public void waitDrive() {
        while (driveFrontLeft.isBusy() && driveFrontRight.isBusy() && driveBackLeft.isBusy() && driveBackRight.isBusy() && opMode.opModeIsActive()) ;
    }

    public void waitIntake() {
        while (intakeArm.isBusy() && intakeSlide1.isBusy() && intakeSlide2.isBusy() && opMode.opModeIsActive()) {
            // Intake has moved to target position, reset encoders in case we had any
            // slipping while running
            resetSlidePositions();
        }
    }

    private void intakeMotors(double power) {
        intakeArm.setPower(power);
        intakeSlide1.setPower(power);
        intakeSlide2.setPower(power);
    }

    private void intakeTargetPositions(int armDistance, int slideDistance) {
        intakeArm.setTargetPosition(armDistance);
        intakeSlide1.setTargetPosition(slideDistance);
        intakeSlide2.setTargetPosition(slideDistance);

        intakeArm.setMode(RUN_TO_POSITION);
        intakeSlide1.setMode(RUN_TO_POSITION);
        intakeSlide2.setMode(RUN_TO_POSITION);
    }

    public void resetSlidePositions(){
        switchPressed = slideSwitch.isPressed();
        if(intakeSlide1.getTargetPosition() <= 90 || intakeSlide2.getTargetPosition() <= 90) {
            if(slideSwitch.isPressed()) {
                telemetry.addData("Reset Encoders", true);
                telemetry.update();
                intakeSlide1.setMode(STOP_AND_RESET_ENCODER);
                intakeSlide2.setMode(STOP_AND_RESET_ENCODER);
            }
        }
        previousSwitchPressed = slideSwitch.isPressed();
    }
}