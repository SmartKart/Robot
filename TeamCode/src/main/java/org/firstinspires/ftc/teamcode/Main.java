package org.firstinspires.ftc.teamcode;

import android.widget.Toast;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.GPSTracker;
import org.firstinspires.ftc.robotcontroller.internal.MyActivity;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

/**
 * Created by James on 2017-03-18.
 */

@Autonomous(name="Main", group="main")
public class Main extends LinearOpMode {

    private GPSTracker gps;

    @Override
    public void runOpMode() {
        Robot robot = new Robot(hardwareMap);

        // create class object
        gps = new GPSTracker(hardwareMap.appContext);
        sendLocation();

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
    }

    private void sendLocation() {

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(hardwareMap.appContext, "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(hardwareMap.appContext, "Can't get location!", Toast.LENGTH_LONG).show();
        }
    }
}
