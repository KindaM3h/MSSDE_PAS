package upm.cabd.mssde_pas;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import upm.cabd.mssde_pas.localDb.AppDataBase;
import upm.cabd.mssde_pas.localDb.RouteEntity;
import upm.cabd.mssde_pas.sensor.AccelerometerData;
import upm.cabd.mssde_pas.sensor.MagnetometerData;

public class AddRouteActivity extends AppCompatActivity implements SensorEventListener {

    private RouteEntity routeEntity;
    private static final String LOG_TAG = "AddRouteActivity";
    LocationManager mLocationManager;
    private static final String [] permission = {"Manifest.permission.ACCESS_COARSE_LOCATION"};
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagnetometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        getLocationPermission();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Log.i(LOG_TAG, "Success! we have TYPE_ACCELEROMETER");
            sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.e(LOG_TAG, "Device doesn't have TYPE_ACCELEROMETER");
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            Log.i(LOG_TAG, "Success! we have TYPE_MAGNETIC_FIELD");
            sensorMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            sensorManager.registerListener(this, sensorMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.e(LOG_TAG, "Device doesn't have TYPE_MAGNETIC_FIELD");
        }
    }
    private void getLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, "Manifest.permission.ACCESS_COARSE_LOCATION") == PackageManager.PERMISSION_GRANTED){
            //TODO: Do the thing!
        } else{
            requestPermissions(permission,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0) {
                    int i = 0;
                    Toast toast = new Toast(this);
                    for (int grant : grantResults) {
                        if (grant == PackageManager.PERMISSION_GRANTED) {
                            toast.setText("Permission granted " + permissions[i]);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Log.e(LOG_TAG, "Permission denied " + permissions[i]);
                        }
                        i = i + 1;
                    }
                }
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Button addRouteSave = findViewById(R.id.addRouteSave);
        EditText addRouteName = findViewById(R.id.addRouteName);
        EditText addRouteDescription = findViewById(R.id.addRouteDescription);
        RatingBar addRouteUserRating = findViewById(R.id.addRouteUserRating);
        SeekBar addRouteUserAccessibility = findViewById(R.id.addRouteUserAccessibility);
        addRouteSave.setOnClickListener(view -> {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            routeEntity = new RouteEntity();
            assert firebaseUser != null;
            routeEntity.setUser(firebaseUser.getEmail());
            routeEntity.setName(String.valueOf(addRouteName.getText()));
            routeEntity.setDescription(String.valueOf(addRouteDescription.getText()));
            routeEntity.setUserGrade(addRouteUserRating.getRating());
            routeEntity.setUserAccessibility(addRouteUserAccessibility.getProgress());
            AppDataBase appDataBase = AppDataBase.getDbInstance(getApplicationContext());
            appDataBase.routeDAO().insertRoute(routeEntity);
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensorMagnetometer);
        sensorManager.unregisterListener(this, sensorAccelerometer);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (Objects.equals(sensorEvent.sensor.getStringType(), Sensor.STRING_TYPE_ACCELEROMETER)){
            AccelerometerData accelerometerData = new AccelerometerData(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            Log.i(LOG_TAG, "Accelerometer Data " + accelerometerData.getAccelerationDelta_X_axis_inMetersPerSecondSquared() + " " +
                                                        accelerometerData.getAccelerationDelta_Y_axis_inMetersPerSecondSquared() + " " +
                                                        accelerometerData.getAccelerationDelta_Z_axis_inMetersPerSecondSquared());
        }
        if (Objects.equals(sensorEvent.sensor.getStringType(), Sensor.STRING_TYPE_MAGNETIC_FIELD)){
            MagnetometerData magnetometerData = new MagnetometerData(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            Log.i(LOG_TAG, "Magnetometer Data " + magnetometerData.getMagneticFlux_X_axis_inMicroTesla() + " " +
                                                       magnetometerData.getMagneticFlux_Y_axis_inMicroTesla() + " " +
                                                       magnetometerData.getMagneticFlux_Z_axis_inMicroTesla());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}