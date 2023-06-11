package upm.cabd.mssde_pas.localDb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface RouteDAO {
    @Insert (onConflict = OnConflictStrategy.ABORT)
    void insertRoute (RouteEntity routeEntity);
}
