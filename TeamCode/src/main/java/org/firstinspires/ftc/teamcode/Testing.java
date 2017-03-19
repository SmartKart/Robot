package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

/**
 * Created by James on 2017-03-18.
 */

@Autonomous(name="Testing", group="test")
public class Testing extends LinearOpMode {

    @Override
    public void runOpMode() {
        VuforiaWrapper.init(hardwareMap.appContext);

        waitForStart();

        while(opModeIsActive()) {
            VectorF pose = Tracker.getPose();

            if(pose == null)
                continue;

            double[] powers = Robot.poseToPower(pose);

            telemetry.addData("Pose", pose.toString());
            telemetry.addData("Motor Power", "Left: %d%% Right: %d%%", (int) (powers[0] * 100), (int) (powers[1] * 100));
            telemetry.update();

            sleep(200);
        }
    }
}
