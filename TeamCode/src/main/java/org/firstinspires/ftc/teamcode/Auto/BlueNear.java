package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.modules.Direction.RIGHT;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.modules.Robot.PINCHER_1_OPEN;
import static org.firstinspires.ftc.teamcode.modules.Robot.PINCHER_2_OPEN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.BaseAuto;

@Autonomous(name = "Blue Near")
public class BlueNear extends BaseAuto {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        telemetry.addData("Blue Prop Pos", bluePropPos);
        telemetry.update();

        if (bluePropPos.equals("Right")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(10, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-33, SPEED_DRIVE);
            bot.turn(180, SPEED_DRIVE, LEFT);
            bot.forward(10, SPEED_DRIVE);
            bot.strafe(5, SPEED_DRIVE, RIGHT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            bot.forward(9, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-7, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(30, SPEED_DRIVE, LEFT);

        } else if (bluePropPos.equals("Left")) {
            bot.strafe(24, SPEED_DRIVE, LEFT);
            bot.forward(27, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(10 , SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.turn(180, SPEED_DRIVE, LEFT);
            bot.forward(15, SPEED_DRIVE);
            bot.strafe(8 , SPEED_DRIVE, LEFT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            bot.forward(4, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-7, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(16, SPEED_DRIVE, LEFT);

        }else{
            bot.forward(35, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            bot.forward(32, SPEED_INTAKE);
            bot.strafe(5, SPEED_DRIVE, RIGHT);
            bot.turn(6, SPEED_DRIVE, RIGHT);
            bot.runIntake(STAGE0, SPEED_INTAKE);
            bot.forward(11, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-4, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(25, SPEED_DRIVE, LEFT);
        }
    }
}