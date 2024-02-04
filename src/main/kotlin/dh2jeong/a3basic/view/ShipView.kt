package dh2jeong.a3basic.view

import dh2jeong.a3basic.model.Orientation
import dh2jeong.a3basic.model.ShipType
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.Rectangle

class ShipView(private val startX: Double, private val startY: Double, private val shipType: ShipType) : Rectangle() {
    private var currentOrientation = Orientation.Vertical
    private var shipID = -1

    init {
        x = startX
        y = startY
        stroke = Color.BLACK
        strokeWidth = 1.0
        width = 20.0
        initializeBoat()
    }

    private fun initializeBoat() {
        when (shipType) {
            ShipType.Destroyer -> {
                this.fill = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.YELLOW), Stop(0.5, Color.BLUE), Stop(0.5, Color.BLUE), Stop(1.0, Color.BLUE)
                )
                this.height = 50.0
            }

            ShipType.Cruiser -> {
                this.fill = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.RED), Stop(0.5, Color.BLUE), Stop(0.5, Color.BLUE), Stop(1.0, Color.BLUE)
                )
                this.height = 81.0
            }

            ShipType.Submarine -> {
                this.fill = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.GREEN), Stop(0.5, Color.BLUE), Stop(0.5, Color.BLUE), Stop(1.0, Color.BLUE)
                )
                this.height = 81.0
            }

            ShipType.Battleship -> {
                this.fill = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.DARKGRAY), Stop(0.5, Color.BLUE), Stop(0.5, Color.BLUE), Stop(1.0, Color.BLUE)
                )
                this.height = 112.0
            }

            else -> {
                this.fill = LinearGradient(
                    0.0, 0.0, 0.0, 1.0, true, CycleMethod.NO_CYCLE,
                    Stop(0.0, Color.PURPLE), Stop(0.5, Color.BLUE), Stop(0.5, Color.BLUE), Stop(1.0, Color.BLUE)
                )
                this.height = 143.0
            }
        }
    }

    fun swapOrientation() {
        currentOrientation = if (currentOrientation == Orientation.Vertical) {
            Orientation.Horizontal
        } else {
            Orientation.Vertical
        }
    }

    fun getOrientation(): Orientation {
        return currentOrientation
    }

    fun getShipType(): ShipType {
        return shipType
    }

    fun getShipID(): Int {
        return shipID
    }

    fun setShipID(newShipID: Int) {
        shipID = newShipID
    }

    fun findHeadCoordinate(centerCoordinate: Pair<Double, Double>): Pair<Double, Double> {
        if (currentOrientation == Orientation.Horizontal) {
            if (shipType == ShipType.Destroyer) {
                return Pair(centerCoordinate.first, centerCoordinate.second)
            } else if (shipType == ShipType.Carrier) {
                return Pair(centerCoordinate.first - 2.0, centerCoordinate.second)
            }
            return Pair(centerCoordinate.first - 1.0, centerCoordinate.second)
        }
        if (shipType == ShipType.Destroyer) {
            return Pair(centerCoordinate.first, centerCoordinate.second)
        } else if (shipType == ShipType.Carrier) {
            return Pair(centerCoordinate.first, centerCoordinate.second - 2.0)
        }
        return Pair(centerCoordinate.first, centerCoordinate.second - 1.0)
    }
}