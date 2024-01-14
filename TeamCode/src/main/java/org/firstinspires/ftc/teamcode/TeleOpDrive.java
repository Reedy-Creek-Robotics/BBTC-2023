package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;
import static org.firstinspires.ftc.teamcode.Robot.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;
import java.util.List;

@TeleOp(name = "Tele-Op Driving")
public class TeleOpDrive extends LinearOpMode {

    Robot bot;
    ElapsedTime pinch1Debounce;
    ElapsedTime pinch2Debounce;
    ElapsedTime speedFactorDebounce;
    private ElapsedTime intakeDebounce;
    static final int buttonDelay = 250;
    private int intakePosition = 0;
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
    DcMotor intakeSlide1;
    DcMotor intakeSlide2;
    DcMotor intakeArm;
    Servo pincher1;
    Servo pincher2;
    Servo drone;
    boolean pincher1Open;
    boolean pincher2Open;
    boolean droneLaunched;
    IntakePositions intakePositions[] = IntakePositions.values();

    @Override
    public void runOpMode() throws InterruptedException {

        initHardware();

        pinch1Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        pinch2Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        speedFactorDebounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        intakeDebounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        telemetry.addLine("> PRESS START");
        waitForStart();

        telemetry.addLine("> PROGRAM STARTED");
        while(opModeIsActive()) {

            processVariableUpdates();

            processDriving();
            processControl();
            processTelemetry();

        }
    }

    private void processDriving(){

        double denominator = Math.max(Math.abs(ly1) + Math.abs(lx1) + Math.abs(rx1), 1);
        double frontLeftPower = (ly1 + lx1 + rx1) / denominator;
        double backLeftPower = (ly1 - lx1 + rx1) / denominator;
        double frontRightPower = (ly1 - lx1 - rx1) / denominator;
        double backRightPower = (ly1 + lx1 - rx1) / denominator;

        driveFrontLeft.setPower(frontLeftPower * speedFactor);
        driveBackLeft.setPower(backLeftPower * speedFactor);
        driveFrontRight.setPower(frontRightPower * speedFactor);
        driveBackRight.setPower(backRightPower * speedFactor);
    }

    private void processControl(){

        if(gamepad2.left_bumper && pinch1Debounce.milliseconds() > buttonDelay) {
            pincher1Open = !pincher1Open;
            pinch1Debounce.reset();
        }

        if(gamepad2.right_bumper && pinch2Debounce.milliseconds() > buttonDelay) {
            pincher2Open = !pincher2Open;
            pinch2Debounce.reset();
        }

        if(pincher1Open){
            pincher1.setPosition(PINCHER_1_OPEN);
        } else {
            pincher1.setPosition(PINCHER_1_CLOSED);
        }

        if(pincher2Open){
            pincher2.setPosition(PINCHER_2_OPEN);
        } else {
            pincher2.setPosition(PINCHER_2_CLOSED);
        }

        if(gamepad1.x && gamepad1.b){
            drone.setPosition(1);
            droneLaunched = true;
        }

        if(!droneLaunched){
            drone.setPosition(0.5);
        }

        if (gamepad2.dpad_up && intakeDebounce.milliseconds() > 200){
            intakePosition++;
            intakeDebounce.reset();
        } else if(gamepad2.dpad_down && intakeDebounce.milliseconds() > 200){
            intakePosition--;
            intakeDebounce.reset();
        }

        if (intakePosition > 6){intakePosition = 6;}
        else if(intakePosition < 0){intakePosition = 0;}

        bot.runIntake(intakePositions[intakePosition],0.5);

    }

    private void processVariableUpdates(){
        ly1 = -gamepad1.left_stick_y;
        lx1 = gamepad1.left_stick_x * 1.1;
        rx1 = gamepad1.right_stick_x;
        lt2 = gamepad2.left_trigger;
        rt2 = gamepad2.right_trigger;
        lt1 = gamepad1.left_trigger;
        rt1 = gamepad1.right_trigger;

        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);

        if (gamepad1.dpad_up && (speedFactorDebounce.milliseconds() >= buttonDelay)) {
            speedFactorDebounce.reset();
            speedFactor += 0.1;
        }

        if (gamepad1.dpad_down && (speedFactorDebounce.milliseconds() >= buttonDelay)) {
            speedFactorDebounce.reset();
            speedFactor -= 0.1;
        }

        if (speedFactor > 1) {
            speedFactor = 1;
        } else if (speedFactor <= 0) {
            speedFactor = 0.1;
        }
    }

    private void processTelemetry(){
        double displayVal = intakeArm.getCurrentPosition();
        telemetry.addData("Left Stick ly1", ly1);
        telemetry.addData("Left Stick lx1", lx1);
        telemetry.addData("Right Stick lx1", rx1);
        telemetry.addData("Speed Factor", speedFactor);
        telemetry.addData("intakeArm:", displayVal);
        if(droneLaunched){
            telemetry.addLine("DRONE LAUNCHED");
        }
        telemetry.addData("Distance", driveFrontRight.getCurrentPosition());

        telemetry.update();
    }

    private void

    initHardware() {
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
        drone = hardwareMap.get(Servo.class, "drone");
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

    }
}