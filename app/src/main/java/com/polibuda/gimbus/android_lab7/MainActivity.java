package com.polibuda.gimbus.android_lab7;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private RelativeLayout layout;
    private SensorManager manager;
    private Sensor accelSensor;
    static boolean gpsActive = true;
    static boolean otherActive = false;
    private double x;
    private double y;
    private double z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.main_layout);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.getItem(0).setEnabled(otherActive);
        menu.getItem(0).setChecked(!otherActive);
        menu.getItem(1).setEnabled(gpsActive);
        menu.getItem(1).setChecked(!gpsActive);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_colours:{
                break;
            }
            case R.id.menu_compass:{
                gpsActive = false;
                otherActive = true;
                startActivity(new Intent(MainActivity.this, CompassActivity.class));
                break;
            }
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                x = -1*((event.values[0]/9.81)*255.0);
                y = -1*((event.values[1]/9.81)*255.0);
                z = -1*((event.values[2]/9.81)*255.0);
                int color = (int)(x+y+z);
                layout.setBackgroundColor(color);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }
}
