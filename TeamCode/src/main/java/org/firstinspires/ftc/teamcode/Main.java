package org.firstinspires.ftc.teamcode;

import android.widget.Toast;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Controller;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcontroller.internal.GPSTracker;
import org.firstinspires.ftc.robotcontroller.internal.MyActivity;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by James on 2017-03-18.
 */

@Autonomous(name="Main", group="main")
public class Main extends LinearOpMode {

    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap);

        Controller.readControllerParameters(hardwareMap.appContext);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask() {
            public void run() {
                sendLocation();
            }
        }, 0, 5000);

        waitForStart();

        while(opModeIsActive()) {
            VectorF pose = Tracker.getPose();

            if(pose == null) {
                robot.setPower(0, 0);
                telemetry.addData("Status", "Not tracking");
                telemetry.update();
                continue;
            }

            robot.setError(pose);

            telemetry.addData("Pose", pose.toString());
            telemetry.addData("Motor Power", "Left: %d%% Right: %d%%", (int) (robot.leftMotor.getPower() * 100), (int) (robot.rightMotor.getPower() * 100));
            telemetry.update();

            idle();
        }

        Tracker.close();
        timer.cancel();
    }

    private void sendLocation() {
        MyActivity activity = (MyActivity) hardwareMap.appContext;
        GPSTracker gps = activity.gps;

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            activity.updateLocation(latitude, longitude);

            // \n is for new line
            //makeToast("Your Location is - \nLat: " + latitude + "\nLong: " + longitude);
        }
        else {
            //makeToast("Can't get location!");
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
