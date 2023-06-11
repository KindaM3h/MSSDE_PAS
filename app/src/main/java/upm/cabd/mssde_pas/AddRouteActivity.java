package upm.cabd.mssde_pas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import upm.cabd.mssde_pas.DatosAbiertosParques.Location;
import upm.cabd.mssde_pas.localDb.AppDataBase;
import upm.cabd.mssde_pas.localDb.RouteEntity;

public class AddRouteActivity extends AppCompatActivity {

    private RouteEntity routeEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);
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
                Location startLocation = new Location();
                startLocation.setLatitude(40.8);
                startLocation.setLongitude(-3.5);
                Location endLocation = new Location();
                endLocation.setLatitude(41.8);
                endLocation.setLongitude(-2.5);
                // TODO: Figure out how to include Location data.
                routeEntity = new RouteEntity();
                routeEntity.setName(String.valueOf(addRouteName.getText()));
                routeEntity.setDescription(String.valueOf(addRouteDescription.getText()));
                routeEntity.setUserGrade(addRouteUserRating.getNumStars());
                routeEntity.setUserAccessibility(addRouteUserAccessibility.getScrollX());
                AppDataBase appDataBase = AppDataBase.getDbInstance(getApplicationContext());
                appDataBase.routeDAO().insertRoute(routeEntity);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}