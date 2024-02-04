package dh2jeong.a3basic.controller

import dh2jeong.a3basic.model.Game
import dh2jeong.a3basic.model.Player
import dh2jeong.a3basic.view.HarbourView
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.EventHandler
import javafx.scene.control.Button

class StartGameButton(private var game: Game, private var harbourView: HarbourView) : Button("Start Game"),
    InvalidationListener {
    init {
        game.ShipsPlacedCount.addListener(this)
        isDisable = true
        prefWidth = 150.0
        layoutX = 362.5
        layoutY = 300.0
        onAction = EventHandler {
            harbourView.isMouseTransparent = true
            game.startGame()
            isDisable = true
        }
    }

    override fun invalidated(observable: Observable?) {
        isDisable = game.getShipsPlacedCount(Player.Human) != 5
    }
}