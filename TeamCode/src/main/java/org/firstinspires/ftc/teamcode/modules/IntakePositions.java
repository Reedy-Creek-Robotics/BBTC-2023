package org.firstinspires.ftc.teamcode.modules;

public enum IntakePositions {
    PICKING(2,0),
    LOADING(10,50),
    STAGE0(250, 100),
    STAGE1(250, 400),
    STAGE2(250, 800),
    STAGE3(250,1200),
    STAGE4(250, 1600),
    STAGE5(250,2000),
    STAGE6(250,2400),
    STAGE7(250, 2800),
    STAGE8(250, 3000),
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