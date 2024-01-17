package org.firstinspires.ftc.teamcode;

public enum IntakePositions {
    LOADING(41,33),
    PICKING(33,33),
    TRAVELING(137,33),
    LINE1(295,848),
    LINE2(295,1769),
    LINE3(295,2585),
    TOP(295,3010);

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