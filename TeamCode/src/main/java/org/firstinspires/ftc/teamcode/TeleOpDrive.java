package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp Driving")
public class TeleOpDrive extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        // INIT
        // all code between here and WAIT runs when the INIT button is pressed on the driver station
        // this is where you initialize all the hardware and code for your program
        // the robot should NOT move in this part of the program (its a penalty)

        // WAIT
        // the program pauses here until the START button is pressed on the driver station
        telemetry.addLine("WAITING FOR START");
        waitForStart();

        // START / RUN
        // all code after here runs when the START button is pressed on the driver station
        // this is where all your code to move the robot goes
        // once all instructions are executed the program will exit
        telemetry.addLine("PROGRAM STARTED");
        while( !isStopRequested() ) {
            // this is your EVENT loop (like a video game loop)
            // these instructions will be executed over and over until STOP is pressed on the driver station
            // you will listen for gamepad and other input commands in this loop
            telemetry.addLine("EVENT LOOP");
        }
    }
}
