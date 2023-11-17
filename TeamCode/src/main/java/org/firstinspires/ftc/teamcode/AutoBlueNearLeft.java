// Works For Scrimmage
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Auto Blue Near Left")
public class AutoBlueNearLeft extends LinearOpMode {

    private DcMotor driveFrontLeft;
    private DcMotor driveFrontRight;
    private DcMotor driveBackRight;
    private DcMotor driveBackLeft;

    @Override
    public void runOpMode() throws InterruptedException {

        // Total ticks to the destination
        int strafeDistance = 2000;
        double speed = 0.3;

        driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");

        Robot bot = new Robot(driveFrontLeft, driveBackLeft, driveBackRight, driveFrontRight);

        // Program pauses until start button is pressed on driver station
        telemetry.addLine("WAITING FOR START");
        waitForStart();

        bot.strafe(strafeDistance, speed, Direction.LEFT);

        while(opModeIsActive() && driveBackLeft.isBusy() && driveBackRight.isBusy() && driveFrontLeft.isBusy() && driveFrontRight.isBusy()) {
            telemetry.addData("driveBackLeft", driveBackLeft.getCurrentPosition());
            telemetry.addData("driveBackRight",driveBackRight.getCurrentPosition());
            telemetry.addData("driveFrontRight", driveFrontRight.getCurrentPosition());
            telemetry.addData("driveFrontLeft", driveFrontLeft.getCurrentPosition());
            telemetry.update();
        }

        driveBackLeft.setPower(0);
        driveBackRight.setPower(0);
        driveFrontRight.setPower(0);
        driveFrontLeft.setPower(0);

    }
}
