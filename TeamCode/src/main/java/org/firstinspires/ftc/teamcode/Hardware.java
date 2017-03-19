package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by James on 2017-03-18.
 */

public class Hardware {

    DcMotor leftMotor;
    DcMotor rightMotor;

    protected double baseSpeed = 0;
    protected double angularSpeed = 0;

    Hardware(HardwareMap hwMap) {
        leftMotor = (DcMotor) hwMap.get("left");
        rightMotor = (DcMotor) hwMap.get("right");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //Use baseSpeed and angular speed
    void setPower() {
        double lPower = baseSpeed + angularSpeed;
        double rPower = baseSpeed - angularSpeed;

        setPower(lPower, rPower);
    }
    void setPower(double lpower, double rpower) {

        setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotor.setPower(bound(lpower, -1, 1));
        rightMotor.setPower(bound(rpower, -1, 1));
    }

    void setBaseSpeed(double speed) {
        baseSpeed = speed;
    }

    void setAngularSpeed(double angular) {
        angularSpeed = angular;
    }

    void stop() {
        setMotorMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void setMotorMode(DcMotor.RunMode targetMode) {
        if(leftMotor.getMode() != targetMode)
            leftMotor.setMode(targetMode);

        if(rightMotor.getMode() != targetMode)
            rightMotor.setMode(targetMode);
    }

    static double bound(double value, double lower, double upper) {
        if(value < lower)
            return lower;
        else if (value > upper)
            return upper;
        else
            return value;
    }
}
