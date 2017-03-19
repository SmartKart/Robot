package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by James on 2017-03-18.
 */

class Tracker {
    //TODO: Add multithreading

    static VuforiaTrackables mMarkers;
    private static VectorF pose = null;
    private static TrackerWorker worker = null;

    static void init() {
        mMarkers = VuforiaWrapper.mInstance.loadTrackablesFromAsset("FTC_2016-17");

        mMarkers.get(0).setName("Wheels");
        mMarkers.get(1).setName("Tools");
        mMarkers.get(2).setName("Legos");
        mMarkers.get(3).setName("Gears");

        mMarkers.activate();

        if(worker != null)
            worker.interrupt();

        worker = new TrackerWorker();
        worker.setDaemon(true);
        worker.setPriority(10);
        worker.start();
    }

    static void close() {
        worker.interrupt();
        worker = null;
    }

    static synchronized VectorF getPose() {
        return pose;
    }

    private static VectorF getEulerRotation(OpenGLMatrix pose) {
        double heading, pitch, roll;

        // Assuming the angles are in radians.
        if (pose.get(1, 0) > 0.998) { // singularity at north pole
            heading = Math.atan2(pose.get(0, 2), pose.get(2, 2));
            pitch = Math.PI/2;
            roll = 0;
        }
        else if (pose.get(1, 0) < -0.998) { // singularity at south pole
            heading = Math.atan2(pose.get(0, 2), pose.get(2, 2));
            pitch = -Math.PI/2;
            roll = 0;
        }
        else {
            heading = Math.atan2(pose.get(2, 0), pose.get(0, 0));
            pitch = Math.atan2(-pose.get(1, 2), pose.get(1, 1));
            roll = Math.asin(pose.get(1, 0));
        }

        return new VectorF((float) Math.toDegrees(roll), (float) Math.toDegrees(heading), (float) Math.toDegrees(pitch));
    }

    private static class TrackerWorker extends Thread {

        VuforiaTrackableDefaultListener lastListener = null;
        int noTrackCount = 0;

        @Override
        public void run() {
            while(!Thread.interrupted()) {

                if(lastListener != null && lastListener.getPose() != null) {
                    processPose(lastListener.getPose());
                }
                else {
                    for (VuforiaTrackable m : mMarkers) {
                        VuforiaTrackableDefaultListener l = (VuforiaTrackableDefaultListener) m.getListener();

                        if (l.isVisible()){
                            processPose(l.getPose());
                            lastListener = l;
                            break;
                        }
                    }

                    noTrackCount++;

                    if(noTrackCount > 5) {
                        synchronized (Tracker.class) {
                            pose = null;
                        }

                        noTrackCount = 0;
                    }
                }
                Thread.yield();
            }
        }

        private void processPose(OpenGLMatrix p) {
            VectorF translation = p.getTranslation();
            float angle = 0f; //getEulerRotation(l.getPose()).get(1);

            synchronized (Tracker.class) {
                //Remapping pose to adjust for landscape orientation
                //pose = new VectorF(-translation.get(1), translation.get(0), translation.get(2), angle);

                pose = new VectorF(translation.get(0), translation.get(1), translation.get(2), angle);
            }

            noTrackCount = 0;
        }
    }
}
