package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

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
import upm.cabd.mssde_pas.localDb.RouteEntity;
import upm.cabd.mssde_pas.view.RouteListAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private static final String API_BASE_URL = "https://datos.madrid.es/egob/catalogo/";
    private IDatosAbiertosParquesRESTAPIService apiService;
    private AppDataBase appDataBase;
    private RouteListAdapter routeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExtendedFloatingActionButton mapButton = findViewById(R.id.map_fab);
        ExtendedFloatingActionButton addRouteButton = findViewById(R.id.add_fab);
        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);
        RecyclerView recyclerViewMain = findViewById(R.id.recyclerView_main);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        routeListAdapter = new RouteListAdapter(this);
        recyclerViewMain.setAdapter(routeListAdapter);
        mapButton.setOnClickListener(this::viewOnMap);
        addRouteButton.setOnClickListener(this::addRoute);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(IDatosAbiertosParquesRESTAPIService.class);
        appDataBase = AppDataBase.getDbInstance(getApplicationContext());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Toast toast = new Toast(getApplicationContext());
                int position = viewHolder.getAdapterPosition();
                RouteEntity routeEntity = routeListAdapter.returnCurrentRouteEntity(position);
                if (direction == ItemTouchHelper.LEFT){
                    //TODO: Do something!
                }
                if (direction == ItemTouchHelper.RIGHT){
                    //TODO: Do something else!
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoff){
            Log.i(LOG_TAG, "Log off!");
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseAuth.getInstance().signOut();
            assert firebaseUser != null;
            Toast.makeText(this, getText(R.string.logoff) +" "+ firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        }
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (appDataBase.parkDAO().getAllParks().size() == 0) {
            updateParkData();
        } else {
            Log.d(LOG_TAG, "Data is available in local db, logs " + appDataBase.parkDAO().getAllParks().size());
        }
        List<RouteEntity> routeEntityList = appDataBase.routeDAO().getAllRoutes();
        routeListAdapter.setItems(routeEntityList);
    }

    private void addRoute(View view) {
        Log.i(LOG_TAG, "Add new Route");
        Intent intent = new Intent(this, AddRouteActivity.class);
        startActivity(intent);
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
                        appDataBase.parkDAO().insertPark(parkEntity);
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
}