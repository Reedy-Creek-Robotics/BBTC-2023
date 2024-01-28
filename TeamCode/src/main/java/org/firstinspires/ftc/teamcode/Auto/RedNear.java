package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Direction.RIGHT;
import static org.firstinspires.ftc.teamcode.modules.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.*;
import static org.firstinspires.ftc.teamcode.modules.Robot.PINCHER_1_OPEN;
import static org.firstinspires.ftc.teamcode.modules.Robot.PINCHER_2_OPEN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.modules.BaseAuto;

@Autonomous(name = "Red Near")
public class RedNear extends BaseAuto {

    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        telemetry.addData("Red Prop Pos", redPropPos);
        telemetry.update();
        
        if (redPropPos.equals("Left")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            bot.forward(10, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-33, SPEED_DRIVE);
            bot.turn(180, SPEED_DRIVE, RIGHT);
            bot.forward(10, SPEED_DRIVE);
            bot.strafe(13, SPEED_DRIVE, LEFT);
            bot.turn(5, SPEED_DRIVE, LEFT);
            bot.runIntake(STAGE1, SPEED_INTAKE);
            bot.forward(3, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-7, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(40, SPEED_DRIVE, RIGHT);

        } else if (redPropPos.equals("Right")) {
            bot.strafe(24, SPEED_DRIVE, RIGHT);
            bot.forward(27, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, LEFT);
            bot.forward(10, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.turn(180, SPEED_DRIVE, RIGHT);
            bot.forward(15, SPEED_DRIVE);
            bot.strafe(6 , SPEED_DRIVE, RIGHT);
            bot.turn(5,SPEED_DRIVE,LEFT);
            bot.runIntake(STAGE1, SPEED_INTAKE);
            bot.forward(2, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-7, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(23, SPEED_DRIVE, RIGHT);

        }else{
            bot.forward(34, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(32, SPEED_INTAKE);
            bot.strafe(15, SPEED_DRIVE, LEFT);
            bot.turn(7, SPEED_DRIVE, LEFT);
            bot.runIntake(STAGE1, SPEED_INTAKE);
            bot.forward(9, 0.2);
            bot.runPincher2(PINCHER_2_OPEN);
            Thread.sleep(1000);
            bot.forward(-4, SPEED_DRIVE);
            bot.runIntake(LOADING, SPEED_INTAKE);
            bot.strafe(30 , SPEED_DRIVE, RIGHT);
        }
    }
}