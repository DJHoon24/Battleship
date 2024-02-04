package dh2jeong.a3basic.controller

import dh2jeong.a3basic.model.Game
import dh2jeong.a3basic.model.Orientation
import dh2jeong.a3basic.view.ShipView
import javafx.animation.Interpolator
import javafx.animation.RotateTransition
import javafx.animation.TranslateTransition
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.transform.Rotate
import javafx.util.Duration
import kotlin.math.floor

class MovableManager(parent: Node, private val game: Game) {

    private var movingNode: ShipView? = null

    // the offset captured at start of drag
    private var offsetX = 0.0
    private var offsetY = 0.0
    private val gridOffsetX = 25.0
    private val gridOffsetY = 47.5

    init {
        // important that this is in bubble phase, not capture phase
        parent.addEventHandler(MouseEvent.MOUSE_CLICKED) { e ->
            val node = movingNode
            if (node != null) {
                if (e.button == MouseButton.SECONDARY) { //ANIMATE
                    rotateShip(node)
                    e.consume()
                } else {
                    //println("drop '$node'")
                    val centerCoordinateX = floor((e.sceneX - gridOffsetX) / 31.0)
                    val centerCoordinateY = floor((e.sceneY - gridOffsetY) / 31.0)
                    val headCoordinatePair = node.findHeadCoordinate(Pair(centerCoordinateX, centerCoordinateY))
                    val headCoordinateX = headCoordinatePair.first
                    val headCoordinateY = headCoordinatePair.second
                    //println("CENTER COORDINATE: ($centerCoordinateX, $centerCoordinateY)")
                    //println("HEAD COORDINATE: ($headCoordinateX, $headCoordinateY)")
                    val newShipID = game.placeShip(
                        node.getShipType(),
                        node.getOrientation(),
                        headCoordinateX.toInt(),
                        headCoordinateY.toInt()
                    )
                    if (newShipID != -1) {
                        // STICK TO PROPER COORDINATE SYSTEM
                        val desiredX = gridOffsetX + (31 * headCoordinateX)
                        val desiredY = gridOffsetY + (31 * headCoordinateY)
                        // DESIRED = TOPLEFT + CURTRNSLATE_X + TRANSLATEX
                        node.translateX += (desiredX - node.boundsInParent.minX)
                        node.translateY += (desiredY - node.boundsInParent.minY)
                        node.setShipID(newShipID)
                        movingNode = null
                    } else {
                        // RESET TO STARTING POSITION
                        // IF HORIZONTAL RESET
                        if (node.getOrientation() == Orientation.Horizontal) {
                            rotateShip(node)
                        }
                        val translation = TranslateTransition(Duration(400.0), node)
                        translation.fromX = node.translateX
                        translation.toX = 0.0
                        translation.fromY = node.translateY
                        translation.toY = 0.0
                        translation.interpolator = Interpolator.EASE_BOTH
                        translation.play()
                        node.setShipID(-1)
                        movingNode = null
                    }
                }
            }

        }

        parent.addEventFilter(MouseEvent.MOUSE_MOVED) { e ->
            val node = movingNode
            if (node != null) {
                node.translateX = e.sceneX + offsetX
                node.translateY = e.sceneY + offsetY
                // we don't want to drag the background too
                e.consume()
            }
        }
    }

    private fun rotateShip(node: ShipView) {
        val rotation = RotateTransition(Duration(200.0), node)
        rotation.axis = Rotate.Z_AXIS
        rotation.interpolator = Interpolator.LINEAR
        if (node.getOrientation() == Orientation.Vertical) {
            rotation.fromAngle = 0.0
            rotation.toAngle = -90.0
        } else {
            rotation.fromAngle = -90.0
            rotation.toAngle = 0.0
        }
        rotation.play()
        node.swapOrientation()
    }

    fun makeMovable(node: ShipView) {
        node.onMouseClicked = EventHandler { e ->
            if (movingNode == null) {
                this.movingNode = node
                val centerBoatX = node.boundsInLocal.centerX
                val centerBoatY = node.boundsInLocal.centerY
                node.translateX = -(centerBoatX - e.sceneX)
                node.translateY = -(centerBoatY - e.sceneY)
                offsetX = node.translateX - e.sceneX
                offsetY = node.translateY - e.sceneY
                val shipID = node.getShipID()
                if (shipID != -1) {
                    game.removeShip(shipID)
                    node.setShipID(-1)
                }
                // we don't want to drag the background too
                e.consume()
            }
        }
    }
}
