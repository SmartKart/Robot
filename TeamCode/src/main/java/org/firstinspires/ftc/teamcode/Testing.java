package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by James on 2017-03-18.
 */

@Autonomous(name="Testing", group="test")
public class Testing extends LinearOpMode {

    @Override
    public void runOpMode() {
        VuforiaWrapper.init(hardwareMap.appContext);

        waitForStart();
    }
}
