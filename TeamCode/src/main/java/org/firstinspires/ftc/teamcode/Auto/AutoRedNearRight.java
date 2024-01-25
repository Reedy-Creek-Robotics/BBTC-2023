package org.firstinspires.ftc.teamcode.Auto;

import static org.firstinspires.ftc.teamcode.modules.Direction.LEFT;
import static org.firstinspires.ftc.teamcode.modules.Direction.RIGHT;
import static org.firstinspires.ftc.teamcode.modules.Robot.PINCHER_1_OPEN;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Red Near Right")
public class AutoRedNearRight extends BaseAuto {

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
        }
    }
}
