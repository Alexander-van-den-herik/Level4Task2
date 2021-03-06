package nl.herika.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.herika.app.model.Game
import nl.herika.app.dao.GameDao

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GamesRoomDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "GAMES_DATABASE"

        @Volatile
        private var gamesRoomDatabaseInstance: GamesRoomDatabase? = null

        fun getDatabase(context: Context): GamesRoomDatabase? {
            if (gamesRoomDatabaseInstance == null) {
                synchronized(GamesRoomDatabase::class.java) {
                    if (gamesRoomDatabaseInstance == null) {
                        gamesRoomDatabaseInstance =
                            Room.databaseBuilder(context.applicationContext,
                                GamesRoomDatabase::class.java, DATABASE_NAME
                            ).build()
                    }
                }
            }
            return gamesRoomDatabaseInstance
        }
    }

}
