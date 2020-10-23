package nl.herika.app.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class Game (

    @ColumnInfo(name = "gameDate")
    var date : String,

    @ColumnInfo(name = "computerChoice")
    var computerChoice: String,

    @ColumnInfo(name = "userChoice")
    var userChoice: String,

    @ColumnInfo(name = "result")
    var result: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
)
