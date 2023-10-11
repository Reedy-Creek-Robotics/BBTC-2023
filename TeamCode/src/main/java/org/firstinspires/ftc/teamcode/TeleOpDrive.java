/*
1. Strafing and turning on opposite joysticks than expected
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleOp Driving")
public class TeleOpDrive extends LinearOpMode {

    double speedFactor = 0.7;

    @Override
    public void runOpMode() throws InterruptedException {
        // INIT
        // all code between here and WAIT runs when the INIT button is pressed on the driver station
        // this is where you initialize all the hardware and code for your program
        // the robot should NOT move in this part of the program (its a penalty)
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        DcMotor backRight = hardwareMap.get(DcMotor.class, "backRight");

        // Move forward
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // WAIT
        // the program pauses here until the START button is pressed on the driver station
        telemetry.addLine("WAITING FOR START");
        waitForStart();



        // START / RUN
        // all code after here runs when the START button is pressed on the driver station
        // this is where all your code to move the robot goes
        // once all instructions are executed the program will exit
        telemetry.addLine("PROGRAM STARTED");
        while( !isStopRequested() ) {
            // this is your EVENT loop (like a video game loop)
            // these instructions will be executed over and over until STOP is pressed on the driver station
            // you will listen for gamepad and other input commands in this loop
            telemetry.addLine("EVENT LOOP");
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;


            if(gamepad1.dpad_up){
                speedFactor = 0.9;
            }


            if(gamepad1.dpad_down) {
                speedFactor = 0.5;
            }


            if(gamepad1.dpad_right || gamepad1.dpad_left) {
                speedFactor = 0.7;
            }


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower * speedFactor);
            backLeft.setPower(backLeftPower * speedFactor);
            frontRight.setPower(frontRightPower * speedFactor);
            backRight.setPower(backRightPower * speedFactor);

            telemetry.addData("Speed Factor", speedFactor);
            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.addData("backRight",backRight.getCurrentPosition());
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("Left Stick Y", y);
            telemetry.addData("Left Stick X", x);
            telemetry.addData("Right Stick X", rx);
            telemetry.update();
        }
    }
}

