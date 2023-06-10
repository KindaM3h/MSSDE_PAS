package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import upm.cabd.mssde_pas.DatosAbiertosParques.QueryContext;
import upm.cabd.mssde_pas.DatosAbiertosParques.DatosAbiertosParques;
import upm.cabd.mssde_pas.DatosAbiertosParques.Graph;
import upm.cabd.mssde_pas.DatosAbiertosParques.IDatosAbiertosParquesRESTAPIService;
import upm.cabd.mssde_pas.localDb.AppDataBase;
import upm.cabd.mssde_pas.localDb.ParkEntity;
import upm.cabd.mssde_pas.view.RouteListAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private static final String API_BASE_URL = "https://datos.madrid.es/egob/catalogo/";
    private IDatosAbiertosParquesRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExtendedFloatingActionButton mapButton = findViewById(R.id.map_fab);
        ExtendedFloatingActionButton addRouteButton = findViewById(R.id.add_fab);
        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar);
        RecyclerView recyclerViewMain = findViewById(R.id.recyclerView_main);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        RouteListAdapter routeListAdapter = new RouteListAdapter(this);
        recyclerViewMain.setAdapter(routeListAdapter);
        mapButton.setOnClickListener(this::viewOnMap);
        addRouteButton.setOnClickListener(this::addRoute);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(IDatosAbiertosParquesRESTAPIService.class);
    }
    @Override
    protected void onStart() {
        super.onStart();
        updateParkData();
    }

    private void addRoute(View view) {
        Log.i(LOG_TAG, "Add new Route");
    }

    private void viewOnMap (View v){
        Log.i(LOG_TAG, "View On Map");
        Intent mapActivityIntent = new Intent(this, MapActivity.class);
        startActivity(mapActivityIntent);
    }
    public void updateParkData () {
        Call<DatosAbiertosParques> call_async = apiService.getAllRegisteredParks();
        call_async.enqueue(new Callback<DatosAbiertosParques>() {
            @Override
            public void onResponse(@NonNull Call<DatosAbiertosParques> call, @NonNull Response<DatosAbiertosParques> response) {
                Log.i(LOG_TAG, String.valueOf(response.code()));
                DatosAbiertosParques parsedResponse = response.body();
                assert parsedResponse != null;
                QueryContext queryContext = parsedResponse.getContext();

                if (null != queryContext){
                    Log.i(LOG_TAG, queryContext.getGeo());
                }
                if (null != parsedResponse.getGraph()){
                    for (Graph parkInstance : parsedResponse.getGraph()){
                        ParkEntity parkEntity = new ParkEntity (parkInstance.getTitle(),
                                parkInstance.getOrganization().getOrganizationDesc(),
                                parkInstance.getOrganization().getAccesibility(),
                                parkInstance.getLocation().getLatitude(),
                                parkInstance.getLocation().getLongitude());
                        Log.d(LOG_TAG, parkEntity.getTitle());
                        AppDataBase appDataBase = AppDataBase.getDbInstance(getApplicationContext());
                        appDataBase.parkDao().insertPark(parkEntity);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DatosAbiertosParques> call, @NonNull Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
    private void bindAccelerometer (){
        //TODO: Move this to the add new walk activity since sensor data should be handled there.
        SensorManager sensorManager = (SensorManager) getSystemService(android.content.Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Log.i(LOG_TAG, "Success! we have an accelerometer");

            Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

}