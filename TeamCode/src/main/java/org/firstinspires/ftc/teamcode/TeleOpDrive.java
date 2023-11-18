/*
ToDo - Add arm motor (TEST)
Todo - Add pincher platform rotation servo (TEST/INPUT #'s)
ToDo - Add pincher servos (TEST/INPUT #'s)
 */
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

    ElapsedTime dBounceTimerPinch1 = new ElapsedTime();
    ElapsedTime dBounceTimerPinch2 = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        // INIT
        // all code between here and WAIT runs when the INIT button is pressed on the driver station
        // this is where you initialize all the hardware and code for your program
        // the robot should NOT move in this part of the program (its a penalty)
        DcMotor driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        DcMotor driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        DcMotor driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        DcMotor driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        DcMotor intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        Servo pincher1 = hardwareMap.get(Servo.class, "pincher1");
        Servo pincher2 = hardwareMap.get(Servo.class, "pincher2");
        boolean pincher1Open = true;
        boolean pincher2Open = true;

        intakeArm.setZeroPowerBehavior(BRAKE);
        intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        double speedFactor = 0.7;

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        // Move forward
        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // WAIT
        // the program pauses here until the START button is pressed on the driver station
        telemetry.addLine("WAITING FOR START");
        waitForStart();
        // Code runs once at start pressed
        telemetry.addLine("PROGRAM STARTED");
        while( !isStopRequested() ) {
            // Event Loop
            telemetry.addLine("EVENT LOOP");
            double ly1 = -gamepad1.left_stick_y; // Remember, ly1 stick value is reversed
            double lx1 = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx1 = gamepad1.right_stick_x;
            double lt2 = gamepad2.left_trigger;
            double rt2 = gamepad2.right_trigger;
            double lx2 = gamepad2.left_stick_x;
            double rx2 = gamepad2.right_stick_x;
            double ly2 = gamepad2.left_stick_y;
            double ry2 = gamepad2.right_stick_y;



                // setting game-pad values
            previousGamepad1.copy(currentGamepad1);
            previousGamepad2.copy(currentGamepad2);
            currentGamepad1.copy(gamepad1);
            currentGamepad2.copy(gamepad2);

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(ly1) + Math.abs(lx1) + Math.abs(rx1), 1);
            double frontLeftPower = (ly1 + lx1 + rx1) / denominator;
            double backLeftPower = (ly1 - lx1 + rx1) / denominator;
            double frontRightPower = (ly1 - lx1 - rx1) / denominator;
            double backRightPower = (ly1 + lx1 - rx1) / denominator;
            double intakeArmPower = (lt2 - rt2);



           if(gamepad2.left_bumper && dBounceTimerPinch1.milliseconds() > 200) {
               pincher1Open = !pincher1Open;
               dBounceTimerPinch1.reset();
           }

            if(gamepad2.right_bumper && dBounceTimerPinch2.milliseconds() > 200) {
                pincher2Open = !pincher2Open;
                dBounceTimerPinch2.reset();
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

           /*
            //closed position
            if(lx2 < 0.1 && lx2 > -0.1 && ly2 < 0.1 && ly2 > -0.1){
                pincher1.setPosition(0.2);
            }
            //open position
            else{
                pincher1.setPosition(0.5);
            }

            //closed position
            if(rx2 < 0.1 && rx2 > -0.1 && ry2 < 0.1 && ry2 > -0.1){
                pincher2.setPosition(0.5);
            }
            //open position
            else{
                pincher2.setPosition(0.19);
            }
*/
            driveFrontLeft.setPower(frontLeftPower * speedFactor);
            driveBackLeft.setPower(backLeftPower * speedFactor);
            driveFrontRight.setPower(frontRightPower * speedFactor);
            driveBackRight.setPower(backRightPower * speedFactor);
            if(intakeArm.getCurrentPosition() < -300){
                intakeArm.setPower(0);
                telemetry.addLine("Test");
            }else {
                intakeArm.setPower(intakeArmPower / 2);
                telemetry.addData("powering", intakeArmPower);
            }

            /*if(Math.abs(intakeArmPower) <= 0.05){
                int pos = intakeArm.getCurrentPosition();
                intakeArm.setTargetPosition(pos);
                intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                intakeArm.setPower(1);
                telemetry.addLine("here");
            }*/

            telemetry.addData("driveBackLeft", driveBackLeft.getCurrentPosition());
            telemetry.addData("driveBackRight",driveBackRight.getCurrentPosition());
            telemetry.addData("driveFrontRight", driveFrontRight.getCurrentPosition());
            telemetry.addData("driveFrontLeft", driveFrontLeft.getCurrentPosition());
            telemetry.addData("Left Stick ly1", ly1);
            telemetry.addData("Left Stick lx1", lx1);
            telemetry.addData("Right Stick lx1", rx1);
            telemetry.addData("intakeArm:", intakeArm.getCurrentPosition());
            telemetry.addData("test", intakeArm.getPowerFloat());
            telemetry.update();
        }
    }
}