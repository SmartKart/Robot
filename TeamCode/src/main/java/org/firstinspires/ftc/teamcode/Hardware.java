package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by James on 2017-03-18.
 */

public class Hardware {

    protected DcMotor leftMotor;
    protected DcMotor rightMotor;

    Hardware(HardwareMap hwMap) {
        leftMotor = (DcMotor) hwMap.get("left");
        rightMotor = (DcMotor) hwMap.get("right");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    void setPower(double lpower, double rpower) {
        leftMotor.setPower(bound(lpower, -1, 1));
        rightMotor.setPower(bound(rpower, -1, 1));
    }

    void stop() {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    private double bound(double value, double lower, double upper) {
        if(value < lower)
            return lower;
        else if (value > upper)
            return upper;
        else
            return value;
    }
}
