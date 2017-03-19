package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

/**
 * Created by James on 2017-03-18.
 */

@Autonomous(name="Main", group="main")
public class Main extends LinearOpMode {

    private Robot robot;

    @Override
    public void runOpMode() {
        robot = new Robot(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            VectorF pose = Tracker.getPose();

            if(pose == null)
                continue;

            double[] powers = Robot.poseToPower(pose);
            robot.setPower(powers[0], powers[1]);

            telemetry.addData("Pose", pose.toString());
            telemetry.addData("Motor Power", "Left: %d%% Right: %d%%", (int) (powers[0] * 100), (int) (powers[1] * 100));
            telemetry.update();
        }
    }
}
