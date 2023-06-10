package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.carousel.CarouselLayoutManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

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
import upm.cabd.mssde_pas.DatosAbiertosParques.Location;
import upm.cabd.mssde_pas.localDb.ParkEntity;
import upm.cabd.mssde_pas.view.OnParkClick;
import upm.cabd.mssde_pas.view.ParkListAdapter;

public class MapActivity extends AppCompatActivity implements OnParkClick {
    private MapView map;
    ParkListAdapter parkListAdapter;
    private static final String API_BASE_URL = "https://datos.madrid.es/egob/catalogo/";
    private IDatosAbiertosParquesRESTAPIService apiService;

    List<Graph> parkList;

    private static final String LOG_TAG = "MapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        map = findViewById(R.id.map);
        RecyclerView recyclerViewMap = findViewById(R.id.recyclerView_map);
        parkListAdapter = new ParkListAdapter(this, this);
        recyclerViewMap.setAdapter(parkListAdapter);
        recyclerViewMap.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(IDatosAbiertosParquesRESTAPIService.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateParkData ();
    }

    private void mapHandler (Location location, String title) {
        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        map.setBuiltInZoomControls(true);
        map.getController().setZoom(14.50);
        map.getController().setCenter(startPoint);
        Marker marker = new Marker(map);
        marker.setPosition(startPoint);
        marker.setTitle(title);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(marker);
        Log.i(LOG_TAG, "Map now display: " + title);
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
                parkList = parsedResponse.getGraph();

                if (null != queryContext){
                    Log.i(LOG_TAG, queryContext.getGeo());
                }
                if (null != parkList){
                    for (Graph parkInstance : parkList){
                        ParkEntity parkEntity = new ParkEntity (parkInstance.getTitle(),
                                                                parkInstance.getOrganization().getOrganizationDesc(),
                                                                parkInstance.getLocation(),
                                                                parkInstance.getOrganization().getAccesibility());
                        Log.d(LOG_TAG, parkInstance.getTitle());
                    }
                }
                parkListAdapter.setItems(parkList);
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

    @Override
    public void onItemClick(Graph graph) {
        mapHandler(graph.getLocation(), graph.getTitle());
    }
}