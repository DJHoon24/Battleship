package dh2jeong.a3basic.view

import dh2jeong.a3basic.model.Game
import dh2jeong.a3basic.model.Player
import javafx.scene.layout.BorderPane

class GameView(private val game: Game) : BorderPane() {
    init {
        this.center = CentreGameView(game)
        this.left = PlayerView("My Formation", Player.Human, game)
        this.right = PlayerView("Opponent's Waters", Player.Ai, game)
    }

}