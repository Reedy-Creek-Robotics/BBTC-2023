package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.Robot.*;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.vision.VisionPortal;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

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
    private VisionPortal portal;
    private OpenCvCamera webcam1;

    private static int RESOLUTION_WIDTH = 1280;
    private static int RESOLUTION_HEIGHT = 720;

    @Override
    public void runOpMode() throws InterruptedException {
        this.driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        this.driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        this.driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        this.driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        this.pincher1 = hardwareMap.get(Servo.class, "pincher1");
        this.pincher2 = hardwareMap.get(Servo.class, "pincher2");

        /*
        this.portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(RESOLUTION_WIDTH, RESOLUTION_HEIGHT))
                .build();
        */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam1 = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam1"), cameraMonitorViewId);

        webcam1.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                telemetry.addLine("Camera Init Successful");
                telemetry.update();

                webcam1.startStreaming(800, 600, OpenCvCameraRotation.UPRIGHT);
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
                pincher1,
                pincher2,
                webcam1,
                telemetry,
                this
        );

        waitForStart();

        this.pincher1.setPosition(PINCHER_1_CLOSED);
        this.pincher2.setPosition(PINCHER_2_CLOSED);

    }
}
