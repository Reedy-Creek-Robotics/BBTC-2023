// Works For Scrimmage
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Auto Red Near Right")
public class AutoRedNearRight extends LinearOpMode {

    private DcMotor driveFrontLeft;
    private DcMotor driveFrontRight;
    private DcMotor driveBackRight;
    private DcMotor driveBackLeft;

    @Override
    public void runOpMode() throws InterruptedException {

        driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");

        // Reset the motor encoder so that it reads zero ticks
        driveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Behavior when motor stops
        driveFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Strafe Right
        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        // WAIT
        // Program pauses until start button is pressed on driver station
        telemetry.addLine("WAITING FOR START");
        waitForStart();

        // Total ticks to the destination
        int targetPosition = 2000;
        double speed = 0.3 ;

        driveFrontLeft.setTargetPosition(targetPosition);
        driveFrontRight.setTargetPosition(targetPosition);
        driveBackLeft.setTargetPosition(targetPosition);
        driveBackRight.setTargetPosition(targetPosition);

        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveFrontLeft.setPower(speed);
        driveFrontRight.setPower(speed);
        driveBackLeft.setPower(speed);
        driveBackRight.setPower(speed);

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
