package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.IntakePositions.LINE1;
import static org.firstinspires.ftc.teamcode.IntakePositions.LINE2;
import static org.firstinspires.ftc.teamcode.IntakePositions.LINE3;
import static org.firstinspires.ftc.teamcode.IntakePositions.LOADING;
import static org.firstinspires.ftc.teamcode.IntakePositions.PICKING;
import static org.firstinspires.ftc.teamcode.IntakePositions.TOP;
import static org.firstinspires.ftc.teamcode.IntakePositions.TRAVELING;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

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

    private OpenCvCamera webcam1;

    private static final double TICKS_PER_CM = 17.83; // 17.83 tics/cm traveled(Strafer)

    private static final double TICKS_PER_INCH = 45.2847909135; //17.83 cm / 2.54 = inches per cm

    private static final double ROTATION_CORRECTION = 1.2; //(62/90);

    private static final double TURN_CONSTANT = 50.5d / 90d; // distance per deg

    //HSV Blue
    final Scalar LOW_BLUE = new Scalar(100, 100, 100);
    final Scalar HIGH_BLUE = new Scalar(130, 255, 255);

    final Scalar RED = new Scalar(255, 0 ,0);

    Mat hsvMat1 = new Mat();

    Mat inRangeMat1 = new Mat();

    Mat morph1 = new Mat();

    Mat hierarchy = new Mat();

    Mat kernel = Mat.ones(7, 7, CvType.CV_8UC1);

    List<MatOfPoint> contoursBlue = new ArrayList<>();

    public static final double
            PINCHER_1_CLOSED = 0.6,
            PINCHER_1_OPEN = 0.5,
            PINCHER_2_CLOSED = 0.4,
            PINCHER_2_OPEN = 0.5;

    //Todo: remove
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
            OpenCvCamera webcam1
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
        this.webcam1 = webcam1;
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
        setup();
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

    public String colorDetectionBlue() {
        String propPos = null;

        OpenCvPipeline blueProcessor = new OpenCvPipeline() {

            @Override
            public Mat processFrame(Mat input) {

                Imgproc.cvtColor(input, hsvMat1, Imgproc.COLOR_RGB2HSV);

                Core.inRange(hsvMat1, LOW_BLUE, HIGH_BLUE, inRangeMat1);

                Imgproc.morphologyEx(inRangeMat1, morph1, Imgproc.MORPH_CLOSE, kernel);
                Imgproc.morphologyEx(morph1, morph1, Imgproc.MORPH_OPEN, kernel);

                List<MatOfPoint> contours = new ArrayList<>();

                Imgproc.findContours(morph1, contours, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

                contoursBlue = contours;

                for (int i = 0; i < contours.size(); i++) {
                    Imgproc.drawContours(input, contours, i, RED, 5, 2);
                }
                return input;
            }
        };

        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                telemetry.addLine("Camera Init Successful");
                telemetry.update();

                webcam1.startStreaming(800, 600, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Load Failed | ERROR CODE", " " + errorCode);
            }
        });

            List<MatOfPoint> contoursBlue = this.contoursBlue;

            webcam1.setPipeline(blueProcessor);

            telemetry.addLine("Detecting BLUE Contours");

            telemetry.addData("Webcam pipeline activity", webcam1.getPipelineTimeMs());

            telemetry.addData("Contours Detected", contoursBlue.size());

            Point contourCent;

            for (int i = 0; i < contoursBlue.size(); i++) {
                if (Imgproc.contourArea(contoursBlue.get(i)) > 10000) {
                    Rect rect = Imgproc.boundingRect(contoursBlue.get(i));
                    contourCent = new Point(((rect.width - rect.x) / 2.0) + rect.x, ((rect.height - rect.y) / 2.0) + rect.y);
                    telemetry.addData("Area of Element:", Imgproc.contourArea(contoursBlue.get(i)));
                    telemetry.addData("Location of Element:", contourCent.x);

                    if(contourCent.x > 300){
                        propPos = "Right";
                    }else if(contourCent.x < 300){
                        propPos = "Center";
                    }
                    else {
                        propPos = "Left";
                    }
                }
            }
        telemetry.update();
        return(propPos);
    }

    private void setup(){

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

    public void waitDrive(){
        while(driveFrontLeft.isBusy() && driveFrontRight.isBusy() && driveBackLeft.isBusy() && driveBackRight.isBusy());
    }
    public void waitIntake(){
        while(intakeArm.isBusy() && intakeSlide1.isBusy() && intakeSlide2.isBusy());
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