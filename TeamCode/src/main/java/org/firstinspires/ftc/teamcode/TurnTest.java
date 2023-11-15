package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class TurnTest extends LinearOpMode {

    DcMotor driveFrontLeft;
    DcMotor driveFrontRight;
    DcMotor driveBackLeft;
    DcMotor driveBackRight;

    @Override
    public void runOpMode() throws InterruptedException {
        driveFrontLeft  = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        int degree;

        init();

        Robot bot = new Robot(driveFrontLeft, driveBackLeft, driveBackRight, driveFrontRight);

        telemetry.addLine("OpMode Init");
        telemetry.update();

        waitForStart();

        bot.turn(90, Direction.LEFT);
        telemetry.addLine("Left 90 degrees");
        telemetry.update();

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy())

        bot.turn(90, Direction.RIGHT);
        telemetry.addLine("Right 90 degrees");
        telemetry.update();

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy())

        bot.turn(180, Direction.LEFT);
        telemetry.addLine("Left 180 degrees");
        telemetry.update();

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy())

        bot.turn(180, Direction.RIGHT);
        telemetry.addLine("Right 180 degrees");
        telemetry.update();
    }
}
