package com.polibuda.gimbus.android_lab7;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView accel;
    private TextView light;
    private SensorManager manager;
    private Sensor lightSensor;
    private Sensor accelSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accel = findViewById(R.id.accel_val);
        light = findViewById(R.id.light_val);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accelSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GPSActivity.class));
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                StringBuilder builder = new StringBuilder();
                builder.append("x: ");
                builder.append(String.format("%7.4f", event.values[0]));
                builder.append(" y: ");
                builder.append(String.format("%7.4f", event.values[1]));
                builder.append(" z: ");
                builder.append(String.format("%7.4f", event.values[2]));
                accel.setText(builder.toString());
            }
            case Sensor.TYPE_LIGHT: {
                StringBuilder builder = new StringBuilder();
                builder.append(String.format("%7.4f", event.values[0]));
                builder.append(" lumen");
                light.setText(builder.toString());
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }
}
