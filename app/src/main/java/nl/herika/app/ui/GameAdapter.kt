package nl.herika.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.herika.app.model.Game
import kotlinx.android.synthetic.main.item_game.view.*
import nl.herika.app.R

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun databind(game: Game) {
            itemView.tvDate.text = game.date
            itemView.tvGameResults.text = game.result

            when(game.computerChoice) {
                "rock" -> {
                    itemView.ivPCMove.setImageResource(R.drawable.rock)
                }
                "paper" -> {
                    itemView.ivPCMove.setImageResource(R.drawable.paper)
                }
                "scissors" -> {
                    itemView.ivPCMove.setImageResource(R.drawable.scissors)
                }
            }
            when(game.userChoice) {
                "rock" -> {
                    itemView.ivUserMove.setImageResource(R.drawable.rock)
                }
                "paper" -> {
                    itemView.ivUserMove.setImageResource(R.drawable.paper)
                }
                "scissors" -> {
                    itemView.ivUserMove.setImageResource(R.drawable.scissors)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}