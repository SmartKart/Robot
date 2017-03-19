package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by James on 2017-03-18.
 */

public class Robot extends Hardware {

    Robot(HardwareMap hw) {
        super(hw);

        VuforiaWrapper.init(hw.appContext);
    }

    void setError(VectorF error) {
        double[] speeds = poseToSpeed(error);

        setBaseSpeed(speeds[0]);
        setAngularSpeed(speeds[1]);
        setPower();
    }

    static double[] poseToSpeed(VectorF pose) {
        //Set angular to correct x
        float x = pose.get(0);
        float z = pose.get(2);

        double angular = Math.signum(x) * (Math.abs(x) / Math.abs(z) * Controller.P_A);

        /*if(Math.abs(angular) < 0.05)
            angular = 0;*/

        angular = Robot.bound(angular, -0.5, 0.5);

        //Set basespeed to correct z
        double base = (Math.abs(z / Controller.TARGET_Z) - 1) * Controller.P_Z;

        /*if(Math.abs(base) < 0.05)
            base = 0;*/

        base = Robot.bound(base, -0.5, 0.5);

        return new double[] { base, angular };
    }

    static double[] speedToPower(double[] speeds) {
        return new double[] { speeds[0] + speeds[1], speeds[0] - speeds[1] };
    }
}
