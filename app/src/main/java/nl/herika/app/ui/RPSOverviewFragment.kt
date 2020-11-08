package nl.herika.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import nl.herika.app.model.Game
import nl.herika.app.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_rps_overview.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.herika.app.R
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RPSOverviewFragment : Fragment() {
    var userMove = "paper"
    var computerMove = "rock"
    var gameResult = ""

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rps_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())
//        getGamesStatsFromDatabase()

        ivRock.setOnClickListener {
            checkGameResult(ivRock)
        }

        ivPaper.setOnClickListener {
            checkGameResult(ivPaper)
        }

        ivScissors.setOnClickListener {
            checkGameResult(ivScissors)
        }

        updateStatistics()
    }

    private fun checkGameResult(view: View) {
        userMove = view.contentDescription.toString()

        checkResult(userMove, generatePcMove())

    }

    private fun checkResult(userMove: String, generatePcMove: String) {
        computerMove = generatePcMove
        when (userMove) {
            "rock" -> {
                ivUserChoice.setImageResource(R.drawable.rock)
                when (computerMove) {
                    getString(R.string.rock) -> {
                        tvResults.text = getString(R.string.draw)
                        gameResult = "draw"
                    }
                    getString(R.string.paper) -> {
                        tvResults.text = getString(R.string.loss)
                        gameResult = "loss"
                    }
                    getString(R.string.scissors) -> {
                        tvResults.text = getString(R.string.win)
                        gameResult = "win"
                    }
                }
                addGame()
            }
            "paper" -> {
                ivUserChoice.setImageResource(R.drawable.paper)
                when (computerMove) {
                    getString(R.string.rock) -> {
                        tvResults.text = getString(R.string.win)
                        gameResult = "win"
                    }
                    getString(R.string.paper) -> {
                        tvResults.text = getString(R.string.draw)
                        gameResult = "draw"
                    }
                    getString(R.string.scissors) -> {
                        tvResults.text = getString(R.string.loss)
                        gameResult = "loss"
                    }
                }
                addGame()
            }
            "scissors" -> {
                ivUserChoice.setImageResource(R.drawable.scissors)
                when (computerMove) {
                    getString(R.string.rock) -> {
                        tvResults.text = getString(R.string.loss)
                        gameResult = "loss"
                    }
                    getString(R.string.paper) -> {
                        tvResults.text = getString(R.string.win)
                        gameResult = "win"
                    }
                    getString(R.string.scissors) -> {
                        tvResults.text = getString(R.string.draw)
                        gameResult = "draw"
                    }
                }
                addGame()
            }
        }
    }

    private fun generatePcMove(): String {
        when ((1..3).random()) {
            1 -> {
                ivComputerChoice.setImageResource(R.drawable.rock)
                return "rock"
            }
            2 -> {
                ivComputerChoice.setImageResource(R.drawable.paper)
                return "paper"
            }
            3 -> {
                ivComputerChoice.setImageResource(R.drawable.scissors)
                return "scissors"
            }
        }
        return ""
    }

    private fun updateStatistics() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            var wins: Int = 0
            var loses: Int = 0
            var draws: Int  = 0

            for (game in games){
                when(game.result){
                    "draw" -> {
                        draws++
                    }
                    "win" -> {
                        wins++
                    }
                    "loss" -> {
                        loses++
                    }
                }
            }
            tvStatistics.text = "Win: " + wins + " Lose: " + loses + " Draw: " + draws
        }
    }

    private fun addGame() {
        val gameDate = Date()
        mainScope.launch {
            val game = Game(
                date = gameDate.toString(),
                computerChoice = computerMove,
                userChoice = userMove,
                result = gameResult

            )

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
        updateStatistics()
    }

}