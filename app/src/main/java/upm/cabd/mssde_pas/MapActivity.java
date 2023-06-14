package upm.cabd.mssde_pas;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

import upm.cabd.mssde_pas.DatosAbiertosParques.Location;
import upm.cabd.mssde_pas.localDb.AppDataBase;
import upm.cabd.mssde_pas.localDb.ParkEntity;
import upm.cabd.mssde_pas.view.OnParkClick;
import upm.cabd.mssde_pas.view.ParkListAdapter;

public class MapActivity extends AppCompatActivity implements OnParkClick {
    private MapView map;
    ParkListAdapter parkListAdapter;
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
        initMap();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppDataBase appDataBase = AppDataBase.getDbInstance(getApplicationContext());
        List<ParkEntity> parkList = appDataBase.parkDAO().getAllParks();
        for (ParkEntity parkEntity : parkList){
            Log.d(LOG_TAG, parkEntity.getTitle());
        }
        parkListAdapter.setItems(parkList);
    }
    public void initMap(){
        GeoPoint geoPoint = new GeoPoint(40.416775, -3.703790);
        map.getController().setZoom(14.50);
        map.getController().setCenter(geoPoint);
    }
    private void mapHandler (Location location, String title) {
        GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        map.setBuiltInZoomControls(true);
        map.getController().setZoom(14.50);
        map.getController().setCenter(geoPoint);
        Marker marker = new Marker(map);
        marker.setPosition(geoPoint);
        marker.setTitle(title);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(marker);
        Log.i(LOG_TAG, "Map now display: " + title);
    }

    @Override
    public void onItemClick(ParkEntity parkEntity) {
        mapHandler (parkEntity.getLocation(),
                    parkEntity.getTitle());
    }
}