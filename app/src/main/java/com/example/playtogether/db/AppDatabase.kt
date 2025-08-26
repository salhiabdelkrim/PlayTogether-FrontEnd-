import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playtogether.db.SportDao
import com.example.playtogether.model.Sport

@Database(entities = [Sport::class, /* autres entités */], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sportDao(): SportDao
    // autres DAOs
}
