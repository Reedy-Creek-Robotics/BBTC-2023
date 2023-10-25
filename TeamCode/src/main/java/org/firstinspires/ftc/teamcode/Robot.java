package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Disabled
public class Robot {

    private DcMotor driveFrontLeft;
    private DcMotor driveFrontRight;
    private DcMotor driveBackRight;
    private DcMotor driveBackLeft;


    public Robot(DcMotor driveFrontLeft, DcMotor driveBackLeft, DcMotor driveBackRight, DcMotor driveFrontRight){

        this.driveFrontLeft = driveFrontLeft;
        this.driveFrontRight = driveFrontRight;
        this.driveBackRight = driveBackRight;
        this.driveBackLeft = driveBackLeft;
    }

    public void forward(int distance, double speed){
        setup(distance);

        // Move Forward
        driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set distance or tick variable to each motor
        driveFrontLeft.setTargetPosition(distance);
        driveFrontRight.setTargetPosition(distance);
        driveBackLeft.setTargetPosition(distance);
        driveBackRight.setTargetPosition(distance);
        // Run motors for
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveFrontLeft.setPower(speed);
        driveFrontRight.setPower(speed);
        driveBackLeft.setPower(speed);
        driveBackRight.setPower(speed);

        // Run motors for
        //driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void strafe(int distance, double speed, Direction direction) {
        setup(distance);


        switch(direction){
            case LEFT:
                // Strafe Left
                driveFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                driveFrontRight.setDirection(DcMotorSimple.Direction.FORWARD);
                driveBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                driveBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

                // Set distance or tick variable to each motor
                driveFrontLeft.setTargetPosition(distance);
                driveFrontRight.setTargetPosition(distance);
                driveBackLeft.setTargetPosition(distance);
                driveBackRight.setTargetPosition(distance);
                break;
            case RIGHT:
                // Strafe Right
                driveFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
                driveFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
                driveBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
                driveBackRight.setDirection(DcMotorSimple.Direction.FORWARD);

                // Set distance or tick variable to each motor
                driveFrontLeft.setTargetPosition(distance);
                driveFrontRight.setTargetPosition(distance);
                driveBackLeft.setTargetPosition(distance);
                driveBackRight.setTargetPosition(distance);
                break;
        }

        driveFrontLeft.setPower(speed);
        driveFrontRight.setPower(speed);
        driveBackLeft.setPower(speed);
        driveBackRight.setPower(speed);

        // Run motors for
        driveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    // May be able to delete distance below, goes unused in this method
    private void setup(int distance){
        // Reset the motor encoder so that it reads zero ticks
        driveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Behavior when motor stops
        driveFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

}


