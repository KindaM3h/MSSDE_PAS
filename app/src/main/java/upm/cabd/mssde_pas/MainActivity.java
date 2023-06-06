package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import upm.cabd.mssde_pas.DatosAbiertosParques.Context;
import upm.cabd.mssde_pas.DatosAbiertosParques.DatosAbiertosParques;
import upm.cabd.mssde_pas.DatosAbiertosParques.Graph;
import upm.cabd.mssde_pas.DatosAbiertosParques.IDatosAbiertosParquesRESTAPIService;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this::viewOnMap);
    }

    public void viewOnMap (View v){
        Log.i(LOG_TAG, "View On Map");
        Intent mapActivityIntent = new Intent(this, MapActivity.class);
        startActivity(mapActivityIntent);
    }
    public void bindAccelerometer (){
        //TODO: Move this to the add new walk activity since sensor data should be handled there.
        SensorManager sensorManager = (SensorManager) getSystemService(android.content.Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Log.e(LOG_TAG, "Success! we have an accelerometer");

            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

}