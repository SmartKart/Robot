package org.firstinspires.ftc.robotcontroller.internal;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.location.LocationListener;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;


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

    /*@Override
    protected void onStart() {
        super.onStart();
        // Async
        pollServer();
    }*/

    public void updateLocation(final double latitude, final double longitude) {
        new AsyncTask<Void, Void, String>() {
            @Override protected String doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();
                JSONObject payload = new JSONObject();

                try {
                    payload.put("id", 1);
                    payload.put("lat", latitude);
                    payload.put("lng", longitude);
                }
                catch(JSONException e) {
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), payload.toString());
                String url = "http://ec2-52-86-213-15.compute-1.amazonaws.com/api/v1/carts/1";
                Request request = new Request.Builder()
                        .url(url)
                        .put(body)
                        .build();
                String str;
                try {
                    Response res = client.newCall(request).execute();
                    str = res.body().string();
                } catch (IOException e) {
                    str = e.getMessage();
                }
                return str;
            }

            @Override
            protected void onPostExecute(String result) {
                String temp = result;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
    }
}

