package com.polibuda.gimbus.android_lab7;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class GPSActivity extends AppCompatActivity implements LocationListener {
    private LocationManager manager;
    private TextView locationVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        locationVal = findViewById(R.id.location);
        locationVal.setText("Here should appear some data");
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.getItem(0).setEnabled(MainActivity.otherActive);
        menu.getItem(0).setChecked(!MainActivity.otherActive);
        menu.getItem(1).setEnabled(MainActivity.gpsActive);
        menu.getItem(1).setChecked(!MainActivity.gpsActive);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sensors:{
                MainActivity.gpsActive = true;
                MainActivity.otherActive = false;
                startActivity(new Intent(GPSActivity.this, MainActivity.class));
                break;
            }
            case R.id.menu_gps:{
                break;
            }
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        StringBuilder builder = new StringBuilder();
        builder.append("Altitude: ");
        builder.append(location.getAltitude());
        builder.append("\n");
        builder.append("Longitude: ");
        builder.append(location.getLongitude());
        builder.append("\n");
        builder.append("Latitude: ");
        builder.append(location.getLatitude());
        builder.append("\n");
        locationVal.setText(builder.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.removeUpdates(this);
    }
}
