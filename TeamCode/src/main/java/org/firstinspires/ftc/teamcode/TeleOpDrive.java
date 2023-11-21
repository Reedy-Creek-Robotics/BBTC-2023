package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

@TeleOp(name = "Tele-Op Driving")
public class TeleOpDrive extends LinearOpMode {

    static final double INCREMENT = 0.05;
    ElapsedTime pinch1Debounce;
    ElapsedTime pinch2Debounce;
    ElapsedTime speedFactorDebounce;
    static final int buttonDelay = 250;
    double speedFactor = 0.7;
    double ly1;
    double lx1;
    double rx1;
    double lt2;
    double rt2;
    double lt1;
    double rt1;
    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    DcMotor driveFrontLeft;
    DcMotor driveFrontRight;
    DcMotor driveBackLeft;
    DcMotor driveBackRight;
    DcMotor intakeArm;
    Servo pincher1;
    Servo pincher2;
    boolean pincher1Open;
    boolean pincher2Open;

    // APRIL TAGS

    private VisionPortal visionPortal;                           // Used to manage the video source.
    private AprilTagProcessor aprilTag;
    boolean targetFound = false;                               // Set to true when an AprilTag target is detected
    double forwardPower = 0.3;                                // Desired forward power/speed (-1 to +1)
    double strafePower = 0.3;                                // Desired strafePower power/speed (-1 to +1)
    double turnPower = 0.3;                                 // Desired turning power/speed (-1 to +1)
    final double desiredDistance = 5.0;                    //  this is how close the camera should get to the target (inches)
    final double forwardGain  =  0.02  ;                  //  Forward Speed Control "Gain". eg: Ramp up to 50% power at a 25 inch error.   (0.50 / 25.0)
    final double strafeGain =  0.015 ;                   //  Strafe Speed Control "Gain".  eg: Ramp up to 25% power at a 25 degree Yaw error.   (0.25 / 25.0)
    final double turnGain   =  0.01  ;                  //  Turn Control "Gain".  eg: Ramp up to 25% power at a 25 degree error. (0.25 / 25.0)
    final double maxAutoForwardSpeed = 0.5;            //  Clip the approach speed to this max value (adjust for your robot)
    final double maxAutoStrafeSpeed= 0.5;             //  Clip the approach speed to this max value (adjust for your robot)
    final double maxAutoTurnSpeed  = 0.3;            //  Clip the turnPower speed to this max value (adjust for your robot)
    private static final boolean USE_WEBCAM = true; // Set true to use a webcam, or false for a phone camera
    private static final int desiredTagID = -1;    // Choose the tag you want to approach or set to -1 for ANY tag.
    private AprilTagDetection detectedTag = null; // Used to hold the data for a detected AprilTag


    @Override
    public void runOpMode() throws InterruptedException {
        // the robot should NOT move in this part of the program (its a penalty)

        initHardware();
        initAprilTag();

        waitForStart();

        while (opModeIsActive()) {

            processVariableUpdates();

            processAprilTagDetection();
            processDriving();
            processControl();
            processTelemetry();

        }
    }

    private void processDriving() {
        if (gamepad1.left_bumper && targetFound) {
            // Determine heading, range and Yaw (tag image rotation) error so we can use them to control the robot automatically.
            double rangeError = (detectedTag.ftcPose.range - desiredDistance);
            double headingError = detectedTag.ftcPose.bearing;
            double yawError = detectedTag.ftcPose.yaw;

            // Use the speed and turnPower "gains" to calculate how we want the robot to move.
            forwardPower = Range.clip(rangeError * forwardGain, -maxAutoForwardSpeed, maxAutoForwardSpeed);
            turnPower = Range.clip(headingError * turnGain, -maxAutoTurnSpeed, maxAutoTurnSpeed);
            strafePower = Range.clip(-yawError * strafeGain, -maxAutoStrafeSpeed, maxAutoStrafeSpeed);
        }else {

            forwardPower  = -gamepad1.left_stick_y;  // Reduce forwardPower rate to 50%.
            strafePower = -gamepad1.left_stick_x;   // Reduce strafePower rate to 50%.
            turnPower   = -gamepad1.right_stick_x;  // Reduce turnPower rate to 33%
        }
        processMoveRobot(forwardPower, strafePower, turnPower);
    }

    private void processMoveRobot(double x, double y, double yaw){
        double driveFrontLeftPower = x -y -yaw;
        double driveFrontRightPower = x +y +yaw;
        double driveBackLeftPower = x +y -yaw;
        double driveBackRightPower = x -y +yaw;

        // Normalize wheel powers to be less than 1.0
        double max = Math.max(Math.abs(driveFrontLeftPower), Math.abs(driveFrontRightPower));
        max = Math.max(max, Math.abs(driveBackLeftPower));
        max = Math.max(max, Math.abs(driveBackRightPower));

        if (max > 1.0) {
            driveFrontLeftPower /= max;
            driveFrontRightPower /= max;
            driveBackLeftPower /= max;
            driveBackRightPower /= max;
        }

        // Send powers to the wheels.
        driveFrontLeft.setPower(driveFrontLeftPower);
        driveFrontRight.setPower(driveFrontRightPower);
        driveBackLeft.setPower(driveBackLeftPower);
        driveBackRight.setPower(driveBackRightPower);
    }

