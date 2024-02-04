package dh2jeong.a3basic

import dh2jeong.a3basic.model.Game
import dh2jeong.a3basic.view.GameView
import dh2jeong.a3basic.view.HarbourView
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class Battleship : Application() {
    override fun start(stage: Stage) {
        val game = Game(10, false)
        val gameView = GameView(game)
        val computer = AI(game)
        game.startGame()
        val harbour = HarbourView(game)

        stage.apply {
            scene = Scene(StackPane(gameView, harbour), 875.0, 375.0)
            title = "CS349 - A3 Battleship - dh2jeong"
        }.show()
    }
}