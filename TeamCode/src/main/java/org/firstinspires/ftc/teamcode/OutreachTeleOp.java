package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class OutreachTeleOp extends LinearOpMode {
    DcMotor driveFrontLeft;
    DcMotor driveFrontRight;
    DcMotor driveBackLeft;
    DcMotor driveBackRight;

    Gamepad currentGamepad1 = new Gamepad();
    Gamepad currentGamepad2 = new Gamepad();
    Gamepad previousGamepad1 = new Gamepad();
    Gamepad previousGamepad2 = new Gamepad();
    @Override
    public void runOpMode() throws InterruptedException {
        initHardware();

        while(opModeIsActive()){

            gamepadUpdates();
            processDriving();
            
        }
    }

    private void gamepadUpdates(){
        gamepad1.copy(currentGamepad1);
        gamepad2.copy(currentGamepad2);
        previousGamepad1.copy(previousGamepad1);
        previousGamepad2.copy(previousGamepad2);
    }

    private void initHardware(){
        driveFrontLeft = hardwareMap.get(DcMotor.class, "driveFrontLeft");
        driveFrontRight = hardwareMap.get(DcMotor.class, "driveFrontRight");
        driveBackLeft = hardwareMap.get(DcMotor.class, "driveBackLeft");
        driveBackRight = hardwareMap.get(DcMotor.class, "driveBackRight");

        driveFrontLeft.setDirection(REVERSE);
        driveBackLeft.setDirection(REVERSE);
    }

    private void processDriving(){
        double lx = gamepad1.left_stick_x;
        double ly = -gamepad1.left_stick_y;
        double rx = gamepad2.right_stick_x;

        double denominator = Math.max(Math.abs(ly) + Math.abs(lx) + Math.abs(rx), 1);
        double frontLeftPower = (ly + lx + rx) / denominator;
        double backLeftPower = (ly - lx + rx) / denominator;
        double frontRightPower = (ly - lx - rx) / denominator;
        double backRightPower = (ly + lx - rx) / denominator;

        driveFrontLeft.setPower(frontLeftPower);
        driveBackLeft.setPower(backLeftPower);
        driveFrontRight.setPower(frontRightPower);
        driveBackRight.setPower(backRightPower);
    }
}
