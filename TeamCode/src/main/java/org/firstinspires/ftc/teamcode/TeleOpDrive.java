package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;
import static org.firstinspires.ftc.teamcode.modules.Robot.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.IntakePositions;
import org.firstinspires.ftc.teamcode.modules.Robot;
import org.openftc.easyopencv.OpenCvCamera;

@TeleOp(name = "Tele-Op Driving")
public class TeleOpDrive extends LinearOpMode {

    Robot bot;
    ElapsedTime pinch1Debounce;
    ElapsedTime pinch2Debounce;
    ElapsedTime speedFactorDebounce;
    ElapsedTime intakeSpeedFactorDebounce;
    private ElapsedTime intakeDebounce;

    static final int buttonDelay = 250;
    private int intakePosition = 0;

    double speedFactor = 0.7;
    double intakeSpeedFactor = 0.5;
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

    TouchSensor slideSwitch;

    OpenCvCamera webcam1;

    boolean pincher1Open;
    boolean pincher2Open;
    boolean droneLaunched;
    boolean manualControl;
    Boolean hangPrimed = false;
    Boolean hangInitiated = false;

    IntakePositions intakePositions[] = IntakePositions.values();

    @Override
    public void runOpMode() throws InterruptedException {

        initHardware();

        pinch1Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        pinch2Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        speedFactorDebounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        intakeSpeedFactorDebounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        intakeDebounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        telemetry.addLine("> PRESS START");
        waitForStart();

        telemetry.addLine("> PROGRAM STARTED");
        while(opModeIsActive()) {

            processVariableUpdates();

            processDriving();
            processControl();
            processTelemetry();

            passiveResetSlidePositions();
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
        if(gamepad2.left_stick_button){
            activeResetSlidePositions();
        }

        if(gamepad2.left_bumper && !previousGamepad2.left_bumper && pinch1Debounce.milliseconds() > buttonDelay) {
            pincher1Open = !pincher1Open;
            pinch1Debounce.reset();
        }

        if(gamepad2.right_bumper && !previousGamepad2.right_bumper && pinch2Debounce.milliseconds() > buttonDelay) {
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
            drone.setPosition(0.7);
            droneLaunched = true;
        }

        if(!droneLaunched){
            drone.setPosition(0);
        }

        if(manualControl) {
            double intakeSlidePower = -gamepad2.left_stick_y;
            double intakeArmPower = -gamepad2.right_stick_y;

            intakeSlide1.setMode(RUN_USING_ENCODER);
            intakeSlide2.setMode(RUN_USING_ENCODER);
            intakeArm.setMode(RUN_USING_ENCODER);

            intakeSlide1.setPower(intakeSlidePower);
            intakeSlide2.setPower(intakeSlidePower);
            intakeArm.setPower(intakeArmPower);

            if (gamepad2.back && !previousGamepad2.back) {
                if (intakePosition > 10) {
                    intakePosition = 10;
                }
                manualControl = false;
                intakeSlide1.setMode(STOP_AND_RESET_ENCODER);
                intakeSlide2.setMode(STOP_AND_RESET_ENCODER);
                intakeArm.setMode(STOP_AND_RESET_ENCODER);
            }
        }else {
            if(gamepad2.back && !previousGamepad2.back){
                manualControl = true;
            }

            if (gamepad2.dpad_up && intakeDebounce.milliseconds() > 200) {
                intakePosition++;
                intakeDebounce.reset();
            } else if (gamepad2.dpad_down && intakeDebounce.milliseconds() > 200) {
                intakePosition--;
                intakeDebounce.reset();
            }

            if (intakePosition > 10) {
                intakePosition = 10;
            } else if (intakePosition < 0) {
                intakePosition = 0;
            }

            if(gamepad2.x && gamepad2.b){
                hangPrimed = true;
            }

            if(hangPrimed){
                intakePosition = 11;
                telemetry.addLine("READY TO HANG");
            }

            if(hangPrimed && gamepad2.y && gamepad2.a){
                hangInitiated = true;
            }

            if(hangInitiated){
                intakePosition = 12;
                telemetry.clearAll();
                telemetry.addLine("I really hope this works");
                telemetry.addLine("and we are haning right now");
                telemetry.addLine("- Cohen");
                telemetry.update();
            }

            bot.runIntake(intakePositions[intakePosition], intakeSpeedFactor);
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

        if (gamepad2.dpad_right && (intakeSpeedFactorDebounce.milliseconds() >= buttonDelay)) {
            intakeSpeedFactorDebounce.reset();
            intakeSpeedFactor += 0.1;
        }

        if (gamepad2.dpad_left && (intakeSpeedFactorDebounce.milliseconds() >= buttonDelay)) {
            intakeSpeedFactorDebounce.reset();
            intakeSpeedFactor -= 0.1;
        }

        if (intakeSpeedFactor > 1) {
            intakeSpeedFactor = 1;
        } else if (intakeSpeedFactor <= 0) {
            intakeSpeedFactor = 0.1;
        }
    }

    private void processTelemetry(){
        if(!hangInitiated) {
            telemetry.addLine("DRIVER:");
            telemetry.addData("Speed Factor", speedFactor);
            if (droneLaunched) {
                telemetry.addLine("DRONE LAUNCHED");
            }

            telemetry.addLine();
            telemetry.addLine();

            telemetry.addLine("CONTROLLER:");
            telemetry.addData("Intake Speed", intakeSpeedFactor);
            telemetry.addLine();
            telemetry.addData("Intake Position",intakePositions[intakePosition]);
            telemetry.addData("Arm Position", intakeArm.getCurrentPosition());
            telemetry.addData("Arm Power", intakeArm.getPower());
            telemetry.addData("Slide Position", intakeSlide1.getCurrentPosition());
            telemetry.addData("Slides At Bottom", slideSwitch.isPressed());
            telemetry.addLine();
            if (pincher1Open) {
                telemetry.addLine("LEFT PINCHER OPEN");
            } else {
                telemetry.addLine("LEFT PINCHER CLOSED");
            }

            if (pincher2Open) {
                telemetry.addLine("RIGHT PINCHER OPEN");
            } else {
                telemetry.addLine("RIGHT PINCHER CLOSED");
            }

            telemetry.update();
        }
    }
    private void initHardware() {
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

        slideSwitch = hardwareMap.get(TouchSensor.class, "slideSwitch");

        manualControl = false;

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

    }
    private void activeResetSlidePositions(){
        intakeArm.setTargetPosition(20);
        intakeArm.setMode(RUN_TO_POSITION);
        intakeArm.setPower(0.5);

        intakeSlide1.setMode(RUN_USING_ENCODER);
        intakeSlide2.setMode(RUN_USING_ENCODER);
        intakeSlide1.setPower(-0.5);
        intakeSlide2.setPower(-0.5);

        while(!slideSwitch.isPressed() && opModeIsActive()) {
            telemetry.addLine("WAITING FOR INTAKE RESET");
            telemetry.update();
            if(gamepad2.right_stick_button){return;}
        }
        intakeSlide1.setMode(STOP_AND_RESET_ENCODER);
        intakeSlide2.setMode(STOP_AND_RESET_ENCODER);
        telemetry.addLine("Encoders Reset!");
    }

    private void passiveResetSlidePositions(){
        if(intakeSlide1.getCurrentPosition() == 0 && !slideSwitch.isPressed() && !manualControl) {
            telemetry.addLine("ATTEMPTING TO AUTO RESET");
            telemetry.addLine();
            telemetry.addLine("PRESS RIGHT STICK IN TO CANCEL AND ENTER MANUAL MODE");
            telemetry.update();

            intakeArm.setTargetPosition(100);
            intakeArm.setMode(RUN_TO_POSITION);
            intakeArm.setPower(0.5);

            intakeSlide1.setMode(RUN_USING_ENCODER);
            intakeSlide1.setPower(-0.5);

            intakeSlide2.setMode(RUN_USING_ENCODER);
            intakeSlide2.setPower(-0.5);

            while (intakeSlide1.getCurrentPosition() < 5 && !slideSwitch.isPressed() && !manualControl) {
                telemetry.addLine("ATTEMPTING TO AUTO RESET");
                telemetry.addLine();
                telemetry.addLine("PRESS RIGHT STICK IN TO CANCEL AND ENTER MANUAL MODE");
                telemetry.update();
                if (gamepad2.right_stick_button) {
                    manualControl = true;
                    return;
                }
            }
            intakeSlide1.setMode(STOP_AND_RESET_ENCODER);
            intakeSlide2.setMode(STOP_AND_RESET_ENCODER);
        }

        if(slideSwitch.isPressed() && intakePosition == 0 || intakePosition == 2 && !manualControl) {
            intakeSlide1.setMode(STOP_AND_RESET_ENCODER);
            intakeSlide2.setMode(STOP_AND_RESET_ENCODER);
            telemetry.addLine("Encoders Reset!");
        }
    }
}