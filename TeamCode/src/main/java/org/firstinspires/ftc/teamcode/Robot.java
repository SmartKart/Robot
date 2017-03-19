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

    static double[] poseToPower(VectorF pose) {
        double left = 0;
        double right = 0;

        float x = pose.get(0);
        float z = pose.get(2);

        left = Math.signum(x) * (Math.abs(x) / Math.abs(z) * 0.3);

        if(Math.abs(left) < 0.05)
            left = 0;

        return new double[] {left, -left};
    }
}
