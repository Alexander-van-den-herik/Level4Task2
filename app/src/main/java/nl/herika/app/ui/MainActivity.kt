package nl.herika.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import nl.herika.app.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.herika.app.R

class MainActivity : AppCompatActivity() {
    private lateinit var historyButton: MenuItem
    private lateinit var deleteButton: MenuItem
    private lateinit var navController: NavController
    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)
        gameRepository = GameRepository(baseContext)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            historyButton = menu.findItem(R.id.btn_nav_icon_history)
            deleteButton = menu.findItem(R.id.btn_nav_icon_delete)
            deleteButton.isVisible = false
        }
        toggleNavIcon()
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.btn_nav_icon_delete -> {
                deleteAllGames()
                true
            }
            R.id.btn_nav_icon_history -> {
                navController.navigate(R.id.action_RPSOverviewFragment_to_GameHistoryFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportActionBar?.setHomeButtonEnabled(false)
            supportFragmentManager.popBackStack()
        }
        return true
    }

    private fun deleteAllGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            gameAdapter = GameAdapter(arrayListOf())
            rvGameHistory.adapter = gameAdapter
            gameAdapter.notifyDataSetChanged()

            Toast.makeText(
                baseContext,
                "Successful deleted all games",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun toggleNavIcon() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.GameHistoryFragment)) { // history fragment
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setTitle(R.string.title_fragment_history)
                historyButton.isVisible = false
                deleteButton.isVisible = true
            } else { // home fragment
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setTitle(R.string.app_name)
                historyButton.isVisible = true
                deleteButton.isVisible = false
            }
        }
    }
}