package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.modules.Direction.RIGHT;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.BOTTOM;
import static org.firstinspires.ftc.teamcode.modules.IntakePositions.LOADING;
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

        if (bluePropPos.equals("Right")) {
            bot.forward(24, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(10, SPEED_DRIVE); //failing
            bot.runPincher1(PINCHER_1_OPEN);

        } else if (bluePropPos.equals("Left")) {
            bot.strafe(24, SPEED_DRIVE, LEFT);
            bot.forward(27, SPEED_DRIVE);
            bot.turn(90, SPEED_DRIVE, RIGHT);
            bot.forward(12, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);

        }else{
            bot.forward(33.5, SPEED_DRIVE);
            bot.runPincher1(PINCHER_1_OPEN);
            bot.forward(-10, SPEED_DRIVE);
            bot.strafe(18, SPEED_DRIVE, RIGHT);
            bot.forward(30, SPEED_DRIVE);
            bot.strafe(80, SPEED_DRIVE, LEFT);
            bot.turn(90, SPEED_DRIVE, LEFT);

        }
    }
}