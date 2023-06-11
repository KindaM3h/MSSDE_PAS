package upm.cabd.mssde_pas.localDb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RouteDAO {
    @Insert (onConflict = OnConflictStrategy.ABORT)
    void insertRoute (RouteEntity routeEntity);
    @Query("SELECT * FROM " + RouteEntity.routeTABLA)
    List<RouteEntity> getAllRoutes();
}
