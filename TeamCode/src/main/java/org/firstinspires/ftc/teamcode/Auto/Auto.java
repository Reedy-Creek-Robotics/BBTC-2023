package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Robot.*;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.modules.Direction.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto", preselectTeleOp = "TeleOpDrive")
public class Auto extends BaseAuto {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        String propPos = bot.detectPropPosition("blue");

        telemetry.addData("Prop Pos", propPos);
        telemetry.update();

        if (propPos.equals("Right")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(10, SPEED_DRIVE); //failing
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-33, SPEED_DRIVE);
            bot.turn(180, SPEED_DRIVE, LEFT);
            bot.forward(10, SPEED_DRIVE);
            bot.strafe(5, SPEED_DRIVE, RIGHT);
            bot.runIntake(BOTTOM, SPEED_INTAKE);
            bot.forward(6, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-7, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(30, SPEED_DRIVE, LEFT);
            bot.forward(16, SPEED_DRIVE);

        } else if (propPos.equals("Left")) {
            bot.strafe(24, SPEED_DRIVE, LEFT);
            bot.forward(27, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(12, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.turn(180, SPEED_DRIVE, LEFT);
            bot.forward(15, SPEED_DRIVE);
            bot.strafe(12 , SPEED_DRIVE, LEFT);
            bot.runIntake(BOTTOM, SPEED_INTAKE);
            bot.forward(3, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-7, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(16, SPEED_DRIVE, LEFT);
            bot.forward(16, SPEED_DRIVE);

        }else{
            bot.forward(33.5, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            bot.forward(32, SPEED_INTAKE);
            bot.strafe(5, SPEED_DRIVE, RIGHT);
            bot.turn(3, SPEED_DRIVE, RIGHT);
            bot.runIntake(BOTTOM, SPEED_INTAKE);
            bot.forward(9, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-4, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(25, SPEED_DRIVE, LEFT);
            bot.forward(16, SPEED_DRIVE);
        }
    }
}