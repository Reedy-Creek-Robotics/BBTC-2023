package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class TurnTest extends LinearOpMode {


    private DcMotor driveFrontLeft;
    private DcMotor driveFrontRight;
    private DcMotor driveBackRight;
    private DcMotor driveBackLeft;

    @Override
    public void runOpMode() throws InterruptedException {

        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");

        Robot bot = new Robot(driveFrontLeft, driveBackLeft, driveBackRight, driveFrontRight);

        init();

        telemetry.addLine("Init");

        telemetry.update();

        waitForStart();


        bot.turn(90, 0.3, Direction.RIGHT);

        telemetry.addLine("Right 90");
        telemetry.update();

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()){}

        bot.turn(90, 0.3, Direction.LEFT);

        telemetry.addLine("Left 90");
        telemetry.update();

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()){}

        bot.turn(180, 0.3, Direction.RIGHT);

        telemetry.addLine("Right 180");
        telemetry.update();

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()){}

        bot.turn(180, 0.3, Direction.LEFT);

        telemetry.addLine("Left 180");
        telemetry.update();

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()){}
    }
}
