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


    public ParkEntity(String title, String description, int accessibility) {
        this.title = title;
        this.description = description;
        this.accessibility = accessibility;
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
}
