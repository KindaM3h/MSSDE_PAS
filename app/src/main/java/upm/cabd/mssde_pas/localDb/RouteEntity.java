package upm.cabd.mssde_pas.localDb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import upm.cabd.mssde_pas.DatosAbiertosParques.Location;

@Entity(tableName = RouteEntity.routeTABLA)
public class RouteEntity {
    static public final String routeTABLA = "Route";
    @PrimaryKey(autoGenerate = true)
    protected int uid;
    @ColumnInfo(name = "user")
    private String user;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "userGrade")
    private float userGrade;
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
    @Ignore
    private String StringPhoto;
    @ColumnInfo(name = "sensorDataRoomKey")
    private int sensorDataRoomKey;

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

    public void setUser(String user) {
        this.user = user;
    }
    public String getUser() {
        return user;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(float userGrade) {
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
    public Location getStartLocation(){
        Location location = new Location();
        location.setLatitude(startLatitude);
        location.setLongitude(startLongitude);
        return location;
    }
    public void setStartLocation(Location location){
        startLatitude = location.getLatitude();
        startLongitude = location.getLongitude();
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
    public Location getEndLocation(){
        Location location = new Location();
        location.setLatitude(endLatitude);
        location.setLongitude(endLongitude);
        return location;
    }
    public void setEndLocation(Location location){
        endLatitude = location.getLatitude();
        endLongitude = location.getLongitude();
    }
    public String getStringPhoto() {
        return StringPhoto;
    }
    public void setStringPhoto(String stringPhoto) {
        StringPhoto = stringPhoto;
    }
    public int getSensorDataRoomKey() {
        return sensorDataRoomKey;
    }
    public void setSensorDataRoomKey(int sensorDataRoomKey) {
        this.sensorDataRoomKey = sensorDataRoomKey;
    }
}