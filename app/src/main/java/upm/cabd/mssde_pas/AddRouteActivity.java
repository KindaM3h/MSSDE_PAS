package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import upm.cabd.mssde_pas.DatosAbiertosParques.Location;
import upm.cabd.mssde_pas.localDb.AppDataBase;
import upm.cabd.mssde_pas.localDb.RouteEntity;

public class AddRouteActivity extends AppCompatActivity {

    private RouteEntity routeEntity;
    LocationManager mLocationManager;
    private static final String [] permission = {"Manifest.permission.ACCESS_COARSE_LOCATION"};
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
        getLocationPermission();
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
                            toast.setText("Permission denied " + permissions[i]);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.show();
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
        addRouteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
    }
}