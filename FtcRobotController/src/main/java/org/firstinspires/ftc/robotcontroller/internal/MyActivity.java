package org.firstinspires.ftc.robotcontroller.internal;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by James on 2017-03-19.
 */

public class MyActivity extends FtcRobotControllerActivity {
    public GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gps = new GPSTracker(MyActivity.this);
    }
}

