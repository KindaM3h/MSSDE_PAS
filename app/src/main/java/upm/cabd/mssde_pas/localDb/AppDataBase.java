package upm.cabd.mssde_pas.localDb;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ParkEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ParkDao parkDao();
    private static final String DB_NAME = "AppDataBase";
    private static AppDataBase INSTANCE;

    public static  AppDataBase getDbInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder (context.getApplicationContext(),
                                             AppDataBase.class,
                                             DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
