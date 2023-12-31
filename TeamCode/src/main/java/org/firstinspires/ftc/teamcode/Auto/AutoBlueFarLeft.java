// Works For Scrimmage



package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.Robot.*;
import static org.firstinspires.ftc.teamcode.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.Direction.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Direction;
import org.firstinspires.ftc.teamcode.IntakePositions;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name = "Auto Blue Far Left")
public class AutoBlueFarLeft extends LinearOpMode {

    private DcMotor driveFrontLeft;
    private DcMotor driveFrontRight;
    private DcMotor driveBackRight;
    private DcMotor driveBackLeft;
    private DcMotor intakeSlide1;
    private DcMotor intakeSlide2;
    private DcMotor intakeArm;
    private Servo pincher1;
    private Servo pincher2;

    @Override
    public void runOpMode() throws InterruptedException {

        driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        intakeSlide1 = hardwareMap.get(DcMotor.class, "intakeSlide1");
        intakeSlide2 = hardwareMap.get(DcMotor.class, "intakeSlide2");
        intakeArm = hardwareMap.get(DcMotor.class, "intakeSlide1");
        pincher1 = hardwareMap.get(Servo.class, "pincher1");
        pincher2 = hardwareMap.get(Servo.class, "pincher2");

        Robot bot = new Robot(
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

        pincher1.setPosition(PINCHER_1_CLOSED);
        pincher2.setPosition(PINCHER_2_CLOSED);

        bot.runIntake(LOADING, 0.3);

        bot.forward(12, 0.3);

        /*
        Find team prop
        */
        String propPos;
        propPos = "Right";
        propPos = "Left";
        propPos = "Center";
        /*
        Find team prop
         */

        if (propPos.equals("Right")) {

            bot.forward(24, 0.3);
            bot.turn(90, 0.3, RIGHT);
            bot.forward(12, 0.3);
            bot.runIntake(PICKING, 0.3);
            pincher1.setPosition(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, 0.3);
            bot.forward(-12, 0.3);
            bot.turn(90, 0.3, LEFT);
        } else if (propPos.equals("Left")) {

            bot.forward(24, 0.3);
            bot.turn(90, 0.3, LEFT);
            bot.forward(12, 0.3);
            bot.runIntake(PICKING, 0.3);
            pincher1.setPosition(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, 0.3);
            bot.forward(-12, 0.3);
            bot.turn(90, 0.3, RIGHT);
        }else{

            bot.forward(36, 0.3);
            bot.runIntake(PICKING, 0.3);
            pincher1.setPosition(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, 0.3);
            bot.forward(-12, 0.3);
        }
    }
}