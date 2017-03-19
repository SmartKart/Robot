package org.firstinspires.ftc.robotcontroller.internal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

/**
 * Created by James on 2017-03-18.
 */

public class Controller {

    public static double P_A = 0.25;
    public static double P_Z = 0.20;
    public static double TARGET_Z = 310; //mm

    public static void setPA(double newValue) {
        P_A = newValue;
    }
    public static void setPZ(double newValue) {
        P_Z = newValue;
    }
    public static void setTZ(double newValue) {
        TARGET_Z = newValue;
    }

    public static void readControllerParameters(Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        Controller.setPA(prefs.getFloat("PROPORTIONAL_A", (float) Controller.P_A));
        Controller.setPZ(prefs.getFloat("PROPORTIONAL_Z", (float) Controller.P_Z));
        Controller.setTZ(prefs.getInt("TARGET_Z", (int) Controller.TARGET_Z));
    }
}