    private void processControl() {
        double intakeArmPower = (lt1 - rt1);

        if (gamepad2.left_bumper && pinch1Debounce.milliseconds() > buttonDelay) {
            pincher1Open = !pincher1Open;
            pinch1Debounce.reset();
        }

        if (gamepad2.right_bumper && pinch2Debounce.milliseconds() > buttonDelay) {
            pincher2Open = !pincher2Open;
            pinch2Debounce.reset();
        }

        if (pincher1Open) {
            pincher1.setPosition(0.2);
        } else {
            pincher1.setPosition(0.8);
        }

        if (pincher2Open) {
            pincher2.setPosition(0.5);
        } else {
            pincher2.setPosition(0.1);
        }

        if (intakeArm.getCurrentPosition() < -700) {
            intakeArm.setPower(0.3);
            telemetry.addLine("Test");
        } else {
            intakeArm.setPower(intakeArmPower);
            telemetry.addData("powering", intakeArmPower);
        }

    }

    private void processVariableUpdates() {
        ly1 = -gamepad1.left_stick_y;
        lx1 = gamepad1.left_stick_x * 1.1;
        rx1 = gamepad1.right_stick_x;
        lt2 = gamepad2.left_trigger;
        rt2 = gamepad2.right_trigger;
        lt1 = gamepad1.left_trigger;
        rt1 = gamepad1.right_trigger;

        // forwardPower using manual POV Joystick mode.  Slow things down to make the robot more controllable.
        forwardPower  = -gamepad1.left_stick_y  / 2.0;  // Reduce forwardPower rate to 50%.
        strafePower = -gamepad1.left_stick_x  / 2.0;   // Reduce strafePower rate to 50%.
        turnPower   = -gamepad1.right_stick_x / 3.0;  // Reduce turnPower rate to 33%.

        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);

        pinch1Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        pinch1Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        speedFactorDebounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        if (gamepad1.dpad_up && (speedFactorDebounce.milliseconds() >= buttonDelay)) {
            speedFactorDebounce.reset();
            speedFactor += 0.1;
            telemetry.addLine("Dpad Up Pressed");
        }

        if (gamepad1.dpad_down && (speedFactorDebounce.milliseconds() >= buttonDelay)) {
            speedFactorDebounce.reset();
            speedFactor -= 0.1;
            telemetry.addLine("Dpad Down Pressed");
        }
        if (speedFactor > 1) {
            speedFactor = 1;
        } else if (speedFactor <= 0) {
            speedFactor = 0.1;
        }
    }

    private void processTelemetry() {
        telemetry.addData("Left Stick ly1", ly1);
        telemetry.addData("Left Stick lx1", lx1);
        telemetry.addData("Right Stick lx1", rx1);
        telemetry.addData("Speed Factor", speedFactor);
        telemetry.addData("intakeArm:", intakeArm.getCurrentPosition());
        if (targetFound) {
            telemetry.addData(">","HOLD Left-Bumper to Drive to Target");
        }
        telemetry.update();
    }

    private void initAprilTag(){
        // Create the AprilTag processor by using a builder.
        aprilTag = new AprilTagProcessor.Builder().build();

        // Create the vision portal by using a builder.
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .addProcessor(aprilTag)
                .build();
    }

    private void initHardware() {
        driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFrontLeft.setZeroPowerBehavior(BRAKE);

        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        driveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveFrontRight.setZeroPowerBehavior(BRAKE);

        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackLeft.setZeroPowerBehavior(BRAKE);

        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        driveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveBackRight.setZeroPowerBehavior(BRAKE);

        intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pincher1 = hardwareMap.get(Servo.class, "pincher1");
        pincher2 = hardwareMap.get(Servo.class, "pincher2");
        boolean pincher1Open = true;
        boolean pincher2Open = true;

        intakeArm.setZeroPowerBehavior(BRAKE);

        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    private void setManualExposure(int exposureMS, int gain) {
        // Wait for the camera to be open, then use the controls

        if (visionPortal == null) {
            return;
        }

        // Make sure camera is streaming before we try to set the exposure controls
        if (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING) {
            telemetry.addData("Camera", "Waiting");
            telemetry.update();
            while (!isStopRequested() && (visionPortal.getCameraState() != VisionPortal.CameraState.STREAMING)) {
                sleep(20);
            }
            telemetry.addData("Camera", "Ready");
            telemetry.update();
        }

        if (!isStopRequested())
        {
            ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
            if (exposureControl.getMode() != ExposureControl.Mode.Manual) {
                exposureControl.setMode(ExposureControl.Mode.Manual);
                sleep(50);
            }
            exposureControl.setExposure((long)exposureMS, TimeUnit.MILLISECONDS);
            sleep(20);
            GainControl gainControl = visionPortal.getCameraControl(GainControl.class);
            gainControl.setGain(gain);
            sleep(20);
        }
    }

    private void processAprilTagDetection(){
        targetFound = false;
        detectedTag  = null;
        setManualExposure(6, 200);

        // Step through the list of detected tags and look for a matching tag
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if ((detection.metadata != null) &&
                    ((desiredTagID < 0) || (detection.id == desiredTagID))) {
                targetFound = true;
                detectedTag = detection;
                break;  // don't look any further.
            } else {
                telemetry.addData("Unknown Target", "Tag ID %d is not desired tag\n", detection.id);
            }
        }
    }
}