package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Robot.*;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.modules.Direction.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.BaseAuto;

@Autonomous(name = "Red Far", preselectTeleOp = "TeleOpDrive")
public class RedFar extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        telemetry.addData("Red Prop Pos", redPropPos);
        telemetry.update();

        if (redPropPos.equals("Right")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(45, SPEED_DRIVE, RIGHT);
            bot.forward(10, SPEED_DRIVE);
            bot.runPincher2(PINCHER_2_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.turn(45, SPEED_DRIVE, LEFT);
            bot.forward(28, SPEED_DRIVE);
            bot.turn(94, SPEED_DRIVE, RIGHT);
            Thread.sleep(4000);
            bot.forward(80, SPEED_DRIVE);
            bot.strafe(45, SPEED_DRIVE, RIGHT);
            bot.turn(5, SPEED_DRIVE, LEFT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            super.waitIntake();
            bot.forward(13, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-5, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            super.waitIntake();
            bot.strafe(36, SPEED_DRIVE, LEFT);

        } else if (redPropPos.equals("Left")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            bot.forward(9.5, SPEED_DRIVE);
            bot.runPincher2(PINCHER_2_OPEN);
            bot.forward(-7, SPEED_DRIVE);
            bot.strafe(24, SPEED_DRIVE, RIGHT);
            bot.turn(183, SPEED_DRIVE, RIGHT);
            Thread.sleep(4000);
            bot.forward(80, SPEED_DRIVE);
            bot.strafe(22, SPEED_DRIVE, RIGHT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            super.waitIntake();
            bot.forward(13, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-5, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            super.waitIntake();
            bot.strafe(18, SPEED_DRIVE, LEFT);;

        }else{
            bot.forward(33, SPEED_DRIVE);
            bot.runPincher2(PINCHER_2_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.strafe(16, SPEED_DRIVE, LEFT);
            bot.forward(28,SPEED_DRIVE);
            bot.turn(86, SPEED_DRIVE, RIGHT);
            Thread.sleep(4000);
            bot.forward(90, SPEED_DRIVE);
            bot.strafe(36, SPEED_DRIVE, RIGHT);
            bot.forward(12, SPEED_DRIVE);
            bot.turn(3, SPEED_DRIVE, LEFT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            super.waitIntake();
            bot.forward(6, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-4, 0.2);
            bot.runIntake(LOADING, SPEED_INTAKE);
            super.waitIntake();
            bot.strafe(28, SPEED_DRIVE, LEFT);
        }
    }
}