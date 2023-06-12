package upm.cabd.mssde_pas.localDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import upm.cabd.mssde_pas.DatosAbiertosParques.Location;

@Entity(tableName = ParkEntity.TABLA)
public class ParkEntity {
    static public final String TABLA = "Park";

    @PrimaryKey(autoGenerate = true)
    protected int uid;
    @ColumnInfo(name = "title")
    private final String title;
    @ColumnInfo(name = "description")
    private final String description;
    @ColumnInfo(name = "accessibility")
    private final int accessibility;
    @ColumnInfo(name = "latitude")
    private final double latitude;
    @ColumnInfo(name = "longitude")
    private final double longitude;


    public ParkEntity(String title, String description, int accessibility, double latitude, double longitude) {
        this.title = title;
        this.description = description;
        this.accessibility = accessibility;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getAccessibility() {
        return accessibility;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public Location getLocation(){
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }
}
