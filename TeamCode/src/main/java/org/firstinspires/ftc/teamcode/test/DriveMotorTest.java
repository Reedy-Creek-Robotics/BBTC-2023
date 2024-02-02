package org.firstinspires.ftc.teamcode.test;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Drive Motor Test")
public class DriveMotorTest extends LinearOpMode {

    DcMotor driveFrontLeft;
    DcMotor driveBackLeft;
    DcMotor driveFrontRight;
    DcMotor driveBackRight;

    @Override
    public void runOpMode() throws InterruptedException {

        driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontLeft.setMode(RUN_USING_ENCODER);

        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        driveFrontRight.setMode(RUN_USING_ENCODER);

        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackLeft.setMode(RUN_USING_ENCODER);

        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");
        driveBackRight.setMode(RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Up Dpad = Front Left", driveFrontLeft.getCurrentPosition());
            telemetry.addData("Down Dpad = Front Right", driveFrontRight.getCurrentPosition());
            telemetry.addData("Right Dpad = Back Right", driveBackRight.getCurrentPosition());
            telemetry.addData("Left Dpad = Back Left", driveBackLeft.getCurrentPosition());
            telemetry.update();

            if(gamepad1.dpad_up){driveFrontLeft.setPower(0.3);}
            else{driveFrontLeft.setPower(0);}

            if (gamepad1.dpad_down) {driveFrontRight.setPower(0.3);}
            else{driveFrontRight.setPower(0);}

            if (gamepad1.dpad_right) {driveBackRight.setPower(0.3);}
            else{driveBackRight.setPower(0);}

            if (gamepad1.dpad_left) {driveBackLeft.setPower(0.3);}
            else{driveBackLeft.setPower(0);}
        }
    }
}
