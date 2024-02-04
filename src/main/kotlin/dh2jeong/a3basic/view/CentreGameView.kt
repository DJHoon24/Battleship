package dh2jeong.a3basic.view

import dh2jeong.a3basic.controller.EndGameButton
import dh2jeong.a3basic.model.Game
import dh2jeong.a3basic.model.GameState
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.geometry.Insets
import javafx.scene.layout.Pane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text

class CentreGameView(private val game: Game) : Pane(), InvalidationListener {
    private val titleText = Text("My Fleet").apply {
        layoutX = 50.0
        layoutY = 20.0
        font = Font.font("Arial", FontWeight.BOLD, 15.0)
        padding = Insets(5.0)
    }

    init {
        game.gameStateProperty.addListener(this)
        this.children.addAll(titleText, EndGameButton(12.5, 330.0))
    }

    override fun invalidated(observable: Observable?) {
        if (game.gameStateProperty.value == GameState.AiWon) {
            titleText.text = "You were defeated!"
            titleText.layoutX -= 25.0
        } else if (game.gameStateProperty.value == GameState.HumanWon) {
            titleText.text = "You won!"
        }
    }
}