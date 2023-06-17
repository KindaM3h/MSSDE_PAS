package upm.cabd.mssde_pas.localDb;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ParkEntity.class, RouteEntity.class}, version = 5)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ParkDAO parkDAO();
    public abstract RouteDAO routeDAO();
    private static final String DB_NAME = "AppDataBase";
    private static AppDataBase INSTANCE;

    public static  AppDataBase getDbInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder (context.getApplicationContext(),
                                             AppDataBase.class,
                                             DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
