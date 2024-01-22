package org.firstinspires.ftc.teamcode.test;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import static org.firstinspires.ftc.teamcode.Robot.PINCHER_1_CLOSED;
import static org.firstinspires.ftc.teamcode.Robot.PINCHER_1_OPEN;
import static org.firstinspires.ftc.teamcode.Robot.PINCHER_2_CLOSED;
import static org.firstinspires.ftc.teamcode.Robot.PINCHER_2_OPEN;

import org.firstinspires.ftc.teamcode.IntakePositions;
import org.firstinspires.ftc.teamcode.IntakePositions.*;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Robot;

import java.util.*;
@TeleOp(name = "Intake Position Test")
@Disabled
public class IntakePositionTest extends LinearOpMode {
    Robot bot;

    IntakePositions intakePositions[] = IntakePositions.values();

    private DcMotor driveFrontLeft;
    private DcMotor driveFrontRight;
    private DcMotor driveBackRight;
    private DcMotor driveBackLeft;
    private DcMotor intakeSlide1;
    private DcMotor intakeSlide2;
    private DcMotor intakeArm;
    private Servo pincher1;
    private Servo pincher2;
    private boolean pincher1Open;
    private boolean pincher2Open;
    private int intakePosition = 0;
    private ElapsedTime pinch1Debounce;
    private ElapsedTime pinch2Debounce;
    private ElapsedTime intakeDebounce;
    private int buttonDelay = 200;


    @Override
    public void runOpMode() throws InterruptedException {
        pinch1Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        pinch2Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        intakeDebounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontLeft.setMode(STOP_AND_RESET_ENCODER);
        driveFrontLeft.setMode(RUN_USING_ENCODER);
        driveFrontLeft.setZeroPowerBehavior(BRAKE);
        driveFrontLeft.setDirection(REVERSE);

        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        driveFrontRight.setMode(STOP_AND_RESET_ENCODER);
        driveFrontRight.setMode(RUN_USING_ENCODER);
        driveFrontRight.setZeroPowerBehavior(BRAKE);

        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackLeft.setMode(STOP_AND_RESET_ENCODER);
        driveBackLeft.setMode(RUN_USING_ENCODER);
        driveBackLeft.setZeroPowerBehavior(BRAKE);
        driveBackLeft.setDirection(REVERSE);

        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        driveBackRight.setMode(STOP_AND_RESET_ENCODER);
        driveBackRight.setMode(RUN_USING_ENCODER);
        driveBackRight.setZeroPowerBehavior(BRAKE);

        intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        intakeArm.setMode(STOP_AND_RESET_ENCODER);
        intakeArm.setMode(RUN_USING_ENCODER);
        intakeArm.setZeroPowerBehavior(BRAKE);

        intakeSlide1 = hardwareMap.get(DcMotor.class, "intakeSlide1");
        intakeSlide1.setMode(STOP_AND_RESET_ENCODER);
        intakeSlide1.setMode(RUN_USING_ENCODER);
        intakeSlide1.setZeroPowerBehavior(BRAKE);
        intakeSlide1.setDirection(REVERSE);

        intakeSlide2 = hardwareMap.get(DcMotor.class, "intakeSlide2");
        intakeSlide2.setMode(STOP_AND_RESET_ENCODER);
        intakeSlide2.setMode(RUN_USING_ENCODER);
        intakeSlide2.setZeroPowerBehavior(BRAKE);


        pincher1 = hardwareMap.get(Servo.class, "pincher1");
        pincher2 = hardwareMap.get(Servo.class, "pincher2");
        pincher1Open = true;
        pincher2Open = true;

        bot = new Robot(
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

        waitForStart();
        while(opModeIsActive()) {

            if (gamepad1.left_bumper && pinch1Debounce.milliseconds() > buttonDelay) {
                pincher1Open = !pincher1Open;
                pinch1Debounce.reset();
            }

            if (gamepad1.right_bumper && pinch2Debounce.milliseconds() > buttonDelay) {
                pincher2Open = !pincher2Open;
                pinch2Debounce.reset();
            }

            if (pincher1Open) {
                pincher1.setPosition(PINCHER_1_OPEN);
            } else {
                pincher1.setPosition(PINCHER_1_CLOSED);
            }

            if (pincher2Open) {
                pincher2.setPosition(PINCHER_2_OPEN);
            } else {
                pincher2.setPosition(PINCHER_2_CLOSED);
            }

            if (gamepad1.dpad_up && intakeDebounce.milliseconds() > 200){
                intakePosition++;
                intakeDebounce.reset();
            } else if(gamepad1.dpad_down && intakeDebounce.milliseconds() > 200){
                intakePosition--;
                intakeDebounce.reset();
            }

            if (intakePosition > 6){intakePosition = 6;}
            else if(intakePosition < 0){intakePosition = 0;}

            bot.runIntake(intakePositions[intakePosition],0.5);

        }


    }
}
