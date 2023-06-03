package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    private static final String API_BASE_URL = "https://datos.madrid.es/egob/catalogo/";
    private static final String LOG_TAG = "MainActivity";
    private IDatosAbiertosParquesRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button refreshButton = findViewById(R.id.refreshButton);
        Button mapButton = findViewById(R.id.mapButton);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(IDatosAbiertosParquesRESTAPIService.class);
        refreshButton.setOnClickListener(this::updateParkData);
        mapButton.setOnClickListener(this::viewOnMap);
    }
    public void updateParkData (View v) {
        Call<DatosAbiertosParques> call_async = apiService.getAllRegisteredParks();
        call_async.enqueue(new Callback<DatosAbiertosParques>() {
            @Override
            public void onResponse(Call<DatosAbiertosParques> call, Response<DatosAbiertosParques> response) {
                Log.i(LOG_TAG, String.valueOf(response.code()));
                DatosAbiertosParques parsedResponse = response.body();
                Context queryContext = parsedResponse.getContext();
                List<Graph> parkList = parsedResponse.getGraph();

                if (null != queryContext){
                    Log.i(LOG_TAG, queryContext.getGeo());
                }
                if (null != parkList){
                    for (Graph parkInstance : parkList){
                        Log.d(LOG_TAG, parkInstance.getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<DatosAbiertosParques> call, Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public void viewOnMap (View v){
        Log.i(LOG_TAG, "View On Map");
        Intent mapActivityIntent = new Intent(this, MapActivity.class);
        startActivity(mapActivityIntent);
    }
}