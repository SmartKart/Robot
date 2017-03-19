package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Controller;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcontroller.internal.GPSTracker;
import org.firstinspires.ftc.robotcontroller.internal.MyActivity;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

/**
 * Created by James on 2017-03-18.
 */

@Autonomous(name="Testing", group="test")
public class Testing extends LinearOpMode {

    @Override
    public void runOpMode() {

        VuforiaWrapper.init(hardwareMap.appContext);
        Controller.readControllerParameters(hardwareMap.appContext);

        // create class object
        sendLocation();

        waitForStart();

        while (opModeIsActive()) {
            VectorF pose = Tracker.getPose();

            if (pose == null) {
                telemetry.addData("Status", "Not tracking");
                telemetry.update();
                continue;
            }

            double[] powers = Robot.speedToPower(Robot.poseToSpeed(pose));

            telemetry.addData("Pose", pose.toString());
            telemetry.addData("Motor Power", "Left: %d%% Right: %d%%", (int) (powers[0] * 100), (int) (powers[1] * 100));
            telemetry.update();

            sleep(200);
        }
    }

    private void sendLocation() {

        GPSTracker gps = ((MyActivity) hardwareMap.appContext).gps;

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            makeToast("Your Location is - \nLat: " + latitude + "\nLong: " + longitude);
        }
        else {
            makeToast("Can't get location!");
        }
    }

    private void makeToast(final String text) {
        final FtcRobotControllerActivity activity = (FtcRobotControllerActivity) hardwareMap.appContext;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
