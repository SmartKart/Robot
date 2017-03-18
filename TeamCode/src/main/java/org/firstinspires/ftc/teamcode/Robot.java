package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by James on 2017-03-18.
 */

public class Robot extends Hardware {

    Robot(HardwareMap hw) {
        super(hw);

        VuforiaWrapper.init(hw.appContext);
    }


}
