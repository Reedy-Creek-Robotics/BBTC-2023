package org.firstinspires.ftc.teamcode.modules;

public enum IntakePositions {
    PICKING(0,0),
    LOADING(10,50),
    STAGE0(300, 50),
    STAGE1(300, 400),
    STAGE2(300, 800),
    STAGE3(300,1200),
    STAGE4(300, 1600),
    PRIME_HANGING(2, 1800),
    HANGING(2, 1300);


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