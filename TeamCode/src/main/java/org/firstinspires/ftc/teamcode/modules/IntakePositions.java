package org.firstinspires.ftc.teamcode.modules;

public enum IntakePositions {
    PICKING(0,0), // rotation arm all the way, slides all the way down
    LOADING(10,90), // arm position to pick up pixel, slides are slightly up above pixel
    TRAVELING(100,0), // arm positioned out from robot, slides all the way down
    BOTTOM(250, 500), // arm is aiming for backdrop, slides extended below bottom white line
    LINE1(250,850), // Same as above, except above line 1
    LINE2(250,1800), // Same as above, except above line 2
    LINE3(250,2650), // Same as above, except above line 3
    TOP(250,3070); // Same as above, except highest can go

    public int slidePosition;
    public int armPosition;

    public int getSlidePosition() {
        return slidePosition;
    }

    public int getArmPosition() {
        return armPosition;
    }
    IntakePositions(int armPosition, int slidePosition) {
        this.slidePosition = slidePosition;
        this.armPosition = armPosition;
        
    }
}