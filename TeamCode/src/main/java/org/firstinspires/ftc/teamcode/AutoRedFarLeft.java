// Test At Scrimmage
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Auto Red Far Left")
public class AutoRedFarLeft extends LinearOpMode {

    private DcMotor driveFrontLeft;
    private DcMotor driveFrontRight;
    private DcMotor driveBackRight;
    private DcMotor driveBackLeft;

    @Override
    public void runOpMode() throws InterruptedException {

        // Total ticks to the destination
        int forwardDistance = 120;
        int strafeDistance = 2500;
        int forwardDistance2 = 2000;
        int strafeDistance2 = 2550;
        double speed = 0.3;

        driveFrontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        driveFrontRight = hardwareMap.get(DcMotor.class, "frontRight");
        driveBackLeft = hardwareMap.get(DcMotor.class, "backLeft");
        driveBackRight = hardwareMap.get(DcMotor.class, "backRight");

        Robot bot = new Robot(driveFrontLeft, driveBackLeft, driveBackRight, driveFrontRight);

        // Program pauses until start button is pressed on driver station
        telemetry.addLine("WAITING FOR START");
        waitForStart();

        bot.forward(forwardDistance, speed);
        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()) {
            telemetry.addData("backLeft", driveBackLeft.getCurrentPosition());
            telemetry.addData("backRight",driveBackRight.getCurrentPosition());
            telemetry.addData("frontRight", driveFrontRight.getCurrentPosition());
            telemetry.addData("frontLeft", driveFrontLeft.getCurrentPosition());
            telemetry.update();
        }

        driveBackLeft.setPower(0);
        driveBackRight.setPower(0);
        driveFrontRight.setPower(0);
        driveFrontLeft.setPower(0);

        bot.strafe(strafeDistance, speed, Direction.RIGHT);

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()) {
            telemetry.addData("backLeft", driveBackLeft.getCurrentPosition());
            telemetry.addData("backRight",driveBackRight.getCurrentPosition());
            telemetry.addData("frontRight", driveFrontRight.getCurrentPosition());
            telemetry.addData("frontLeft", driveFrontLeft.getCurrentPosition());
            telemetry.update();
        }

        driveBackLeft.setPower(0);
        driveBackRight.setPower(0);
        driveFrontRight.setPower(0);
        driveFrontLeft.setPower(0);

        bot.forward(forwardDistance2, speed);

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()) {
            telemetry.addData("backLeft", driveBackLeft.getCurrentPosition());
            telemetry.addData("backRight",driveBackRight.getCurrentPosition());
            telemetry.addData("frontRight", driveFrontRight.getCurrentPosition());
            telemetry.addData("frontLeft", driveFrontLeft.getCurrentPosition());
            telemetry.update();
        }

        bot.strafe(strafeDistance2, speed, Direction.RIGHT);

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()) {
            telemetry.addData("backLeft", driveBackLeft.getCurrentPosition());
            telemetry.addData("backRight",driveBackRight.getCurrentPosition());
            telemetry.addData("frontRight", driveFrontRight.getCurrentPosition());
            telemetry.addData("frontLeft", driveFrontLeft.getCurrentPosition());
            telemetry.update();
        }

        driveBackLeft.setPower(0);
        driveBackRight.setPower(0);
        driveFrontRight.setPower(0);
        driveFrontLeft.setPower(0);

    }
}