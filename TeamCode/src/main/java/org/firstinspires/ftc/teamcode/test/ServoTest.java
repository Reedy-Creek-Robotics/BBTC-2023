package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Servo Test")
@Disabled
public class ServoTest extends LinearOpMode {
    Servo pincher1;
    Servo pincher2;
    ElapsedTime pincher1Debounce;
    ElapsedTime pincher2Debounce;
    double pincher1Pos = 0;
    double pincher2Pos = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        pincher1 = hardwareMap.get(Servo.class, "pincher1");
        pincher2 = hardwareMap.get(Servo.class, "pincher2");

        pincher1Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        pincher2Debounce = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        waitForStart();

        pincher1.setPosition(pincher1Pos);
        pincher2.setPosition(pincher2Pos);

        while(opModeIsActive()){
            if(gamepad1.dpad_up && pincher1Debounce.milliseconds() > 200){
                pincher1Pos = (pincher1Pos += 0.1);
                pincher1Debounce.reset();
            }

            if(gamepad1.dpad_down && pincher1Debounce.milliseconds() > 200){
                pincher1Pos = (pincher1Pos -= 0.1);
                pincher1Debounce.reset();
            }

            if(gamepad1.dpad_right && pincher2Debounce.milliseconds() > 200){
                pincher2Pos = (pincher2Pos += 0.1);
                pincher2Debounce.reset();
            }

            if(gamepad1.dpad_left && pincher2Debounce.milliseconds() > 200){
                pincher2Pos = (pincher2Pos -= 0.1);
                pincher2Debounce.reset();
            }


            if(pincher1Pos > 1){pincher1Pos=1;}
            else if(pincher1Pos < 0){pincher1Pos=0;}

            if(pincher2Pos>1){pincher2Pos=1;}
            else if(pincher2Pos<0){pincher2Pos=0;}


            pincher1.setPosition(pincher1Pos);
            pincher2.setPosition(pincher2Pos);

            telemetry.addData("Pincher 1", pincher1Pos);
            telemetry.addData("Pincher 2", pincher2Pos);
            telemetry.update();
        }
    }
}
