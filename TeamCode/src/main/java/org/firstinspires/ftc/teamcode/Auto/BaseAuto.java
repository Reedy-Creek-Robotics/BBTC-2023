package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.IntakePositions.LOADING;
import static org.firstinspires.ftc.teamcode.Robot.PINCHER_1_CLOSED;
import static org.firstinspires.ftc.teamcode.Robot.PINCHER_2_CLOSED;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.vision.VisionPortal;

public class BaseAuto extends LinearOpMode {
    Robot bot;

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

    private static int RESOLUTION_WIDTH = 1280;
    private static int RESOLUTION_HEIGHT = 720;
    private static final double SPEED_INTAKE = .3;

    public BaseAuto() {
        super();

        this.driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        this.driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        this.driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        this.driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        this.intakeSlide1 = hardwareMap.get(DcMotor.class, "intakeSlide1");
        this.intakeSlide2 = hardwareMap.get(DcMotor.class, "intakeSlide2");
        this.intakeArm = hardwareMap.get(DcMotor.class, "intakeSlide1");
        this.pincher1 = hardwareMap.get(Servo.class, "pincher1");
        this.pincher2 = hardwareMap.get(Servo.class, "pincher2");

        this.portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam1"))
                .setCameraResolution(new Size(RESOLUTION_WIDTH, RESOLUTION_HEIGHT))
                .build();

        this.pincher1.setPosition(PINCHER_1_CLOSED);
        this.pincher2.setPosition(PINCHER_2_CLOSED);

        this.bot = new Robot(
                driveFrontLeft,
                driveBackLeft,
                driveBackRight,
                driveFrontRight,
                intakeSlide1,
                intakeSlide2,
                intakeArm,
                pincher1,
                pincher2
        );

        bot.runIntake(LOADING, SPEED_INTAKE);
    }

    @Override
    public void runOpMode() throws InterruptedException {}
}
