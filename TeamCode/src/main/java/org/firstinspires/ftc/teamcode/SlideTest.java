package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Slide Test")
public class SlideTest extends LinearOpMode {

    private DcMotor slideMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        slideMotor = hardwareMap.get(DcMotor.class, "slideMotor");

        // Reset the motor encoder so that it reads zero ticks
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Behavior when motor stops
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Program pauses until start button is pressed on driver station
        telemetry.addLine("WAITING FOR START");
        waitForStart();


        // Move Forward
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Total ticks to the destination
        int targetPosition0 = 1000;
        double speed = 0.1;

        // Set distance or tick variable to each motor
        slideMotor.setTargetPosition(targetPosition0);

        // Run motors for
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slideMotor.setPower(speed);

        while(opModeIsActive() && slideMotor.isBusy()) {
            telemetry.addData("slideMotor", slideMotor.getCurrentPosition());
            telemetry.update();
        }

        // Reset the motor encoder so that it reads zero ticks
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Turn the motor back on, required if you use STOP_AND_RESET_ENCODER
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Strafe Right
        slideMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        // Total ticks to the destination
        int targetPosition1 = 1000;

        slideMotor.setTargetPosition(targetPosition1);

        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slideMotor.setPower(speed);

        while(opModeIsActive() && slideMotor.isBusy()) {
            telemetry.addData("slideMotor", slideMotor.getCurrentPosition());
            telemetry.update();
        }

        slideMotor.setPower(0);

    }
}
