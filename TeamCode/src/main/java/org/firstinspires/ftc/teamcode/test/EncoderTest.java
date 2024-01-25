package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
@Disabled
public class EncoderTest extends LinearOpMode {
    DcMotor intakeArm;
    DcMotor intakeSlide1;
    DcMotor intakeSlide2;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        intakeArm = hardwareMap.get(DcMotor.class, "intakeArm");
        intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide1 = hardwareMap.get(DcMotor.class, "intakeSlide1");
        intakeSlide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide2 = hardwareMap.get(DcMotor.class, "intakeSlide2");
        intakeSlide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while(opModeIsActive()){
            telemetry.addData("Arm", intakeArm.getCurrentPosition());
            telemetry.addData("Slide 1", intakeSlide1.getCurrentPosition());
            telemetry.addData("Slide 2", intakeSlide2.getCurrentPosition());
            telemetry.update();
        }
    }
}
