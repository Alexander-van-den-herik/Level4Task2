package nl.herika.app.repository

import android.content.Context
import nl.herika.app.dao.GameDao
import nl.herika.app.database.GamesRoomDatabase
import nl.herika.app.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GamesRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

}
