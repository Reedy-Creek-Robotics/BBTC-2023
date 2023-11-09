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

@TeleOp(name = "Tele-Op Driving")
public class TeleOpDrive extends LinearOpMode {

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
        DcMotor intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        Servo pincherRotation = hardwareMap.get(Servo.class, "pincherRotation");
        Servo pincher1 = hardwareMap.get(Servo.class, "pincher1");
        Servo pincher2 = hardwareMap.get(Servo.class, "pincher2");

        intakeArm.setZeroPowerBehavior(BRAKE);

        double speedFactor = 0.7;

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad currentGamepad2 = new Gamepad();

        Gamepad previousGamepad1 = new Gamepad();
        Gamepad previousGamepad2 = new Gamepad();

        // Move forward
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

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

            // pincherRotation
            //in-taking
            if(gamepad2.x){
                pincherRotation.setPosition(0);
            }
            //traveling
            else if(gamepad2.y){
                pincherRotation.setPosition(0.1);
            }
            //depositing
            else if(gamepad2.b){
                pincherRotation.setPosition(0.2);
            }

            //closed position
            if(lx2 < 0.1 && lx2 > -0.1){
                pincher1.setPosition(0.45);
            }
            //open position
            else{
                pincher1.setPosition(0.2);
            }

            //closed position
            if(rx2 < 0.1 && rx2 > -0.1){
                pincher2.setPosition(0.2);
            }
            //open position
            else{
                pincher2.setPosition(0.5);
            }

            frontLeft.setPower(frontLeftPower * speedFactor);
            backLeft.setPower(backLeftPower * speedFactor);
            frontRight.setPower(frontRightPower * speedFactor);
            backRight.setPower(backRightPower * speedFactor);
            intakeArm.setPower(intakeArmPower);

            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.addData("backRight",backRight.getCurrentPosition());
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("Left Stick ly1", ly1);
            telemetry.addData("Left Stick lx1", lx1);
            telemetry.addData("Right Stick lx1", rx1);
            telemetry.update();
        }
    }
}