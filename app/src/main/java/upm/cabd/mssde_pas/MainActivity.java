package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import upm.cabd.mssde_pas.DatosAbiertosParques.DatosAbiertosParques;

public class MainActivity extends AppCompatActivity {
    private static final String API_BASE_URL = "https://datos.madrid.es/egob/catalogo/";
    private static final String LOG_TAG = "ResponseCode";
    private IDatosAbiertosParquesRESTAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button refreshButton = findViewById(R.id.refreshButton);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(IDatosAbiertosParquesRESTAPIService.class);
        refreshButton.setOnClickListener(this::updateParkData);
    }
    public void updateParkData (View v) {
        Call<List<DatosAbiertosParques>> call_async = apiService.getAllRegisteredParks();
        call_async.enqueue(new Callback<List<DatosAbiertosParques>>() {
            @Override
            public void onResponse(@NonNull Call<List<DatosAbiertosParques>> call, @NonNull Response<List<DatosAbiertosParques>> response) {
                Log.i(LOG_TAG, String.valueOf(response.code()));
            }

            @Override
            public void onFailure(@NonNull Call<List<DatosAbiertosParques>> call, @NonNull Throwable t) {
                Toast.makeText(
                        getApplicationContext(),
                        "ERROR: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }
}