package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Auto.BaseAuto;

@TeleOp(name = "Color Prop Test Blue")
public class ColorPropTestBlue extends BaseAuto {
    String colorToDetect = "blue";
    int timer = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        while(opModeIsActive()) {
            String propPos = bot.detectPropPosition(colorToDetect);
            telemetry.addData("Color To Detect", colorToDetect);
            telemetry.addData("Position", propPos);
            telemetry.update();


            

            sleep(100);
            timer += 1;

            if(timer > 100){
                colorToDetect = "red";
            }
        }
    }
}
