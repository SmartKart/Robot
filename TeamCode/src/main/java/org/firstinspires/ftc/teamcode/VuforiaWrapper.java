package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by James on 2017-03-18.
 */

final class VuforiaWrapper {

    static VuforiaLocalizer mInstance;

    static void init(Context ctx) {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters();
        params.cameraMonitorViewIdParent = R.id.cameraMonitorViewId;
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        params.vuforiaLicenseKey = ctx.getResources().getString(R.string.vuforia_license_key);

        mInstance = new VuforiaLocalizerImpl2(params);

        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        Tracker.init();
        FrameExtractor.init();
    }
}
