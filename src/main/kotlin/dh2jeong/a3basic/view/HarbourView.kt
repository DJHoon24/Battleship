package dh2jeong.a3basic.view

import dh2jeong.a3basic.controller.EndGameButton
import dh2jeong.a3basic.controller.MovableManager
import dh2jeong.a3basic.controller.StartGameButton
import dh2jeong.a3basic.model.*
import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.scene.layout.Pane
import javafx.scene.transform.Rotate
import javafx.util.Duration

class HarbourView(private val game: Game) : Pane(), InvalidationListener {
    private val mover = MovableManager(this, game)
    private val startX = 362.5
    private val startY = 32.5
    private val destroyer = ShipView(startX, startY, ShipType.Destroyer).apply {
        mover.makeMovable(this)
    }
    private val cruiser = ShipView(startX + (31 * 1), startY, ShipType.Cruiser).apply {
        mover.makeMovable(this)
    }
    private val submarine = ShipView(startX + (31 * 2), startY, ShipType.Submarine).apply {
        mover.makeMovable(this)
    }
    private val battleship = ShipView(startX + (31 * 3), startY, ShipType.Battleship).apply {
        mover.makeMovable(this)
    }
    private val carrier = ShipView(startX + (31 * 4), startY, ShipType.Carrier).apply {
        mover.makeMovable(this)
    }

    init {
        game.gameStateProperty.addListener(this)
        setupShips()
        setupButtons()
        isMouseTransparent = false
    }

    override fun invalidated(observable: Observable?) {
        if (game.gameStateProperty.value == GameState.AiWon || game.gameStateProperty.value == GameState.HumanWon) {
            if (!game.isSunk(Player.Human, destroyer.getShipID())) {
                reset(destroyer)
            }
            if (!game.isSunk(Player.Human, cruiser.getShipID())) {
                reset(cruiser)
            }
            if (!game.isSunk(Player.Human, submarine.getShipID())) {
                reset(submarine)
            }
            if (!game.isSunk(Player.Human, battleship.getShipID())) {
                reset(battleship)
            }
            if (!game.isSunk(Player.Human, carrier.getShipID())) {
                reset(carrier)
            }
        }
    }

    private fun reset(shipView: ShipView) {
        if (shipView.getOrientation() == Orientation.Horizontal) {
            val rotation = RotateTransition(Duration(10.0), shipView)
            rotation.axis = Rotate.Z_AXIS
            rotation.interpolator = Interpolator.LINEAR
            rotation.fromAngle = -90.0
            rotation.toAngle = 0.0
            rotation.play()
            shipView.swapOrientation()
        }
        shipView.translateX = 0.0
        shipView.translateY = 0.0
    }

    private fun setupButtons() {
        this.children.addAll(StartGameButton(game, this), EndGameButton(362.5, 330.0))
    }

    private fun setupShips() {
        this.children.addAll(destroyer, cruiser, submarine, battleship, carrier)
    }
}