package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Auto.BaseAuto;

@TeleOp(name = "Color Prop Test Red")
public class ColorPropTestRed extends BaseAuto {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        while(opModeIsActive()) {
            String propPos = bot.detectPropPosition("red");
            telemetry.addLine("Detecting Red");
            telemetry.addData("Position", propPos);
            telemetry.update();
        }
    }
}