// Works For Scrimmage



package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.Robot.*;
import static org.firstinspires.ftc.teamcode.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.Direction.*;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Direction;
import org.firstinspires.ftc.teamcode.IntakePositions;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.Auto.BaseAuto;
import org.firstinspires.ftc.vision.VisionPortal;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Autonomous(name = "Auto Blue Far Left")
public class AutoBlueFarLeft extends BaseAuto {

    private static final double SPEED_INTAKE = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        /*
        Find team prop
         */
        bot.runIntake(LOADING, SPEED_INTAKE);
        bot.waitIntake();

        String propPos = bot.detectPropPosition("blue");


        bot.forward(24, 0.3);
        bot.waitDrive();

        if (propPos.equals("right")) {
            bot.turn(90, 0.3, RIGHT);
            bot.waitDrive();
            bot.forward(12, 0.3);
            bot.waitDrive();
            bot.runIntake(PICKING, SPEED_INTAKE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.waitIntake();
            bot.forward(-12, 0.3);
            bot.waitDrive();
            bot.turn(90, 0.3, LEFT);
            bot.waitDrive();

        } else if (propPos.equals("left")) {
            bot.forward(24, 0.3);
            bot.waitDrive();
            bot.turn(90, 0.3, LEFT);
            bot.waitDrive();
            bot.forward(12, 0.3);
            bot.waitDrive();
            bot.runIntake(PICKING, SPEED_INTAKE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.waitIntake();
            bot.forward(-12, 0.3);
            bot.waitDrive();
            bot.turn(90, 0.3, RIGHT);
            bot.waitDrive();
        }else{
            bot.forward(12, 0.3);
            bot.waitDrive();
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.waitIntake();
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.waitIntake();
            bot.forward(-12, 0.3);
            bot.waitDrive();
        }
    }
}