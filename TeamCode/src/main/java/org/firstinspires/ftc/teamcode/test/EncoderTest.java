package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class EncoderTest extends LinearOpMode {
    DcMotor intakeArm;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intakeArm.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeArm.setTargetPosition(300);
        intakeArm.setPower(-.3);
        intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while(opModeIsActive()){
            telemetry.addData("Encoder Value", intakeArm.getCurrentPosition());
            telemetry.update();
        }
    }
}
