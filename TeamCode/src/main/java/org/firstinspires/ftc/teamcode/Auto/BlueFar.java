package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Direction.RIGHT;
import static org.firstinspires.ftc.teamcode.modules.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.modules.Robot.PINCHER_1_OPEN;
import static org.firstinspires.ftc.teamcode.modules.Robot.PINCHER_2_OPEN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.BaseAuto;

@Autonomous(name = "Blue Far")
public class BlueFar extends BaseAuto {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        telemetry.addData("Blue Prop Pos", bluePropPos);
        telemetry.update();

        if (bluePropPos.equals("Left")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            bot.forward(10, SPEED_DRIVE);
            bot.runPincher2(PINCHER_2_OPEN);
            bot.forward(-8, SPEED_DRIVE);
            bot.strafe(30, SPEED_DRIVE, RIGHT);
            bot.turn(3, SPEED_DRIVE, RIGHT);
            Thread.sleep(4000);
            bot.forward(80, SPEED_DRIVE);
            bot.strafe(35, SPEED_DRIVE, LEFT);
            bot.turn(3, SPEED_DRIVE, LEFT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            super.waitIntake();
            bot.forward(13, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-5, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            super.waitIntake();
            bot.strafe(28, SPEED_DRIVE, RIGHT);

        } else if (bluePropPos.equals("Right")) {
            bot.forward(28, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(9, SPEED_DRIVE);
            bot.runPincher2(PINCHER_2_OPEN);
            bot.forward(-7, SPEED_DRIVE);
            bot.strafe(20, SPEED_DRIVE, LEFT);
            bot.turn(182, SPEED_DRIVE, LEFT);
            Thread.sleep(4000);
            bot.forward(80, SPEED_DRIVE);
            bot.strafe(20 , SPEED_DRIVE, LEFT);
            bot.turn(3, SPEED_DRIVE, LEFT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            super.waitIntake();
            bot.forward(9, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-5, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            super.waitIntake();
            bot.strafe(18, SPEED_DRIVE, RIGHT);

        }else{
            bot.forward(34, SPEED_DRIVE);
            bot.runPincher2(PINCHER_2_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.strafe(16, SPEED_DRIVE, RIGHT);
            bot.forward(31,SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            Thread.sleep(4000);
            bot.forward(90, SPEED_DRIVE);
            bot.strafe(25 , SPEED_DRIVE, LEFT);
            bot.forward(12, SPEED_DRIVE);
            bot.turn(3, SPEED_DRIVE, RIGHT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            super.waitIntake();
            bot.forward(6, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-4, 0.2);
            bot.runIntake(LOADING, SPEED_INTAKE);
            super.waitIntake();
            bot.strafe(18, SPEED_DRIVE, RIGHT);
        }
    }
}