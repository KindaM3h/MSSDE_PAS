package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.sql.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import upm.cabd.mssde_pas.DatosAbiertosParques.QueryContext;
import upm.cabd.mssde_pas.DatosAbiertosParques.DatosAbiertosParques;
import upm.cabd.mssde_pas.DatosAbiertosParques.Graph;
import upm.cabd.mssde_pas.DatosAbiertosParques.IDatosAbiertosParquesRESTAPIService;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExtendedFloatingActionButton mapButton = findViewById(R.id.map_fab);
        ExtendedFloatingActionButton addRouteButton = findViewById(R.id.add_fab);
        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar);
        RecyclerView recyclerViewMain = findViewById(R.id.recyclerView_main);
        materialToolbar.setNavigationIcon(R.drawable.round_navigate_before_24);
        mapButton.setOnClickListener(this::viewOnMap);
        addRouteButton.setOnClickListener(this::addRoute);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    private void addRoute(View view) {
        Log.i(LOG_TAG, "Add new Route");
    }

    private void viewOnMap (View v){
        Log.i(LOG_TAG, "View On Map");
        Intent mapActivityIntent = new Intent(this, MapActivity.class);
        startActivity(mapActivityIntent);
    }
    private void bindAccelerometer (){
        //TODO: Move this to the add new walk activity since sensor data should be handled there.
        SensorManager sensorManager = (SensorManager) getSystemService(android.content.Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Log.e(LOG_TAG, "Success! we have an accelerometer");

            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

}