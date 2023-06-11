package upm.cabd.mssde_pas.localDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import upm.cabd.mssde_pas.DatosAbiertosParques.Location;

@Entity(tableName = RouteEntity.routeTABLA)
public class RouteEntity {
    static public final String routeTABLA = "Route";
    @PrimaryKey(autoGenerate = true)
    protected int uid;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "userGrade")
    private int userGrade;
    @ColumnInfo(name = "userAccessibility")
    private int userAccessibility;
    @ColumnInfo(name = "startLatitude")
    private double startLatitude;
    @ColumnInfo(name = "startLongitude")
    private double startLongitude;
    @ColumnInfo(name = "endLatitude")
    private double endLatitude;
    @ColumnInfo(name = "endLongitude")
    private double endLongitude;

    public RouteEntity (){
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(int userGrade) {
        this.userGrade = userGrade;
    }

    public int getUserAccessibility() {
        return userAccessibility;
    }

    public void setUserAccessibility(int userAccessibility) {
        this.userAccessibility = userAccessibility;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }
}