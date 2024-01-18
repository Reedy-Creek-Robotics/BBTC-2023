package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Auto.BaseAuto;

@TeleOp(name = "Color Prop Test Blue")
public class ColorPropTestBlue extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        String propPos = bot.colorDetectionBlue();
        telemetry.addData("Position", propPos);
        telemetry.update();
        while(opModeIsActive());
    }
}
