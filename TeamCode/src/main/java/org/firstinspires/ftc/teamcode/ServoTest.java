
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "Concept: Scan Servo")
public class ServoTest extends LinearOpMode {

    Servo pincher1;
    Servo pincher2;


    public void runOpMode() {
        pincher1 = hardwareMap.get(Servo.class, "pincher1");
        pincher2 = hardwareMap.get(Servo.class, "pincher2");


        waitForStart();
        while(opModeIsActive()){

            if(gamepad1.x){
                pincher2.setPosition(0);
            }
            if(gamepad1.y){
                pincher2.setPosition(0.5);
            }
            if(gamepad1.b){
                pincher2.setPosition(1);
            }
            if(gamepad1.dpad_left){
                pincher1.setPosition(0);
            }
            if(gamepad1.dpad_up){
                pincher1.setPosition(0.5);
            }
            if(gamepad1.dpad_right){
                pincher1.setPosition(1);
            }
            telemetry.addData("pincher1", pincher1.getPosition());
            telemetry.addData("pincher2", pincher2.getPosition());
            telemetry.update();
        }
    }

}
