package dh2jeong.a3basic.view

import dh2jeong.a3basic.model.CellState
import dh2jeong.a3basic.model.Game
import dh2jeong.a3basic.model.GameState
import dh2jeong.a3basic.model.Player
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.EventHandler
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.VPos
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text

class PlayerView(private var title: String, private val playerType: Player, private val game: Game) : VBox(),
    InvalidationListener {
    private val grid = GridPane().apply {
        prefWidth = 350.0
        prefHeight = 310.0
    }
    private val titleText = Text(title).apply {
        alignment = Pos.CENTER
        font = Font.font("Arial", FontWeight.BOLD, 15.0)
    }

    init {
        game.gameStateProperty.addListener(this)
        this.children.add(titleText)
        this.children.add(grid)
        createGrid()
    }

    override fun invalidated(observable: Observable?) {
        createGrid()
    }

    private fun createGrid() {
        grid.children.clear()
        var curLetter = 'A'
        for (i in 0 until 10) {
            val horizontalLabel = Label("${i + 1}")
            val horizontalLabelTwo = Label("${i + 1}")
            val verticalLabel = Label(curLetter.toString()).apply {
                padding = Insets(0.0, 5.0, 0.0, 5.0)
            }
            val verticalLabelTwo = Label(curLetter.toString()).apply {
                padding = Insets(0.0, 5.0, 0.0, 5.0)
            }
            curLetter++
            GridPane.setHalignment(horizontalLabel, HPos.CENTER)
            GridPane.setValignment(horizontalLabel, VPos.CENTER)
            GridPane.setHalignment(horizontalLabelTwo, HPos.CENTER)
            GridPane.setValignment(horizontalLabelTwo, VPos.CENTER)
            GridPane.setHalignment(verticalLabel, HPos.CENTER)
            GridPane.setValignment(verticalLabel, VPos.CENTER)
            GridPane.setHalignment(verticalLabelTwo, HPos.CENTER)
            GridPane.setValignment(verticalLabelTwo, VPos.CENTER)
            grid.add(verticalLabel, 0, i + 1)
            grid.add(verticalLabelTwo, 11, i + 1)
            grid.add(horizontalLabel, i + 1, 11)
            grid.add(horizontalLabelTwo, i + 1, 0)
        }
        val curGrid = game.getBoard(playerType)
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                val colour = getColour(curGrid[i][j])
                val rect = Rectangle(30.0, 30.0, colour).apply {
                    if (playerType == Player.Ai && game.gameStateProperty.value != GameState.AiWon && game.gameStateProperty.value != GameState.HumanWon) {
                        onMouseClicked = EventHandler {
//                            println("BorderPane X Center: ${this.boundsInParent.centerX} , BorderPane Y Center: ${this.boundsInParent.centerY}")
//                            println("Screen X: ${it.sceneX}, Screen Y: ${it.sceneY}")
//                            println("X: $j, Y: $i")
                            game.attackCell(j, i)
                        }
                    }
                }
                rect.stroke = Color.BLACK
                rect.strokeWidth = 1.0
                grid.add(rect, j + 1, i + 1)
            }
        }
    }

    private fun getColour(cellState: CellState): Color {
        return when (cellState) {
            CellState.Ocean -> {
                Color.LIGHTBLUE
            }

            CellState.ShipSunk -> {
                Color.DARKGRAY
            }

            CellState.ShipHit -> {
                Color.CORAL
            }

            else -> {
                Color.LIGHTGRAY
            }
        }
    }
}