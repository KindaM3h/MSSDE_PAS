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
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "location")
    private Location location;

    @ColumnInfo(name = "accessibility")
    private int accessibility;


    public ParkEntity(String title, String description, Location location, int accessibility) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.accessibility = accessibility;
    }

    public int getUid() {
        return uid;
    }

}
