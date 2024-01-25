package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Robot.*;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.modules.Direction.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Blue Far Left", preselectTeleOp = "TeleOpDrive")
public class AutoBlueFarLeft extends BaseAuto {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        String propPos = bot.detectPropPosition("red");

        telemetry.addData("Prop Pos", propPos);
        telemetry.update();

        if (propPos.equals("Right")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(7, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);

        } else if (propPos.equals("Left")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            bot.forward(7, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);

        }else{
            bot.forward(33, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.strafe(16, SPEED_DRIVE, LEFT);
            bot.forward(33,SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(90, SPEED_DRIVE);
            bot.strafe(27, SPEED_DRIVE, RIGHT);
            bot.forward(12, SPEED_DRIVE);
            bot.turn(3, SPEED_DRIVE, LEFT);
            bot.runIntake(BOTTOM, SPEED_INTAKE);
            bot.forward(4, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-4, 0.2);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(24, SPEED_DRIVE, LEFT);
            bot.forward(16, SPEED_DRIVE);
        }
    }
}