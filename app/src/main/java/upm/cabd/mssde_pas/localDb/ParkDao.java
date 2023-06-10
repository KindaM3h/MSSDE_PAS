package upm.cabd.mssde_pas.localDb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface ParkDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertPark (ParkEntity parkEntity);
}
