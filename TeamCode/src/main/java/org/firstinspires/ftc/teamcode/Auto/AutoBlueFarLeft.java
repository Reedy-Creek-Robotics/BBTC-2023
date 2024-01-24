package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Robot.*;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.modules.Direction.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Blue Far Left", preselectTeleOp = "TeleOpDrive")
public class AutoBlueFarLeft extends BaseAuto {

    private static final double SPEED_INTAKE = 0.5;
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        /*
        Find team prop
         */
        bot.runIntake(LOADING, SPEED_INTAKE);
        bot.waitIntake();

        String propPos = bot.detectPropPosition("blue");


        bot.forward(24, 0.3);
        bot.waitDrive();

        if (propPos.equals("right")) {
            bot.turn(90, 0.3, RIGHT);
            bot.waitDrive();
            bot.forward(12, 0.3);
            bot.waitDrive();
            bot.runIntake(PICKING, SPEED_INTAKE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.waitIntake();
            bot.forward(-12, 0.3);
            bot.waitDrive();
            bot.turn(90, 0.3, LEFT);
            bot.waitDrive();

        } else if (propPos.equals("left")) {
            bot.forward(24, 0.3);
            bot.waitDrive();
            bot.turn(90, 0.3, LEFT);
            bot.waitDrive();
            bot.forward(12, 0.3);
            bot.waitDrive();
            bot.runIntake(PICKING, SPEED_INTAKE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.waitIntake();
            bot.forward(-12, 0.3);
            bot.waitDrive();
            bot.turn(90, 0.3, RIGHT);
            bot.waitDrive();
        }else{
            bot.forward(12, 0.3);
            bot.waitDrive();
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.waitIntake();
            bot.runPincher1(PINCHER_1_OPEN);
            bot.runIntake(TRAVELING, SPEED_INTAKE);
            bot.waitIntake();
            bot.forward(-12, 0.3);
            bot.waitDrive();
        }
    }
}