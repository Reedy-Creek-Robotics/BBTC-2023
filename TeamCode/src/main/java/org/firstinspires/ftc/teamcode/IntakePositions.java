package org.firstinspires.ftc.teamcode;

public enum IntakePositions {
    PICKING(10,90),
    LOADING(0,15),
    TRAVELING(100,15),
    BOTTOM(200, 380),
    LINE1(200,850),
    LINE2(200,1800),
    LINE3(200,2650),
    TOP(200,3070);

    int slidePosition;
    int armPosition;

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