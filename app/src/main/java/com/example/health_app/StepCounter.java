package com.example.step_counter;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StepCounter extends AppCompatActivity implements SensorEventListener {

    TextView tv_steps;
    TextView tv_calories;
    TextView tv_distance;
    Button reset;
    SensorManager sensormanager;
    Sensor mstepcounter;
    int stepcount =0;
    boolean running;
    DecimalFormat df= new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        tv_steps = (TextView) findViewById(R.id.TV_STEPS);
        tv_calories = (TextView) findViewById(R.id.TV_CALORIES);
        tv_distance = (TextView) findViewById(R.id.TV_DISTANCE);
        reset=findViewById(R.id.reset);
        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        runtimepermission();

        reset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                stepcount=stepcount-stepcount;
                tv_steps.setText(String.valueOf(stepcount-stepcount));
                double calorie=calories(stepcount);
                double distances=distance(stepcount);
                tv_calories.setText(String.valueOf(calorie));
                tv_distance.setText(String.valueOf(distances));

            }
        });

        if (sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mstepcounter = sensormanager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            running = true;
        } else {
            Toast.makeText(getApplicationContext(),"Sensor not found",Toast.LENGTH_LONG).show();
            running = false;


        }

    }



    @Override
    protected void onResume() {
        super.onResume();

        if (mstepcounter != null) {
            sensormanager.registerListener(this, mstepcounter, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mstepcounter != null) {
            sensormanager.unregisterListener(this, mstepcounter);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == mstepcounter) {
            stepcount = (int) sensorEvent.values[0];
            tv_steps.setText(String.valueOf(stepcount));
            calories(stepcount);
            distance(stepcount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void runtimepermission() {
        Dexter.withContext(StepCounter.this)
                .withPermission(Manifest.permission.ACTIVITY_RECOGNITION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public double calories(int count) {
        double cal = Double.parseDouble(df.format(count * 0.045));
        tv_calories.setText(String.valueOf(cal));
        return cal;
    }

    public double distance(int count) {
        double feet = count * 2.5;
        double distance = Double.parseDouble(df.format(feet / 3.281));
        tv_distance.setText(String.valueOf(distance));
        return distance;
    }
}