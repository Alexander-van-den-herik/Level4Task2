package nl.herika.app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nl.herika.app.model.Game

@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()

//    @Query("SELECT COUNT(*) FROM game_table WHERE result LIKE 'win'")
//    suspend fun getGamesResultWins()
//
//    @Query("SELECT COUNT(*) FROM game_table WHERE result LIKE 'loss'")
//    suspend fun getGamesResultLosses()
//
//    @Query("SELECT COUNT(*) FROM game_table WHERE result LIKE 'draw'")
//    suspend fun getGamesResultDraws()
}