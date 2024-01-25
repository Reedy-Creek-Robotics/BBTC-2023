package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.modules.BaseAuto;

@TeleOp(name = "Color Prop Test Blue")
@Disabled
public class ColorPropTestBlue extends BaseAuto {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        while(opModeIsActive()) {
            String propPos = bot.detectPropPosition("blue");
            telemetry.addLine("Detecting Blue");
            telemetry.addData("Position", propPos);
            telemetry.update();
            }
        }
    }
