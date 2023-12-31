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

    private static int RESOLUTION_WIDTH = 1280;
    private static int RESOLUTION_HEIGHT = 720;
    private static final double SPEED_INTAKE = .3;
    @Override
    public void runOpMode() throws InterruptedException {
        /*
        Find team prop
         */

        String propPos;
        propPos = "Right";
        propPos = "Left";
        propPos = "Center";


        bot.forward(24, 0.3);

        if (propPos.equals("Right")) {
            bot.turn(90, 0.3, RIGHT);
            bot.forward(12, 0.3);
            bot.runIntake(PICKING, SPEED_INTAKE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.forward(-12, 0.3);
            bot.turn(90, 0.3, LEFT);

        } else if (propPos.equals("Left")) {
            bot.forward(24, 0.3);
            bot.turn(90, 0.3, LEFT);
            bot.forward(12, 0.3);
            bot.runIntake(PICKING, SPEED_INTAKE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.forward(-12, 0.3);
            bot.turn(90, 0.3, RIGHT);
        }else{
            bot.forward(12, 0.3);
            bot.runIntake(PICKING, SPEED_INTAKE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.forward(-12, 0.3);
        }
    }
}