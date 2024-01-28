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
            bot.forward(30, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(80, SPEED_DRIVE);
            bot.strafe(50, SPEED_DRIVE, RIGHT);
            bot.turn(5, SPEED_DRIVE, RIGHT);
            bot.runIntake(STAGE1, SPEED_INTAKE);
            bot.forward(13, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-5, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(36, SPEED_DRIVE, LEFT);

        } else if (redPropPos.equals("Left")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            bot.forward(8.5, SPEED_DRIVE);
            bot.runPincher2(PINCHER_2_OPEN);
            bot.forward(-7, SPEED_DRIVE);
            bot.strafe(30, SPEED_DRIVE, RIGHT);
            bot.turn(185, SPEED_DRIVE, RIGHT);
            bot.forward(80, SPEED_DRIVE);
            bot.strafe(20, SPEED_DRIVE, RIGHT);
            bot.runIntake(STAGE1, SPEED_INTAKE);
            bot.forward(11, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-5, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(16, SPEED_DRIVE, LEFT);;

        }else{
            bot.forward(33, SPEED_DRIVE);
            bot.runPincher2(PINCHER_2_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.strafe(16, SPEED_DRIVE, LEFT);
            bot.forward(33,SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(90, SPEED_DRIVE);
            bot.strafe(30, SPEED_DRIVE, RIGHT);
            bot.forward(12, SPEED_DRIVE);
            bot.turn(3, SPEED_DRIVE, LEFT);
            bot.runIntake(STAGE1, SPEED_INTAKE);
            bot.forward(6, 0.2);
            bot.runPincher1(PINCHER_1_OPEN);
            Thread.sleep(1000);
            bot.forward(-4, 0.2);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(24, SPEED_DRIVE, LEFT);
        }
    }
}