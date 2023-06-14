package upm.cabd.mssde_pas.localDb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ParkDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertPark (ParkEntity parkEntity);

    @Query("SELECT * FROM " + ParkEntity.TABLA)
    List<ParkEntity> getAllParks();
}
