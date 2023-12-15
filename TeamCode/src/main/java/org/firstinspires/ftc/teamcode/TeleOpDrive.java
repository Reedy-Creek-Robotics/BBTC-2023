package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Tele-Op Driving")
public class TeleOpDrive extends LinearOpMode {

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
    Servo drone;
    boolean pincher1Open;
    boolean pincher2Open;
    boolean droneLaunched;
    @Override
    public void runOpMode() throws InterruptedException {
        // the robot should NOT move in this part of the program (its a penalty)

        initHardware();

        pinch1Debounce = new ElapsedTime();
        pinch1Debounce = new ElapsedTime();
        speedFactorDebounce = new ElapsedTime();

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
        double frontLeftPower = Math.pow(((ly1 + lx1 + rx1) / denominator), 2);
        double backLeftPower = Math.pow(((ly1 - lx1 + rx1) / denominator), 2);
        double frontRightPower = Math.pow(((ly1 - lx1 - rx1) / denominator), 2);
        double backRightPower = Math.pow(((ly1 + lx1 - rx1) / denominator), 2);

        driveFrontLeft.setPower(frontLeftPower * speedFactor);
        driveBackLeft.setPower(backLeftPower * speedFactor);
        driveFrontRight.setPower(frontRightPower * speedFactor);
        driveBackRight.setPower(backRightPower * speedFactor);
    }


    /**
     * Processes the control of systems besides driving
     */
    private void processControl(){
        double intakeArmPower = (lt1 - rt1);

        if(gamepad2.left_bumper && pinch1Debounce.milliseconds() > buttonDelay) {
            pincher1Open = !pincher1Open;
            pinch1Debounce.reset();
        }

        if(gamepad2.right_bumper && pinch2Debounce.milliseconds() > buttonDelay) {
            pincher2Open = !pincher2Open;
            pinch2Debounce.reset();
        }

        if(pincher1Open){
            pincher1.setPosition(0.2);
        } else {
            pincher1.setPosition(0.8);
        }

        if(pincher2Open){
            pincher2.setPosition(0.5);
        } else {
            pincher2.setPosition(0.1);
        }

        if(gamepad1.x && gamepad1.b){
            drone.setPosition(1);
            droneLaunched = true;
        }

        if(!droneLaunched){
            drone.setPosition(0.5);
        }

        if(intakeArm.getCurrentPosition() < -700){
            intakeArm.setPower(0.3);
            telemetry.addLine("Test");
        }else {
            intakeArm.setPower(intakeArmPower);
            telemetry.addData("powering", intakeArmPower);
        }

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
        telemetry.addData("Left Stick ly1", ly1);
        telemetry.addData("Left Stick lx1", lx1);
        telemetry.addData("Right Stick lx1", rx1);
        telemetry.addData("Speed Factor", speedFactor);
        telemetry.addData("intakeArm:", intakeArm.getCurrentPosition());
        if(droneLaunched){
            telemetry.addLine("DRONE LAUNCHED");
        }
        telemetry.addData("Distance", driveFrontRight.getCurrentPosition());

        telemetry.update();
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
        intakeArm.setZeroPowerBehavior(BRAKE);

        pincher1 = hardwareMap.get(Servo.class, "pincher1");
        pincher2 = hardwareMap.get(Servo.class, "pincher2");
        drone = hardwareMap.get(Servo.class, "drone");
        pincher1Open = true;
        pincher2Open = true;

        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    }
}