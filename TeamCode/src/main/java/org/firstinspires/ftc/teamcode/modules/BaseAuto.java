package org.firstinspires.ftc.teamcode.modules;

import static org.firstinspires.ftc.teamcode.modules.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.modules.Direction.RIGHT;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.BOTTOM;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.LINE1;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.LOADING;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.PICKING;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.TRAVELING;
import static org.firstinspires.ftc.teamcode.modules.Robot.*;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.modules.Robot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class BaseAuto extends LinearOpMode {
    public Robot bot;

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
    private VisionPortal portal;
    private OpenCvCamera webcam1;
    protected static final double SPEED_INTAKE = 0.3;
    protected static final double SPEED_DRIVE = 0.3;
    protected String bluePropPos;
    protected String redPropPos;

    @Override
    public void runOpMode() throws InterruptedException {
        this.driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        this.driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        this.driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        this.driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        this.intakeSlide1 = hardwareMap.get(DcMotor.class, "intakeSlide1");
        this.intakeSlide2 = hardwareMap.get(DcMotor.class, "intakeSlide2");
        this.intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        this.pincher1 = hardwareMap.get(Servo.class, "pincher1");
        this.pincher2 = hardwareMap.get(Servo.class, "pincher2");

        this.driveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.driveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.driveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.driveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.intakeSlide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.intakeSlide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        this.slideSwitch = hardwareMap.get(TouchSensor.class, "slideSwitch");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam1 = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam1"), cameraMonitorViewId);

        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                telemetry.addLine("Camera Init Successful");
                telemetry.update();

                webcam1.startStreaming(800, 600, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Error", errorCode);
                telemetry.update();
            }
        });


       this.bot = new Robot(
                 driveFrontLeft,
                driveBackLeft,
                driveBackRight,
                driveFrontRight,
                intakeSlide1,
                intakeSlide2,
                intakeArm,
                pincher1,
                pincher2,
                slideSwitch,
                webcam1,
                telemetry,
                this
        );
        WebcamPipeline colorDetector = new WebcamPipeline(colorToDetect);
        webcam1.setPipeline(colorDetector);

        Thread.sleep(2000);

        waitForStart();

        bluePropPos = bot.detectPropPosition("blue");
        redPropPos = bot.detectPropPosition("red");

        webcam1.stopStreaming();
        webcam1.closeCameraDevice();

        this.pincher1.setPosition(PINCHER_1_CLOSED);
        this.pincher2.setPosition(PINCHER_2_CLOSED);

        Thread.sleep(100);

        bot.runIntake(PICKING, SPEED_INTAKE);
    }
}
